/**
 * This project is for the purposes of learning recursive backtracking
 * 
 * @author aghayalod
 *
 */
public class Sudoku {
    // define a grid to solve
    public static int[][] GRID_TO_SOLVE = { { 3, 0, 6, 5, 0, 8, 4, 0, 0 }, { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 8, 7, 0, 0, 0, 0, 3, 1 }, { 0, 0, 3, 0, 1, 0, 0, 8, 0 }, { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
            { 0, 5, 0, 0, 9, 0, 6, 0, 0 }, { 1, 3, 0, 0, 0, 0, 2, 5, 0 }, { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
            { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };

    private int[][] board; // board we will be using
    public static final int EMPTY = 0; // empty cell
    public static final int SIZE = 9; // size of sudoku grids

    // Constructor
    public Sudoku(int[][] board) {
        this.board = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = board[i][j];
            }

        }
    }

    // we check if a possible number is already in a row
    private boolean isInRow(int row, int number) {

        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == number)
                return true;
        }

        return false;
    }

    // check if possible number already exists in column
    private boolean isInCol(int col, int number) {
        for (int j = 0; j < SIZE; j++) {
            if (board[j][col] == number)
                return true;
        }
        return false;
    }

    // check if number exists in 3x3
    private boolean isInBox(int col, int row, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                if (number == board[i][j])
                    return true;
            }
        }
        return false;
    }

    // combine all helpers to determine if number placed in a certain position is
    // okay
    private boolean isOk(int row, int col, int number) {
        return !isInCol(col, number) && !isInRow(row, number) && !isInBox(col, row, number);
    }

    // solve method THIS IS THE RECURSIVE BACKTRACKING PART
    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // find empty cell
                if (board[row][col] == EMPTY) {
                    System.out.println("Filling: " + row + "," + col);
                    // try all possible choices
                    for (int number = 1; number <= SIZE; number++) {
                        if (isOk(row, col, number)) {
                            // if all constraints passed add to board
                            System.out.println("Solved: " + number);
                            board[row][col] = number;

                            // BACKTRACKING
                            System.out.println("Recursion");
                            if (solve()) { // Base Case if solve is true that means we're done, it also calls solve
                                           // again on the method
                                return true;

                            } else {
                                // if not a solution empty the cell and we go right back through
                                System.out.println("go back");
                                board[row][col] = EMPTY;

                            }

                        }
                    }
                    return false; // grid is unsolvable

                }
            }
        }
        System.out.println("done");
        return true; // sudoku solved
    }

    public void display() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
            System.out.print((int)'c'+ " is the numerical value of c");

        }
    }

    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku(GRID_TO_SOLVE);
        System.out.println("Sudoku grid to solve");
        sudoku.display();

        if (sudoku.solve()) {
            System.out.println("Graph solved with simple backtracking");
            sudoku.display();
        } else {
            System.out.println("Unsolvable");
        }
    }

}
