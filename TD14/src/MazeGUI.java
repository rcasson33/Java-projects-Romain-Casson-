import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.util.concurrent.CancellationException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;

public class MazeGUI extends JFrame {
  private Maze maze;
  private final MazePanel canvas;
  private final JSlider delaySlider;
  private final JSpinner sizeSpinner;
  private final JComboBox<String> methodCombo;
  private final JButton resetBtn;
  private final JButton generateBtn;
  private final JButton solveBtn;
  private final JButton screenshotBtn;
  private final JToggleButton pauseBtn;
  private volatile boolean paused = false;
  private volatile boolean canceled = false;
  private final Object pauseLock = new Object();
  private final JCheckBox debugCheck;

  // Color palette (tweak here to change theme)
  private static final Color CANVAS_BG = new Color(245, 245, 245);
  private static final Color MAZE_BG = Color.WHITE;
  private static final Color WALL_COLOR = Color.BLACK;
  private static final Color START_TINT = new Color(0x6DBFF2); // #6DBFF2
  private static final Color EXIT_TINT = new Color(0xD9B504); // #D9B504
  private static final Color PATH_TRIANGLE = new Color(0x6b7a8f); // #6b7a8f
  private static final Color PATH_CONNECTOR = new Color(0xAA6b7a8f, true); // #797FF2
  private static final Color GRID_OVERLAY = new Color(0, 0, 0, 20);

  public MazeGUI() {
    super("Maze GUI");
    try {
      this.maze = new Maze(Path.of("maze25.txt"));
    } catch (IOException ex) {
      this.maze = new Maze(30, 30);
    }

    // Canvas rendering the maze grid
    canvas = new MazePanel();

    // Controls
    resetBtn = new JButton("reset");
    generateBtn = new JButton("generate");
    solveBtn = new JButton("solve");
    screenshotBtn = new JButton("screenshot");

    resetBtn.addActionListener(this::onReset);
    generateBtn.addActionListener(this::onGenerate);
    solveBtn.addActionListener(this::onSolve);
    screenshotBtn.addActionListener(this::onScreenshot);

    // Mnemonics (Alt+Key)
    resetBtn.setMnemonic(KeyEvent.VK_R);
    generateBtn.setMnemonic(KeyEvent.VK_G);
    solveBtn.setMnemonic(KeyEvent.VK_S);
    screenshotBtn.setMnemonic(KeyEvent.VK_P);

    // Size chooser (N x N)
    int n0 = Math.max(3, Math.min(200, (maze.R - 1) / 2));
    sizeSpinner = new JSpinner(new SpinnerNumberModel(n0, 3, 200, 1));
    sizeSpinner.setPreferredSize(new Dimension(64, 24));

    // Delay slider (log-scale: delay = 10^(d/10) - 1)
    delaySlider = new JSlider(0, 30, 8);
    delaySlider.setPreferredSize(new Dimension(200, 24));
    delaySlider.setPaintTicks(false);
    delaySlider.setPaintLabels(false);

    // Install step callback for animation repaint + pacing
    Maze.setOnStep(() -> {
      // always repaint on EDT
      SwingUtilities.invokeLater(canvas::repaint);
      // sleep on the worker thread to pace animation
      int pos = delaySlider.getValue();
      int d = (int) Math.round(Math.pow(10.0, pos / 10.0) - 1.0);
      if (d > 0) {
        try {
          Thread.sleep(d);
        } catch (InterruptedException ignored) {
        }
      }
      if (canceled) {
        throw new CancellationException();
      }
      if (paused) {
        synchronized (pauseLock) {
          while (paused) {
            try {
              pauseLock.wait();
            } catch (InterruptedException ignored) {
            }
          }
        }
      }
    });

    JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
    controls.add(new JLabel("size:"));
    controls.add(sizeSpinner);
    controls.add(resetBtn);
    controls.add(generateBtn);
    controls.add(new JLabel("method:"));
    methodCombo = new JComboBox<>(new String[] {
        "recursive",
        "iterative: stack",
        "iterative: queue",
        "iterative: random",
        "iterative: max",
        "wilson"
    });
    methodCombo.setPreferredSize(new Dimension(160, 24));
    controls.add(methodCombo);
    controls.add(solveBtn);

    setLayout(new BorderLayout());
    add(controls, BorderLayout.NORTH);
    add(canvas, BorderLayout.CENTER);

    // Bottom controls (pause/resume at left; screenshot at right)
    JPanel bottom = new JPanel(new BorderLayout());
    JPanel leftBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pauseBtn = new JToggleButton("pause");
    pauseBtn.addActionListener(this::onPause);
    leftBottom.add(pauseBtn);
    leftBottom.add(new JLabel("delay (ms):"));
    leftBottom.add(delaySlider);
    debugCheck = new JCheckBox("debug");
    debugCheck.addActionListener(e -> canvas.repaint());
    leftBottom.add(debugCheck);
    bottom.add(leftBottom, BorderLayout.CENTER);
    JPanel rightBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    rightBottom.add(screenshotBtn);
    bottom.add(rightBottom, BorderLayout.EAST);
    add(bottom, BorderLayout.SOUTH);

    // Keyboard shortcuts (Ctrl+Key)
    JRootPane root = getRootPane();
    InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = root.getActionMap();
    // Action shortcuts removed; use mnemonics. Keeping only Ctrl+Space for pause.

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK), "togglePause");
    am.put("togglePause", new javax.swing.AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pauseBtn.doClick();
      }
    });

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 968);
    setLocationRelativeTo(null);
  }

  private void onReset(ActionEvent e) {
    // cancel any running task and unpause to let it exit
    canceled = true;
    synchronized (pauseLock) {
      pauseLock.notifyAll();
    }
    resumeIfPaused();
    int n = (Integer) sizeSpinner.getValue();
    this.maze = new Maze(n, n);
    canvas.repaint();
  }

  private void onGenerate(ActionEvent e) {
    resumeIfPaused();
    canceled = false;
    setButtonsEnabled(false);
    runAsync(() -> {
      try {
        String method = (String) methodCombo.getSelectedItem();
        int n = (Integer) sizeSpinner.getValue();
        this.maze = new Maze(n, n);
        if ("recursive".equals(method)) {
          maze.generateRec();
        } else if ("wilson".equals(method)) {
          maze.generateWilson();
        } else {
          Bag<Integer> bag;
          if ("iterative: queue".equals(method))
            bag = new Queue<>();
          else if ("iterative: random".equals(method))
            bag = new RandomBag<>();
          else if ("iterative: max".equals(method))
            bag = new MaxBag<>();
          else
            bag = new Stack<>(); // iterative: stack (default)
          maze.generateIter(bag);
        }
        maze.clearMarks();
      } catch (CancellationException ex) {
        // aborted by reset/pause cancel
      } finally {
        canceled = false;
        SwingUtilities.invokeLater(() -> {
          setButtonsEnabled(true);
          resumeIfPaused();
          canvas.repaint();
        });
      }
    });
  }

  private void onSolve(ActionEvent e) {
    resumeIfPaused();
    canceled = false;
    setButtonsEnabled(false);
    runAsync(() -> {
      try {
        maze.solve();
      } catch (CancellationException ex) {
        // aborted by reset/pause cancel
      } finally {
        canceled = false;
        SwingUtilities.invokeLater(() -> {
          setButtonsEnabled(true);
          resumeIfPaused();
          canvas.repaint();
        });
      }
    });
  }

  private void setButtonsEnabled(boolean enabled) {
    // enable/disable all buttons in the north panel
    Container north = getContentPane().getComponentCount() > 0 ? (Container) getContentPane().getComponent(0) : null;
    if (north instanceof JPanel) {
      for (Component c : ((JPanel) north).getComponents()) {
        if (c instanceof JButton) {
          if (c == resetBtn)
            continue;
          c.setEnabled(enabled);
        }
      }
    }
  }

  private void onScreenshot(ActionEvent e) {
    try {
      String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
      Path out = Path.of("maze-" + ts + ".png");
      int w = Math.max(1, canvas.getWidth());
      int h = Math.max(1, canvas.getHeight());
      BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = img.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      canvas.paint(g2);
      g2.dispose();
      ImageIO.write(img, "png", out.toFile());
      JOptionPane.showMessageDialog(this, "Saved: " + out.toAbsolutePath(), "Screenshot",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(this, "Failed to save PNG: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void onPause(ActionEvent e) {
    if (pauseBtn.isSelected()) {
      paused = true;
      pauseBtn.setText("resume");
    } else {
      paused = false;
      synchronized (pauseLock) {
        pauseLock.notifyAll();
      }
      pauseBtn.setText("pause");
    }
  }

  private void resumeIfPaused() {
    if (paused) {
      paused = false;
      synchronized (pauseLock) {
        pauseLock.notifyAll();
      }
    }
    pauseBtn.setSelected(false);
    pauseBtn.setText("pause");
  }

  private void runAsync(Runnable task) {
    Thread t = new Thread(task, "maze-task");
    t.setDaemon(true);
    t.start();
  }

  private class MazePanel extends JPanel {
    MazePanel() {
      // Fond blanc pour éviter toute marge grise autour du labyrinthe
      setBackground(MAZE_BG);
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(800, 800);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      int R = maze.R;
      int C = maze.C;
      double cell = Math.max(2.0, Math.min(getWidth() / (double) C, getHeight() / (double) R));
      double w = cell * C;
      double h = cell * R;
      double ox = (getWidth() - w) / 2.0;
      double oy = (getHeight() - h) / 2.0;

      // Draw background grid
      g2.setColor(MAZE_BG);
      g2.fill(new Rectangle2D.Double(ox, oy, w, h));

      // Draw thinner walls as lines/pillars, connecting to pillars and not snapping
      // to frame edges
      double t = Math.max(1.0, cell / 5.0); // wall thickness
      double ext = (cell - t) / 2.0; // extend segments into neighboring pillar cells
      g2.setColor(WALL_COLOR);
      for (int i = 0; i < R; i++) {
        for (int j = 0; j < C; j++) {
          int idx = i + R * j;
          Maze.Cell val = maze.grid[idx];
          if (val == Maze.VOID || val == Maze.UP || val == Maze.DOWN || val == Maze.LEFT || val == Maze.RIGHT)
            continue;

          double x = ox + j * cell;
          double y = oy + i * cell;
          boolean evenI = (i % 2) == 0;
          boolean evenJ = (j % 2) == 0;
          if (evenI && !evenJ) {
            // horizontal segment, extend left/right to meet pillars
            double yy = y + (cell - t) / 2.0;
            double xx = x - ext;
            double ww = cell + 2.0 * ext;
            g2.fill(new Rectangle2D.Double(xx, yy, ww, t));
          } else if (!evenI && evenJ) {
            // vertical segment, extend up/down to meet pillars
            double xx = x + (cell - t) / 2.0;
            double yy = y - ext;
            double hh = cell + 2.0 * ext;
            g2.fill(new Rectangle2D.Double(xx, yy, t, hh));
          } else {
            // intersection pillar (even-even)
            double xx = x + (cell - t) / 2.0;
            double yy = y + (cell - t) / 2.0;
            g2.fill(new Rectangle2D.Double(xx, yy, t, t));
          }
        }
      }

      // Highlight start and exit cells
      int si = maze.start % R, sj = maze.start / R;
      int ei = maze.exit % R, ej = maze.exit / R;
      g2.setColor(START_TINT); // green start tint
      g2.fill(new Rectangle2D.Double(ox + sj * cell, oy + si * cell, cell, cell));
      g2.setColor(EXIT_TINT); // red exit tint
      g2.fill(new Rectangle2D.Double(ox + ej * cell, oy + ei * cell, cell, cell));

      // Draw path arrows as triangles + connecting lines to next cell
      Color pathFill = PATH_TRIANGLE; // opaque for triangles
      Color pathLine = PATH_CONNECTOR; // semi-transparent for connectors
      Stroke oldStroke = g2.getStroke();
      g2.setStroke(new BasicStroke((float) Math.max(1.5, cell / 8.0), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      for (int i = 0; i < R; i++) {
        for (int j = 0; j < C; j++) {
          int idx = i + R * j;
          Maze.Cell val = maze.grid[idx];
          if (val == Maze.UP || val == Maze.DOWN || val == Maze.LEFT || val == Maze.RIGHT) {
            double x = ox + j * cell;
            double y = oy + i * cell;

            double pad = Math.max(1.0, cell / 12.0); // small padding from cell border
            Path2D.Double tri = new Path2D.Double();
            double dx, dy; // destination: center of next cell in direction
            double cx = x + cell / 2.0;
            double cy = y + cell / 2.0;
            if (val == Maze.UP) {
              tri.moveTo(x + cell / 2.0, y + pad);
              tri.lineTo(x + pad, y + cell - pad);
              tri.lineTo(x + cell - pad, y + cell - pad);
              tri.closePath();
              dx = cx;
              dy = cy - 2.0 * cell;
            } else if (val == Maze.DOWN) {
              tri.moveTo(x + cell / 2.0, y + cell - pad);
              tri.lineTo(x + pad, y + pad);
              tri.lineTo(x + cell - pad, y + pad);
              tri.closePath();
              dx = cx;
              dy = cy + 2.0 * cell;
            } else if (val == Maze.LEFT) {
              tri.moveTo(x + pad, y + cell / 2.0);
              tri.lineTo(x + cell - pad, y + pad);
              tri.lineTo(x + cell - pad, y + cell - pad);
              tri.closePath();
              dx = cx - 2.0 * cell;
              dy = cy;
            } else { // RIGHT
              tri.moveTo(x + cell - pad, y + cell / 2.0);
              tri.lineTo(x + pad, y + pad);
              tri.lineTo(x + pad, y + cell - pad);
              tri.closePath();
              dx = cx + 2.0 * cell;
              dy = cy;
            }

            g2.setColor(pathLine);
            g2.draw(new Line2D.Double(cx, cy, dx, dy));
            g2.setColor(pathFill);
            g2.fill(tri);
          }
        }
      }
      g2.setStroke(oldStroke);

      // Draw MARK discs (non-directional marks)
      g2.setColor(PATH_TRIANGLE);
      double diam = Math.max(2.0, cell / 2.0);
      double rad = diam / 2.0;
      for (int i = 0; i < R; i++) {
        for (int j = 0; j < C; j++) {
          int idx = i + R * j;
          if (maze.grid[idx] == Maze.MARK) {
            double cx = ox + j * cell + cell / 2.0;
            double cy = oy + i * cell + cell / 2.0;
            g2.fill(new Ellipse2D.Double(cx - rad, cy - rad, diam, diam));
          }
        }
      }

      // Debug overlay (optional per-cell code letters)
      if (debugCheck.isSelected()) {
        // Per-cell semi-transparent background
        for (int i = 0; i < R; i++) {
          for (int j = 0; j < C; j++) {
            double x = ox + j * cell;
            double y = oy + i * cell;
            // base: translucent white
            g2.setColor(new Color(255, 255, 255, 120));
            g2.fill(new Rectangle2D.Double(x, y, cell, cell));
            boolean evenI = (i % 2) == 0;
            boolean evenJ = (j % 2) == 0;
            boolean border = (i == 0 || i == R - 1 || j == 0 || j == C - 1);
            if (border || (evenI && evenJ)) {
              // pillars and border: gray taint
              g2.setColor(new Color(128, 128, 128, 80));
              g2.fill(new Rectangle2D.Double(x, y, cell, cell));
            } else if (evenI ^ evenJ) {
              // passages (between cells): green taint
              g2.setColor(new Color(0, 200, 0, 50));
              g2.fill(new Rectangle2D.Double(x, y, cell, cell));
            }
          }
        }
        // Letters overlay
        g2.setColor(new Color(220, 0, 0));
        float fs = (float) Math.max(10.0, cell * 0.7);
        Font oldFont = g2.getFont();
        g2.setFont(oldFont.deriveFont(Font.BOLD, fs));
        FontMetrics fm = g2.getFontMetrics();
        for (int i = 0; i < R; i++) {
          for (int j = 0; j < C; j++) {
            Maze.Cell val = maze.grid[i + R * j];
            char ch;
            if (val == Maze.WALL)
              ch = 'W';
            else if (val == Maze.REMOVABLE)
              ch = 'R';
            else if (val == Maze.UP)
              ch = '↑';
            else if (val == Maze.DOWN)
              ch = '↓';
            else if (val == Maze.LEFT)
              ch = '←';
            else if (val == Maze.RIGHT)
              ch = '→';
            else if (val == Maze.MARK)
              ch = '*';
            else
              ch = 'ø'; // VOID
            if (ch != '\0') {
              double x = ox + j * cell;
              double y = oy + i * cell;
              String s1 = String.valueOf(ch);
              int sw = fm.stringWidth(s1);
              int sa = fm.getAscent();
              int sd = fm.getDescent();
              double tx = x + (cell - sw) / 2.0;
              double ty = y + (cell + sa - sd) / 2.0;
              g2.drawString(s1, (float) tx, (float) ty);
            }
          }
        }
        g2.setFont(oldFont);
      }
      // Grid overlay (optional subtle grid)
      g2.setColor(GRID_OVERLAY);
      for (int i = 0; i <= R; i++) {
        double y = oy + i * cell;
        g2.draw(new Line2D.Double(ox, y, ox + w, y));
      }
      for (int j = 0; j <= C; j++) {
        double x = ox + j * cell;
        g2.draw(new Line2D.Double(x, oy, x, oy + h));
      }

      g2.dispose();
    }

    // no extra helpers needed for triangle arrows
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MazeGUI().setVisible(true));
  }
}
