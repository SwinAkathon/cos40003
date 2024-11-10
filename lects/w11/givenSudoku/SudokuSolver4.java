
public class SudokuSolver4 {

    int[][] matrix;

    public SudokuSolver4(int[][] matrix) {
        this.matrix = matrix;
    }

    public static void main(String[] args) {

        int[][] sudoku = {
            {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 9, 0, 2, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 4, 5, 7, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 3, 0},
            {0, 0, 1, 0, 0, 0, 0, 6, 8},
            {0, 0, 8, 5, 0, 0, 0, 1, 0},
            {0, 9, 0, 0, 0, 0, 4, 0, 0}};

        //SudokuSolver4 s = new SudokuSolver4(sudoku);
        SudokuSolver4 s = new SudokuSolver4(Input4.board);

        long tStart = System.currentTimeMillis();
        //System.naoTime();

        s.solve(0, 0);

        long elapseTime = System.currentTimeMillis() - tStart;

        s.printArray();
        System.out.println("It takes " + elapseTime + " ms.");

    }

    public boolean solve(int i, int j) {
        if (j == 9) {
            j = 0;
            i++;
            if (i == 9) {
                return true;
            }
        }

        //if, the cell i,j is empty, try 1-9; otherwise move to the next  
        if (matrix[i][j] == 0) {

            for (int k = 1; k <= 9; k++) {
                //try from 1 to 9  
                if (check(i, j, k)) {
                    matrix[i][j] = k;
                    if (solve(i, j + 1)) {
                        return true;
                    }
                }
            }

            matrix[i][j] = 0;
            return false;

        } else {
            //if occupied, move the next cell 
            return solve(i, j + 1);
        }

    }

    private boolean check(int row, int line, int number) {
        //check if there exists the same number at the row/line 
        for (int i = 0; i < 9; i++) {
            if (matrix[row][i] == number || matrix[i][line] == number) {
                return false;
            }
        }
        //check if there exists the same number in the corresponding grid  
        int tempRow = row / 3;
        int tempLine = line / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[tempRow * 3 + i][tempLine * 3 + j] == number) {
                    return false;
                }
            }
        }

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

    static int[][] board
            = {{0, 2, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 6, 0, 0, 0, 0, 3},
            {0, 7, 4, 0, 8, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 3, 0, 0, 2},
            {0, 8, 0, 0, 4, 0, 0, 1, 0},
            {6, 0, 0, 5, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 7, 8, 0},
            {5, 0, 0, 0, 0, 9, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 4, 0}};

}
