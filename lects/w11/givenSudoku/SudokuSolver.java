
public class SudokuSolver {

  public static void main(String[] args) {
    SudokuSolver s = new SudokuSolver();
    // char[][] board = {
    // {'.', '2', '6', '5', '.', '.', '.', '9', '.'},
    // {'5', '.', '.', '.', '7', '9', '.', '.', '4'},
    // {'3', '.', '.', '.', '1', '.', '.', '.', '.'},
    // {'6', '.', '.', '.', '.', '.', '8', '.', '7'},
    // {'.', '7', '5', '.', '2', '.', '.', '1', '.'},
    // {'.', '1', '.', '.', '.', '.', '4', '.', '.'},
    // {'.', '.', '.', '3', '.', '8', '9', '.', '2'},
    // {'7', '.', '.', '.', '6', '.', '.', '4', '.'},
    // {'.', '3', '.', '2', '.', '.', '1', '.', '.'}};

    System.out.println("Initial board:");
    s.print(Input.board);

    long tStart = System.currentTimeMillis();
    s.solver(Input.board);

    long elapseTime = System.currentTimeMillis() - tStart;

    System.out.println("\nSolved board:");
    s.print(Input.board);
    System.out.println("It takes " + elapseTime + " ms.");
  }

  /**
   * @effects prints <tt>board</tt> to the standard output
   *
   */
  public void print(char[][] board) {
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board[0].length; c++) {
        System.out.print(board[r][c] + " ");
      }
      System.out.println();
    }

  }

  /**
   * @effects brute-force method to solve <tt>board</tt>, i.e. iterating through
   *          all possible number combinations for the remaining cells.
   * @modifies  board, i.e. 
   *            empty cells (i.e. '.') of the specified board are filled with new values
   */
  public boolean solver(char[][] board) {
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board[0].length; c++) {
        if (board[r][c] == '.') {
          // cell (r,c) has not been solved
          for (int k = 1; k <= 9; k++) {  
            // loop: set (r,c) to '1'->'9' and check for validity
            board[r][c] = (char) ('0' + k);
            if (isValid(board, r, c) // is (r,c) locally valid? AND
                && solver(board)     // does (r,c) lead to rest of the board being solved?
            ) {
              // YES => board is resolved
              return true;  
            } else {
              // NO => reset (r,c) so that we can retry with next char in '1'->'9'
              board[r][c] = '.';
            }
          }

          // no valid value from '1'->'9' is found for (r,c)
          // => board is not solved, return false to backtrack
          return false;
        }
      }
    }
    return true;
  }

  /**
   * @effects check if row r, column c and the 3x3 grid intersecting with r, c of
   *          the specified board are valid.
   */
  public boolean isValid(char[][] board, int r, int c) {
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
}

class Input {

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
