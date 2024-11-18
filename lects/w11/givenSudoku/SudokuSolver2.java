
public class SudokuSolver2 {

  char[][] board = new char[9][9];

  public SudokuSolver2(char[][] board) {
    // copy board to this.board
    for (int r = 0; r < 9; r++) {
      for (int c = 0; c < 9; c++) {
        this.board[r][c] = board[r][c];
      }
    }
  }

  // same
  public void print() {
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board[0].length; c++) {
        System.out.print(board[r][c] + " ");
      }
      System.out.println();
    }

  }

  /**
   * @effects brute-force method to solve <tt>board</tt> starting from cell (i,j),
   *          i.e. iterating through all possible number combinations for (i,j)
   *          and the remaining cells.
   * @modifies this.board,
   *           i.e. empty cells (i.e. '.') of this.board are filled with new
   *           values
   */
  public boolean solver(int i, int j) {
    if (j == 9) {
      // cycle j
      j = 0;
      i++;
      if (i == 9) {
        // no more rows -> RESOLVED
        return true;
      }
    }

    if (board[i][j] >= '1' && board[i][j] <= '9') {
      // (i,j) is set => solve (i, j+1) - the next one on the same row
      return solver(i, j + 1);
    } else {
      // (i,j) has not yet been
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

  /**
   * same as SudokuSolver.isValid()
   * 
   * @effects check if row r, column c and the corresponding 3x3 grid
   *          intersecting with r, c of the specified board are valid.
   */
  public boolean isValid(int r, int c) {
    // check row for duplicates
    /*
     * for all i in [0,8], 
     * row[i] =
     *    - true if value ('1' + i) is set at row r
     *    - false if otherwise
     */
    boolean[] row = new boolean[9];
    for (int i = 0; i < 9; i++) {
      if (board[r][i] >= '1' && board[r][i] <= '9') {
        /*
         * v = board[r][i] be value of cell (r,i)
         * => idx = (int) (v - '1') is in [0,8] (due to how v is set)
         * row[idx]
         *    = true if v has been set (i.e. duplicate)
         *    = false if v has NOT been set
         */
        if (row[board[r][i] - '1'] == false) {
          row[board[r][i] - '1'] = true;
        } else {
          return false;
        }
      }
    }

    // check column c for duplicates
    /*
     * for all i in [0,8], 
     * col[i] =
     *      - true if value ('1' + i) is set at column c
     *      - false if otherwise
     */
    boolean[] col = new boolean[9];
    for (int i = 0; i < 9; i++) {
      if (board[i][c] >= '1' && board[i][c] <= '9') {
        /*
         * v = board[i][c] be value of cell (i,c)
         * => idx = (int) (v - '1') is in [0,8]
         * col[idx]
         *    = true if v has been set in the column (i.e. duplicate)
         *    = false if v has NOT been set in the column
         */
        if (col[board[i][c] - '1'] == false) {
          // NOT yet set, set it
          col[board[i][c] - '1'] = true;
        } else {
          // duplicate
          return false;
        }
      }
    }

    // check the corresponding 3*3 grid for duplicates
    /*
     * for all g in [0,8], 
     * grid[g] =
     *      - true if value '1'+g is set in the grid
     *      - false if otherwise
     * 
     * corresponding grid G = (r1, r2, c1, c2) of r, c satisfies:
     *  r1 <= r < r2 /\ r1 = floor(r/3)*3 /\ r2 = r1 + 3
     *  c1 <= c < c2 /\ r1 = floor(c/3)*3 \/ c2 = c1 + 3
     *  (r1,c1 are the closest multiples of 3 still smaller than r)
     */    
    boolean[] grid = new boolean[9];
    for (int i = (r / 3) * 3; i < (r / 3) * 3 + 3; i++) {
      /* i in [r1, r2] of the corresponding grid */
      for (int j = (c / 3) * 3; j < (c / 3) * 3 + 3; j++) {
        /* j in [c1, c2] of the corresponding grid */
        if (board[i][j] >= '1' && board[i][j] <= '9') {
        /* 
         * v = board[i][j]: value of cell (i,j)
         * => idx = (int) (v - '1') is in [0,8]
         * grid[idx]  
         *    = true  if v has been set in the grid (i.e. duplicate)
         *    = false if v has NOT been set in the grid
         */
          if (grid[board[i][j] - '1'] == false) {
            // NOT yet set, set it
            grid[board[i][j] - '1'] = true;
          } else {
            // duplicate
            return false;
          }
        }
      }
    }

    // row, column and the corresponding 3*3 grid do not have duplicates
    return true;
  }

  public static void main(String[] args) {

    // char[][] board = { { '.', '2', '6', '5', '.', '.', '.', '9', '.' },
    // { '5', '.', '.', '.', '7', '9', '.', '.', '4' },
    // { '3', '.', '.', '.', '1', '.', '.', '.', '.' },
    // { '6', '.', '.', '.', '.', '.', '8', '.', '7' },
    // { '.', '7', '5', '.', '2', '.', '.', '1', '.' },
    // { '.', '1', '.', '.', '.', '.', '4', '.', '.' },
    // { '.', '.', '.', '3', '.', '8', '9', '.', '2' },
    // { '7', '.', '.', '.', '6', '.', '.', '4', '.' },
    // { '.', '3', '.', '2', '.', '.', '1', '.', '.' } };

    SudokuSolver2 s = new SudokuSolver2(Input2.board);
    // SudokuSolver2 s = new SudokuSolver2(board);

    long tStart = System.currentTimeMillis();
    boolean solved = s.solver(0, 0);

    // s.print(board);
    long elapseTime = System.currentTimeMillis() - tStart;
    if (solved) {
      s.print();
    }
    System.out.println("It takes " + elapseTime + " ms.");
  }

}

class Input2 {

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
