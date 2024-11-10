
public class SudokuSolver5 implements Runnable {

    char[][] board = new char[9][9];
    int start_i, start_j;

    private static volatile boolean finished = false;

    public SudokuSolver5(char[][] board, int i, int j) {
        this.start_i = i;
        this.start_j = j;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                this.board[r][c] = board[r][c];
            }
        }
    }

    public void run() {
        if (solver(start_i, start_j)) {
            finished = true;
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
            return solver(i, j + 1);
        } else {
            for (int k = 1; k <= 9; k++) {
                board[i][j] = (char) ('0' + k);
                if (isValid(i, j) && solver(i, j + 1)) {
                    return true;
                }
            }
            board[i][j] = '.';
            return false;
        }

    }

    public boolean isValid(int r, int c) {
        //check row
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

        //check column
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

        //check the 3*3 grid
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

        char[][] board = {{'.', '2', '6', '5', '.', '.', '.', '9', '.'},
        {'5', '.', '.', '.', '7', '9', '.', '.', '4'},
        {'3', '.', '.', '.', '1', '.', '.', '.', '.'},
        {'6', '.', '.', '.', '.', '.', '8', '.', '7'},
        {'.', '7', '5', '.', '2', '.', '.', '1', '.'},
        {'.', '1', '.', '.', '.', '.', '4', '.', '.'},
        {'.', '.', '.', '3', '.', '8', '9', '.', '2'},
        {'7', '.', '.', '.', '6', '.', '.', '4', '.'},
        {'.', '3', '.', '2', '.', '.', '1', '.', '.'}};

        SudokuSolver5[] instance = new SudokuSolver5[4];
        Thread[] t = new Thread[4];

        long tStart = System.currentTimeMillis();

        for (int i = 0; i < 4; i++) {
            Input5.board[0][0] = (char) ('0' + (i + 1));
            instance[i] = new SudokuSolver5(Input5.board, 0, 1);
            t[i] = new Thread(instance[i]);
        }

        for (int i = 0; i < 4; i++) {

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

    static char[][] board
            = {{'.', '2', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '6', '.', '.', '.', '.', '3'},
            {'.', '7', '4', '.', '8', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '3', '.', '.', '2'},
            {'.', '8', '.', '.', '4', '.', '.', '1', '.'},
            {'6', '.', '.', '5', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '1', '.', '7', '8', '.'},
            {'5', '.', '.', '.', '.', '9', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '4', '.'}};

}
