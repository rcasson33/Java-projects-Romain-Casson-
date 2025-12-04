import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class Test5c extends JFrame {

	public Test5c() {
		super();
		init();
		loadDict();
	}

	public HashSet<String> _mots = new HashSet<String>();
	JTextArea _jmot = new JTextArea("");
	JTextArea _best = new JTextArea("");
	JSlider _delay = new JSlider(JSlider.HORIZONTAL,
			1, 500, 10);
	public static String msgCrypte1 = "JTSUBUUTORTTNAEOOOES";
	public static String msgCrypte2 = "ITILNAOTNENNUNMTSTEECIOLT";
	public static String msgCrypte3 = "NGINRSRMPTMEBTTSEEMNETLIAUAREO";
	public static String[] msgs = { msgCrypte1, msgCrypte2, msgCrypte3 };
	JComboBox<String> _jta = new JComboBox<String>(msgs);
	JSpinner js = new JSpinner();
	public static int tailleCle = 5;

	public void loadDict() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("MotsFrancais.txt"));
			String s = in.readLine();
			while (s != null) {
				_mots.add(s.trim().toUpperCase());
				s = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Veuillez placer le fichier 'MotsFrancais.txt' a la racine du projet");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String highlight(int start, boolean alt, JTextArea target) {
		String s = target.getText();

		for (int i = s.length(); i >= start + 1; i--) {
			String t = s.substring(start, i).toUpperCase();
			if (_mots.contains(t)) {
				Highlighter highlighter = target.getHighlighter();
				HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
						alt ? Color.pink : Color.yellow);
				try {
					highlighter.addHighlight(start, i, painter);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				String r = highlight(i, !alt, target);
				return t + r;
			}
		}
		return "";
	}

	public void setContent(String s) {
		_jmot.setText(s);
		String t = highlight(0, true, _jmot);
		if (_best.getText().length() < t.length()) {
			_best.setText("" + t);
			highlight(0, true, _best);
		}

	}

	private JPanel createFlowPanel(String txt) {
		JLabel l = new JLabel(txt + " ");
		l.setPreferredSize(new Dimension(120, 25));
		JPanel res = new JPanel(new FlowLayout(FlowLayout.LEADING));
		res.add(l);
		res.setAlignmentX(LEFT_ALIGNMENT);
		// res.setBorder(BorderFactory.createTitledBorder("Options"));
		return res;
	}

	public void init() {
		this.setPreferredSize(new Dimension(560, 300));
		this.setLayout(new BorderLayout());

		JPanel jps = new JPanel();
		jps.setLayout(new BoxLayout(jps, BoxLayout.PAGE_AXIS));

		JPanel jpn = new JPanel();
		jpn.setLayout(new BoxLayout(jpn, BoxLayout.PAGE_AXIS));
		jpn.setBorder(BorderFactory.createTitledBorder("Options"));

		JPanel jpc = new JPanel();
		jpc.setLayout(new BoxLayout(jpc, BoxLayout.PAGE_AXIS));

		JButton jb = new JButton("Decrypte");
		JPanel tmp = new JPanel();
		tmp.setLayout(new BorderLayout());
		jb.setPreferredSize(new Dimension(100, 100));
		jps.add(jb);

		jps.add(tmp, BorderLayout.EAST);

		_jta.setPreferredSize(new Dimension(300, 20));

		tmp = createFlowPanel("Message code");
		tmp.add(_jta);
		jpn.add(tmp);

		tmp = createFlowPanel("Taille de Cle");
		SpinnerNumberModel sm = new SpinnerNumberModel(tailleCle, 1, 15, 1);
		js.setModel(sm);
		tmp.add(js);
		jpn.add(tmp);

		tmp = createFlowPanel("Vitesse");
		_delay.setPreferredSize(new Dimension(200, 30));
		_delay.setPaintTicks(true);
		_delay.setMinorTickSpacing(20);
		tmp.add(_delay);
		jpn.add(tmp);

		_jmot.setEditable(false);
		_jmot.setBackground(UIManager.getColor("Panel.background"));
		_best.setEditable(false);
		_best.setBackground(UIManager.getColor("Panel.background"));

		jpc.setBorder(BorderFactory.createTitledBorder("Sortie"));

		tmp = createFlowPanel("Mot courant");
		tmp.add(_jmot);
		_jmot.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		jpc.add(tmp);

		_jmot.setPreferredSize(new Dimension(250, 20));
		_best.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));

		tmp = createFlowPanel("Meilleur prefixe ");
		tmp.add(_best);
		jpc.add(tmp);

		this.add(jpn, BorderLayout.NORTH);
		tmp = new JPanel(new BorderLayout());
		tmp.add(jpc, BorderLayout.CENTER);
		tmp.add(jps, BorderLayout.EAST);
		this.add(tmp, BorderLayout.SOUTH);

		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
	}

	public void start() {
		_best.setText("");
		(new Test7bThread(this)).start();
	}

	public int getKeySize() {
		return ((Integer) js.getValue()).intValue();
	}

	public String getMessage() {
		return _jta.getSelectedItem().toString();
	}

	public long getDelay() {
		return (long) (1000. / (double) _delay.getValue());
	}

	public class Test7bThread extends Thread {
		Test5c _w;

		public Test7bThread(Test5c w) {
			_w = w;
		}

		public void run() {
			try {
				String[] msgs = Decryptement.decrypterToutes(_w.getMessage(), _w.getKeySize());
				for (int i = 0; i < msgs.length; i++) {
					_w.setContent(msgs[i]);
					synchronized (_w) {
						_w.wait(_w.getDelay());
					}

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Test5c w = new Test5c();
		w.setTitle("Test5c");
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.pack();
		w.setVisible(true);
	}
}
