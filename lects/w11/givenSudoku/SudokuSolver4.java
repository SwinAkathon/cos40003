
public class SudokuSolver4 {

  int[][] matrix; // same as board

  public SudokuSolver4(int[][] matrix) {
    // do not copy board
    this.matrix = matrix;
  }

  public static void main(String[] args) {

    // int[][] sudoku = {
    //     { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
    //     { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
    //     { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
    //     { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
    //     { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
    //     { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
    //     { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
    //     { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
    //     { 0, 9, 0, 0, 0, 0, 4, 0, 0 } };

    // SudokuSolver4 s = new SudokuSolver4(sudoku);
    SudokuSolver4 s = new SudokuSolver4(Input4.board);

    long tStart = System.currentTimeMillis();
    // System.naoTime();

    s.solve(0, 0);

    long elapseTime = System.currentTimeMillis() - tStart;

    s.printArray();
    System.out.println("It takes " + elapseTime + " ms.");

  }

  /**
   * Logically behaves the same as SudokuSolver2.solver but is more efficient in function check() which only checks validity for a number, instead of the entire row, column and the corresponding grid
   * 
   * @effects brute-force method to solve <tt>board</tt> starting from cell (i,j), i.e. iterating through all possible number combinations for (i,j) and the remaining cells.
   * @modifies  this.board, 
   *            i.e. empty cells (i.e. '.') of this.board are filled with new values
   */
  public boolean solve(int i, int j) {
    if (j == 9) {
      // cycle j 
      j = 0;
      i++;
      if (i == 9) {
        // no more rows -> RESOLVED
        return true;
      }
    }

    // if, the cell i,j is empty, try 1-9; otherwise move to the next
    if (matrix[i][j] == 0) {
      // (i,j) is empty, try 1->9
      for (int k = 1; k <= 9; k++) {
        // try from 1 to 9
        if (check(i, j, k)) { 
          // k is locally valid for (i,j)
          matrix[i][j] = k;
          if (solve(i, j + 1)) {
            // k is globally valid for (i,j)
            // i.e. the next cell (i, j + 1) and (recursively) remainder of the board are solved
            return true;
          }
        }
      }

      // no valid value from '1'->'9' is found for (i,j)
      // reset (i,j)
      matrix[i][j] = 0;
      // return false to backtrack
      return false;

    } else {
      // if occupied, move the next cell
      return solve(i, j + 1);
    }

  }

  /** 
   * Works faster than isValid() in other versions in that it 
   * (1) only checks validity for a given number, instead of the entire row, column and the corresponding grid. 
   * (2) uses one loop, instead of 2 separate loops for checking row, col
   * 
   * @effects return true if the specified number is NOT found in either row, column or the corresponding 3*3 grid
   */
  private boolean check(int row, int col, int number) {
    // check if there exists the same number at the row or col
    // use one instead of two loops
    for (int i = 0; i < 9; i++) {
      if (matrix[row][i] == number || matrix[i][col] == number) {
        return false;
      }
    }

    // check if there exists the same number in the corresponding grid
    int tempRow = row / 3;
    int tempLine = col / 3;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (matrix[tempRow * 3 + i][tempLine * 3 + j] == number) {
          return false;
        }
      }
    }

    // number is not found in row, column and the corresponding 3*3 grid
    return true;
  }

  public void printArray() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }
}

class Input4 {

  static int[][] board = { 
      { 0, 2, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 6, 0, 0, 0, 0, 3 },
      { 0, 7, 4, 0, 8, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 3, 0, 0, 2 },
      { 0, 8, 0, 0, 4, 0, 0, 1, 0 },
      { 6, 0, 0, 5, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 1, 0, 7, 8, 0 },
      { 5, 0, 0, 0, 0, 9, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 4, 0 } };

}
