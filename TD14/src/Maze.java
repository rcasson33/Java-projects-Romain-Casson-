import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Maze {
    public static enum Cell {
        VOID, WALL, REMOVABLE, RIGHT, UP, LEFT, DOWN, MARK
    }

    public static final Cell VOID = Cell.VOID;
    public static final Cell WALL = Cell.WALL;
    public static final Cell REMOVABLE = Cell.REMOVABLE;
    public static final Cell RIGHT = Cell.RIGHT;
    public static final Cell UP = Cell.UP;
    public static final Cell LEFT = Cell.LEFT;
    public static final Cell DOWN = Cell.DOWN;
    public static final Cell MARK = Cell.MARK;

    // Optional animation hook for stepwise visualization
    private static volatile Runnable onStep;

    public static void setOnStep(Runnable r) {
        onStep = r;
    }

    // Notify UI to animate a step (no delay here; GUI controls pacing)
    private static void step() {
        if (onStep != null)
            onStep.run();
    }

    final Cell[] grid;
    final int rows, cols;
    final int R, C;
    final int[] dirs; // {1, -R, -1, R}
    final int start, exit;

    // Initialize a maze with no open passage, and random distinct start/exit cells.
    Maze(int rows, int cols) {
        if (rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("rows and cols must be positive");
        if ((long) rows * (long) cols < 2)
            throw new IllegalArgumentException("maze must have at least two cells for distinct start/exit");
        this.rows = rows;
        this.cols = cols;
        R = 2 * rows + 1;
        C = 2 * cols + 1;
        grid = new Cell[R * C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                int idx = i + R * j;
                boolean iEven = (i % 2 == 0);
                boolean jEven = (j % 2 == 0);
                if (iEven && jEven) {
                    grid[idx] = WALL; // pillars and borders handled by bounds below
                } else if (iEven ^ jEven) {
                    // between two cells: interior removable walls or border walls
                    if (i > 0 && i < 2 * rows && j > 0 && j < 2 * cols)
                        grid[idx] = REMOVABLE;
                    else
                        grid[idx] = WALL;
                } else {
                    // cell
                    grid[idx] = VOID;
                }
            }
        }

        // initialize direction shifts now that R is known
        this.dirs = new int[] { 1, -R, -1, R };
        // Choose distinct random start and exit among cell coordinates
        Random rnd = new Random();
        int sRow = rnd.nextInt(rows), sCol = rnd.nextInt(cols);
        int eRow, eCol;
        do {
            eRow = rnd.nextInt(rows);
            eCol = rnd.nextInt(cols);
        } while (eRow == sRow && eCol == sCol);
        start = index(sRow, sCol);
        exit = index(eRow, eCol);
    }

    // Construct a Maze from its string representation (same format as toString)
    // Assumptions:
    // - No direction marks (LEFT/RIGHT/UP/DOWN) are present in the string.
    // - Start cell is marked with 'E' and exit cell with 'S'.
    // - Walls are represented by '█' and void cells by ' '.
    Maze(String s) {
        String[] lines = s.split("\n");
        if (lines.length == 0)
            throw new IllegalArgumentException("empty maze string");

        int cols = lines[0].length();
        for (String line : lines) {
            if (line.length() != cols)
                throw new IllegalArgumentException("inconsistent line lengths in maze string");
        }

        R = lines.length;
        // initialize direction shifts now that R is known
        this.dirs = new int[] { 1, -R, -1, R };
        C = cols;
        grid = new Cell[R * C];

        this.rows = R / 2;
        this.cols = C / 2;
        int sIndex = -1;
        int eIndex = -1;

        for (int i = 0; i < R; i++) {
            String line = lines[i];
            for (int j = 0; j < C; j++) {
                int idx = i + R * j;
                char ch = line.charAt(j);
                switch (ch) {
                    case ' ': // empty passage
                        grid[idx] = VOID;
                        break;
                    case 'E': // start
                        grid[idx] = VOID;
                        sIndex = idx;
                        break;
                    case 'S': // exit
                        grid[idx] = VOID;
                        eIndex = idx;
                        break;
                    case '█': { // wall glyph: map to WALL for border/pillars, REMOVABLE for interior walls
                        boolean border = (i == 0 || i == R - 1 || j == 0 || j == C - 1);
                        boolean iEven = (i % 2 == 0);
                        boolean jEven = (j % 2 == 0);
                        if (border || (iEven && jEven))
                            grid[idx] = WALL;
                        else if (iEven ^ jEven)
                            grid[idx] = REMOVABLE;
                        else
                            grid[idx] = VOID; // cells should not be '█'
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("invalid character in maze: '" + ch + "'");
                }
            }
        }

        if (sIndex < 0 || eIndex < 0)
            throw new IllegalArgumentException("maze string must contain 'E' (start) and 'S' (exit)");

        start = sIndex;
        exit = eIndex;
    }

    // Construct a Maze from a file path by reading its contents
    // and delegating to the Maze(String) constructor above.
    Maze(Path path) throws IOException {
        this(Files.readString(path));
    }

    // check that the border and pillar squares are WALL, that the inner walls are
    // either REMOVABLE or VOID, and that the cells are not walls.
    boolean isValid() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                int idx = i + R * j;
                boolean border = (i == 0 || i == R - 1 || j == 0 || j == C - 1);
                boolean iEven = (i % 2 == 0);
                boolean jEven = (j % 2 == 0);
                if (border) {
                    if (grid[idx] != WALL)
                        return false;
                } else if (iEven && jEven) { // pillars
                    if (grid[idx] != WALL)
                        return false;
                } else if (iEven ^ jEven) { // walls between two cells (interior)
                    if (grid[idx] != REMOVABLE && grid[idx] != VOID)
                        return false;
                } else {
                    // cells
                    if (grid[idx] == WALL || grid[idx] == REMOVABLE)
                        return false;
                }
            }
        }
        return true;
    }

    static char charOfCell(Cell c) {
        switch (c) {

            case VOID:
                return ' ';
            case REMOVABLE:
                // return '▓';
            case WALL:
                return '█';
            case UP:
                return '^';
            // return '▲';
            case DOWN:
                return 'v';
            // return '▲';
            case LEFT:
                return '<';
            // return '▲';
            case RIGHT:
                return '>';
            // return '▲';
            case MARK:
                return '*';
            default:
                throw new Error("Invalid value");
        }

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++)
                s.append(charOfCell(grid[i + R * j]));
            s.append('\n');
        }
        return s.toString();
    }

    public void clearMarks() {
        for (int i = 1; i < R; i += 2) {
            for (int j = 1; j < C; j += 2)
                grid[i + R * j] = VOID;
        }
    }

    public int index(int i, int j) {
        return 2 * i + 1 + R * (2 * j + 1);
    }

    // A maze is perfect if its passage graph over cells is a tree:
    // connected and acyclic (edges = nodes - 1).
    public boolean isPerfect() {
        int cellRows = (R - 1) / 2;
        int cellCols = (C - 1) / 2;
        int totalCells = cellRows * cellCols;

        // Count open edges between adjacent cells (right and down to avoid double
        // count)
        // The edges (open or closed) are at odd indices in `grid`.
        int edges = 0;
        for (int i = 1; i < grid.length; i += 2) {
            if (grid[i] == VOID)
                edges++;
        }

        // DFS to check connectivity using openings between cells
        boolean[] vis = new boolean[R * C];
        java.util.ArrayDeque<Integer> q = new java.util.ArrayDeque<>();
        q.add(start);
        int visited = 0;
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            if (vis[u])
                continue;
            vis[u] = true;
            visited++;
            for (int shift : dirs) {
                int v = u + 2 * shift; // neighbor cell index
                if (grid[u + shift] != VOID)
                    continue;
                q.addLast(v);
            }
        }

        boolean connected = (visited == totalCells);
        boolean acyclic = (edges == totalCells - 1);
        return connected && acyclic;
    }

    // Helpers for directions and shuffling
    private void shuffle(int[] a) {
        Random rnd = new Random();
        for (int k = a.length - 1; k > 0; k--) {
            int r = rnd.nextInt(k + 1);
            int tmp = a[k];
            a[k] = a[r];
            a[r] = tmp;
        }
    }

    private Cell markOfShift(int shift) {
        if (shift == 1)
            return DOWN;
        if (shift == -1)
            return UP;
        if (shift == R)
            return RIGHT;
        if (shift == -R)
            return LEFT;
        throw new IllegalArgumentException("invalid shift: " + shift);
    }

    private int shiftOfMark(Cell mark) {
        if (mark == DOWN)
            return 1;
        if (mark == UP)
            return -1;
        if (mark == RIGHT)
            return R;
        if (mark == LEFT)
            return -R;
        throw new IllegalArgumentException("invalid mark: " + mark);
    }

    public boolean solve() {
        return solve(start, exit);
    }

    boolean solve(int from, int to) {
        if (from == to){
            return true;
        }
        for (int shift: dirs) {
            int passage = from + shift;
            int next = from + 2*shift; // indice de la cellule voisine
            if (grid[passage] != WALL && grid[passage] != REMOVABLE && grid[next] == VOID){
                grid[from] = markOfShift(shift);
                step();
                if (solve(next, to)){
                    return true;
                }
                grid[from] = VOID;
            }
        }
        return false;
    }

    public void generateRec() {
        generateRec(start);
    }

    public void generateRec(int cell) {
        if (isPerfect()){
            return;
        }
        int[] random_dirs = dirs.clone();
        shuffle(random_dirs);
        grid[cell] = MARK;
        for (int shift : random_dirs) {
            int passage = cell + shift;
            int next = cell + 2 * shift;
            if (grid[passage] == REMOVABLE &&  grid[next] == VOID){
                grid[cell] = MARK;
                grid[next] = MARK;
                grid[passage] = VOID;
                step();
                generateRec(next);
                }
            }
        }

    public void generateIter(Bag<Integer> bag){
        grid[start] = MARK;
        bag.push(start);
        while (!bag.isEmpty()){
            int cell = bag.pop();
            int[] random_dirs = dirs.clone();
            shuffle(random_dirs);
            for (int shift : random_dirs) {
                int passage = cell + shift;
                int next = cell + 2 * shift;
                if (grid[passage] == REMOVABLE && grid[cell] == MARK && grid[next] == VOID){
                    grid[cell] = MARK;
                    grid[next] = MARK;
                    grid[passage] = VOID;
                    step();
                    bag.push(next);
                }
            }
        }
    }

    // Wilson's algorithm for maze generation using loop-erased random walks
    public void generateWilson() {
        grid[start] = MARK;
        int [] tab = new int[R*C];
        shuffle(tab);
        while (!NonMarque()){
            int index = tab[com];
        }

    }

    public boolean NonMarque(){
        for (Cell c : grid){
            if (c != MARK){
                return false;
            }
        }
        return true;
    }

}
