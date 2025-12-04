public class Test {
  private static void assertTrue(boolean cond, String msg) {
    if (!cond)
      throw new AssertionError(msg);
  }

  private static void assertFalse(boolean cond, String msg) {
    if (cond)
      throw new AssertionError(msg);
  }

  private static void testSolve() {
    // Test solve() using provided files maze25.txt and maze25sol.txt
    try {
      String txt = java.nio.file.Files.readString(java.nio.file.Path.of("maze25.txt"));
      // Use the new helper to validate that a path exists and is consistent
      assertSolves("maze25.txt should be solvable", txt.trim());

      // Additionally check the exact rendered path matches the expected solution
      String expected = java.nio.file.Files.readString(java.nio.file.Path.of("maze25sol.txt")).trim();
      Maze m2 = new Maze(txt);
      boolean solved = m2.solve(m2.start, m2.exit);
      assertTrue(solved, "maze25.txt should be solvable");
      // Ensure solver did not corrupt the grid structure (walls/passages layout)
      assertTrue(m2.isValid(), "after solve(): maze structure invalid per isValid() — walls/passages layout corrupted");
      String actual = m2.toString().trim();
      assertTrue(actual.equals(expected), "Solved maze rendering must match maze25sol.txt");

      System.out.println("solve() tests passed on maze25.txt.");
    } catch (java.nio.file.NoSuchFileException e) {
      throw new RuntimeException(
          "Missing maze file. Ensure maze25.txt (and maze25sol.txt) exist. Current dir: "
              + System.getProperty("user.dir"));
    } catch (java.io.IOException e) {
      throw new RuntimeException("I/O error while testing solve(): " + e.getMessage(), e);
    }
  }

  public static void main(String[] args) {
    testSolveSmallMazes();
    testSolve();
    testGenerateRec();
    testGenerateIter();
    testGenerateWilson();
  }

  // --- Additional small-maze tests for solve() ---
  private static void testSolveSmallMazes() {
    // 1x2 corridor, open between cells (should solve)
    String m1 = String.join("\n",
        "█████",
        "█E S█",
        "█████");
    assertSolves("1x2 open corridor", m1);

    // 2x2 with a single bent path: E -> right -> down -> S
    String m2 = String.join("\n",
        "█████",
        "█E  █",
        "███ █",
        "█ █S█",
        "█████");
    assertSolves("2x2 corner path", m2);

    // 1x2 corridor but closed between cells (unsolvable)
    String m3 = String.join("\n",
        "█████",
        "█E█S█",
        "█████");
    assertDoesNotSolve("1x2 blocked corridor", m3);
    System.out.println("Small-maze solve() tests passed.");
  }

  private static void assertSolves(String name, String mazeText) {
    Maze m = new Maze(mazeText);
    String before = mazeText + "\n";
    boolean ok = m.solve(m.start, m.exit);
    assertTrue(m.isValid(), "after solve(): maze structure invalid per isValid() — walls/passages layout corrupted");
    if (!ok) {
      throw new AssertionError(name + ": solve() returned false on a solvable maze.\n"
          + "Maze before:\n" + before
          + "Maze after:\n" + m.toString());
    }
    if (!reachesExitViaMarks(m)) {
      throw new AssertionError(name + ": path marks do not lead from start to exit.\n"
          + diagPositions(m)
          + "Maze after (with marks):\n" + m.toString());
    }
  }

  private static void assertDoesNotSolve(String name, String mazeText) {
    Maze m = new Maze(mazeText);
    boolean ok = m.solve(m.start, m.exit);
    assertTrue(m.isValid(), "after solve(): maze structure invalid per isValid() — walls/passages layout corrupted");
    if (ok) {
      throw new AssertionError(name + ": solve() unexpectedly returned true.\n"
          + "Maze after (with marks):\n" + m.toString());
    }
  }

  // Follow the arrow marks from start; verify we reach exit safely.
  private static boolean reachesExitViaMarks(Maze m) {
    int p = m.start;
    int steps = 0;
    int maxSteps = (m.R * m.C); // very loose upper bound in grid coordinates
    while (steps++ < maxSteps) {
      if (p == m.exit)
        return true;
      Maze.Cell mark = m.grid[p];
      int shift;
      if (mark == Maze.DOWN)
        shift = 1;
      else if (mark == Maze.UP)
        shift = -1;
      else if (mark == Maze.RIGHT)
        shift = m.R;
      else if (mark == Maze.LEFT)
        shift = -m.R;
      else
        return false; // no outgoing mark
      // wall between the two cells must be open
      if (m.grid[p + shift] != Maze.VOID)
        return false;
      p = p + 2 * shift;
    }
    return false;
  }

  private static String diagPositions(Maze m) {
    return String.format("start=%s exit=%s (R=%d,C=%d)\n",
        coord(m, m.start), coord(m, m.exit), m.R, m.C);
  }

  private static String coord(Maze m, int idx) {
    int i = idx % m.R; // row in grid coordinates
    int j = idx / m.R; // col in grid coordinates
    return "(" + i + "," + j + ")";
  }



  // --- Generation helpers ---
  private static void assertPerfect(String name, Maze m) {
    if (!m.isValid())
      throw new AssertionError(name + ": maze not structurally valid after generation (isValid()==false)");
    if (!m.isPerfect())
      throw new AssertionError(name + ": maze is not perfect (should be a single spanning tree)");
  }

  private static void assertRandomness(String name, java.util.function.Supplier<Maze> sup) {
    String first = sup.get().toString();
    boolean different = false;
    for (int k = 0; k < 4; k++) {
      String s2 = sup.get().toString();
      if (!s2.equals(first)) { different = true; break; }
    }
    if (!different)
      throw new AssertionError(name + ": no apparent randomness — repeated generations produced identical mazes");
  }

  private static Maze genRec(int r, int c) {
    Maze m = new Maze(r, c);
    m.generateRec();
    return m;
  }

  private static Maze genIter(int r, int c, Bag<Integer> bag) {
    Maze m = new Maze(r, c);
    m.generateIter(bag);
    return m;
  }

  private static Maze genWilson(int r, int c) {
    Maze m = new Maze(r, c);
    m.generateWilson();
    return m;
  }

  private static void testGenerateRec() {
    Maze m = genRec(12, 12);
    assertPerfect("generateRec", m);
    assertRandomness("generateRec", () -> genRec(12, 12));
    System.out.println("generateRec: perfect + valid; randomness observed.");
  }

  private static void testGenerateIter() {
    // Stack policy
    Maze s = genIter(12, 12, new Stack<>());
    assertPerfect("generateIter(stack)", s);
    assertRandomness("generateIter(stack)", () -> genIter(12, 12, new Stack<>()));

    // Queue policy
    Maze q = genIter(12, 12, new Queue<>());
    assertPerfect("generateIter(queue)", q);

    // Random policy
    Maze r = genIter(12, 12, new RandomBag<>());
    assertPerfect("generateIter(random)", r);
    System.out.println("generateIter (stack/queue/random): perfect + valid; randomness observed (stack).");
  }

  private static void testGenerateWilson() {
    Maze m = genWilson(12, 12);
    assertPerfect("generateWilson", m);
    assertRandomness("generateWilson", () -> genWilson(12, 12));

    Maze m0 = genWilson(3, 3);
    m0.clearMarks();
		int cnt = 0;

    // Test uniformity
		// Chernoff bound parameters
		final int nbMazes = 192;
		final int K = 1000000;
		final double mu = K / (double) nbMazes;
		final double eps = 0.01;
		final double delta = Math.sqrt( 3*Math.log(2/eps)/mu );

		for(int k = 0; k < K; ++k) {
			m = new Maze(3, 3);
			m.generateWilson();
			m.clearMarks();
			if(java.util.Arrays.equals(m.grid, m0.grid))
				++cnt;
		}

		assertTrue( cnt > (1-delta)*mu && cnt < (1+delta)*mu, "distribution of generateWilson does not seem to be uniform");

    System.out.println("generateWilson: perfect + valid; randomness observed; uniform distribution observed");
  }

}
