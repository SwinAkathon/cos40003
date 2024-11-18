
public class SudokuSolver5 implements Runnable {

  char[][] board = new char[9][9];
  int start_i, start_j;

  private static volatile boolean finished = false;

  public SudokuSolver5(char[][] board, int i, int j) {
    this.start_i = i;
    this.start_j = j;

    // copy board to this.board: WHY???
    // Not really parallel, because board is copied!!!!
    for (int r = 0; r < 9; r++) {
      for (int c = 0; c < 9; c++) {
        this.board[r][c] = board[r][c];
      }
    }
  }

  @Override
  public void run() {
    if (solver(start_i, start_j)) {
      finished = true;
      // moved to after: 
      print();
    }
  }

  public void print() {
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board[0].length; c++) {
        System.out.print(board[r][c] + " ");
      }
      System.out.println();
    }

  }

  /**
   * Similar to SudokuSolver2.solver(), except for 
   * the check for finished. This is a volatile class variable that 
   * is shared among all threads and whose updates are atomic.
   */
  public boolean solver(int i, int j) {
    if (finished) {
      return false;
    }

    if (j == 9) {
      j = 0;
      i++;
      if (i == 9) {
        return true;
      }
    }

    if (board[i][j] >= '1' && board[i][j] <= '9') {
      // cell (i,j) has been set, try next one one same row (i, j+1)
      return solver(i, j + 1);
    } else {
      // cell (i,j) has NOT been set, solve it
      for (int k = 1; k <= 9; k++) {
        // loop: set (i,j) to '1'->'9' and check for validity
        board[i][j] = (char) ('0' + k);
        if (isValid(i, j)       // is (i,j) locally valid? AND
            && solver(i, j + 1) // does (i,j) lead to the next cell (i, j+1) being solved?
            ) {
            // YES => (i,j) is resolved
          return true;
        }
      }

      // reset (i,j) so that we can retry with next char in '1'->'9'
      board[i][j] = '.';

      // no valid value from '1'->'9' is found for (i,j), return false to backtrack
      return false;
    }

  }

  /* 
   * same as SudokuSolver2.isValid()
   */
  public boolean isValid(int r, int c) {
    // check row
    boolean[] row = new boolean[9];
    for (int i = 0; i < 9; i++) {
      if (board[r][i] >= '1' && board[r][i] <= '9') {
        if (row[board[r][i] - '1'] == false) {
          row[board[r][i] - '1'] = true;
        } else {
          return false;
        }
      }
    }

    // check column
    boolean[] col = new boolean[9];
    for (int i = 0; i < 9; i++) {
      if (board[i][c] >= '1' && board[i][c] <= '9') {
        if (col[board[i][c] - '1'] == false) {
          col[board[i][c] - '1'] = true;
        } else {
          return false;
        }
      }
    }

    // check the 3*3 grid
    boolean[] grid = new boolean[9];
    for (int i = (r / 3) * 3; i < (r / 3) * 3 + 3; i++) {
      for (int j = (c / 3) * 3; j < (c / 3) * 3 + 3; j++) {
        if (board[i][j] >= '1' && board[i][j] <= '9') {
          if (grid[board[i][j] - '1'] == false) {
            grid[board[i][j] - '1'] = true;
          } else {
            return false;
          }
        }
      }
    }

    return true;
  }

  public static void main(String[] args) {

    // char[][] board = { { '.', '2', '6', '5', '.', '.', '.', '9', '.' },
    //     { '5', '.', '.', '.', '7', '9', '.', '.', '4' },
    //     { '3', '.', '.', '.', '1', '.', '.', '.', '.' },
    //     { '6', '.', '.', '.', '.', '.', '8', '.', '7' },
    //     { '.', '7', '5', '.', '2', '.', '.', '1', '.' },
    //     { '.', '1', '.', '.', '.', '.', '4', '.', '.' },
    //     { '.', '.', '.', '3', '.', '8', '9', '.', '2' },
    //     { '7', '.', '.', '.', '6', '.', '.', '4', '.' },
    //     { '.', '3', '.', '2', '.', '.', '1', '.', '.' } };

    System.out.println("Parallel Sudoku:");

    final int numThreads = 4;
    SudokuSolver5[] instance = new SudokuSolver5[numThreads];
    Thread[] t = new Thread[numThreads];

    long tStart = System.currentTimeMillis();

    /* Creates numThreads number of threads, 
     * - each thread runs a unique instance of solver
     * - each solver operates on a copy of the same board but with  cell (0,0) set to a unique value
     */
    for (int i = 0; i < numThreads; i++) {
      /* original code: */
      Input5.board[0][0] = (char) ('0' + (i + 1));
      instance[i] = new SudokuSolver5(Input5.board, 0, 1);
      
      // updated v1.0: starts at the same cell (0,0)
      // instance[i] = new SudokuSolver5(Input5.board, 0, 0);

      // updated v2.0: starts at different cells
      // instance[i] = new SudokuSolver5(Input5.board, 0, i % 9);

      t[i] = new Thread(instance[i]);
    }

    for (int i = 0; i < numThreads; i++) {
      t[i].start();
    }

    while (!finished) {
      Thread.yield();
    }

    long elapseTime = System.currentTimeMillis() - tStart;

    System.out.println("It takes " + elapseTime + " ms.");
  }
}

class Input5 {

  static char[][] board = { 
      { '.', '2', '.', '.', '.', '.', '.', '.', '.' },
      { '.', '.', '.', '6', '.', '.', '.', '.', '3' },
      { '.', '7', '4', '.', '8', '.', '.', '.', '.' },
      { '.', '.', '.', '.', '.', '3', '.', '.', '2' },
      { '.', '8', '.', '.', '4', '.', '.', '1', '.' },
      { '6', '.', '.', '5', '.', '.', '.', '.', '.' },
      { '.', '.', '.', '.', '1', '.', '7', '8', '.' },
      { '5', '.', '.', '.', '.', '9', '.', '.', '.' },
      { '.', '.', '.', '.', '.', '.', '.', '4', '.' } };

}
