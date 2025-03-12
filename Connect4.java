import java.util.Scanner;

public class Connect4 {
    public static final int EMPTY = 0;
    public static final int PLAYER_X = 1;
    public static final int PLAYER_O = -1;

    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final int WINNING_COUNT = 4;

    public static final Scanner scanner = new Scanner(System.in);

    public static boolean isValidMove(int[][] board, int col) {
        return col >= 0 && col < COLS && board[0][col] == EMPTY;
    }

    public static boolean makeMove(int[][] board, int col, int player) {
        if (isValidMove(board, col)) {
            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] == EMPTY) {
                    board[row][col] = player;
                    return true;
                }
            }
        }
        return false;
    }

    public static int checkWin(int[][] board) {
        // Check each player for a win
        for (int player : new int[] { PLAYER_X, PLAYER_O }) {

            // Check horizontal
            for (int row = 0; row < ROWS; row++) {
                for (int column = 0; column <= COLS - WINNING_COUNT; column++) {
                    int sum = board[row][column] + board[row][column + 1] + board[row][column + 2] + board[row][column + 3];
                    if (sum == 4 * player)
                        return player;
                }
            }

            // Check vertical
            for (int column = 0; column < COLS; column++) {
                for (int row = 0; row <= ROWS - WINNING_COUNT; row++) {
                    int sum = board[row][column] + board[row + 1][column] + board[row + 2][column] + board[row + 3][column];
                    if (sum == 4 * player)
                        return player;
                }
            }

            // Check diagonal (top-left to bottom-right)
            for (int row = 0; row < ROWS - WINNING_COUNT; row++) {
                for (int column = 0; column <= COLS - WINNING_COUNT; column++) {
                    int sum = board[row][column] + board[row + 1][column + 1] + board[row + 2][column + 2] + board[row + 3][column + 3];
                    if (sum == 4 * player)
                        return player;
                }
            }

            // Check diagonal (bottom-left to top-right)
            for (int row = 0; row <= ROWS - WINNING_COUNT; row++) {
                for (int column = COLS - 1; column >= 0 + WINNING_COUNT; column--) {
                    int sum = board[row][column] + board[row + 1][column - 1] + board[row + 2][column - 2] + board[row + 3][column - 3];
                    if (sum == 4 * player)
                        return player;
                }
            }

        }
        return EMPTY; // No winner
    }

    public static boolean isBoardFull(int[][] board) {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col] == EMPTY)
                return false;
        }
        return true;
    }

    public static void printBoard(int[][] board) {
        System.out.println("  c 0 1 2 3 4 5 6");
        System.out.println("r ----------------");
        for (int row = 0; row < ROWS; row++) {
            System.out.print(row + " | ");
            for (int col = 0; col < COLS; col++) {
                int cell = board[row][col];
                if (cell == PLAYER_X)
                    System.out.print("X ");
                else if (cell == PLAYER_O)
                    System.out.print("O ");
                else
                    System.out.print(". ");
            }
            System.out.println("|");
        }
        System.out.println(" ----------------");
    }

    public static void main(String[] args) {
        int[][] board = new int[ROWS][COLS];
        int currentPlayer = PLAYER_X;
        boolean gameWon = false;

        while (!gameWon && !isBoardFull(board)) {
            printBoard(board);
            int col;
            while (true) {
                System.out.println("Player " + (currentPlayer == PLAYER_X ? "X" : "O") + "'s turn.");
                col = (int) (Math.random() * COLS);
                // Sleep for 1 second to make it easier to see the moves
                // Uncomment the following lines to simulate a delay
                // try {
                // Thread.sleep(1000);
                // } catch (InterruptedException e) {
                // Thread.currentThread().interrupt();
                // }
                // Uncomment the following lines to allow user input
                // System.out.print("Enter column (0-6): ");
                if (isValidMove(board, col))
                    break;
                System.out.println("Invalid move. Try again.");
            }

            if (makeMove(board, col, currentPlayer)) {
                int winner = checkWin(board);
                if (winner != EMPTY) {
                    gameWon = true;
                    printBoard(board);
                    System.out.println("Player " + (winner == PLAYER_X ? 'X' : 'O') + " wins!");
                } else {
                    currentPlayer *= -1; // Switch player
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        if (!gameWon) {
            printBoard(board);
            System.out.println("It's a draw!");
        }
    }

}
