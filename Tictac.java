import java.util.*;

public class Tictac {

    public static void main(String[] args) {

        char[][] board = new char[3][3];

        // Initialize board
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = ' ';
            }
        }

        Scanner sc = new Scanner(System.in);
        char player = 'X';
        boolean gameover = false;
        int moves = 0;

        while (!gameover) {
            printBoard(board);

            // ------------ INPUT BLOCK (With exit + validation) ------------
            int row = -1, col = -1;

            while (true) {
                try {
                    System.out.println("Player " + player + " enter row and col (0-2) or type 'exit' to quit: ");

                    String r = sc.next();  // row input as string
                    if (r.equalsIgnoreCase("exit")) {
                        System.out.println("Game Exited.");
                        System.exit(0);
                    }

                    String c = sc.next();  // col input as string
                    if (c.equalsIgnoreCase("exit")) {
                        System.out.println("Game Exited.");
                        System.exit(0);
                    }

                    // convert to int
                    row = Integer.parseInt(r);
                    col = Integer.parseInt(c);

                    // range check
                    if (row < 0 || row > 2 || col < 0 || col > 2) {
                        System.out.println("❌ Invalid position! Enter numbers between 0 and 2.");
                        continue;
                    }

                    break; // valid → break loop

                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input! Please enter numbers or type 'exit'.");
                }
            }
            // --------------------------------------------------------------

            // Place the move
            if (board[row][col] == ' ') {
                board[row][col] = player;
                moves++;

                gameover = haveWon(board, player);

                if (gameover) {
                    printBoard(board);
                    System.out.println("🎉 Player " + player + " has WON the game!");
                } else {
                    player = (player == 'X') ? 'O' : 'X';  // switch player
                }

            } else {
                System.out.println("❌ Already Occupied! Try again.");
            }
        }

        sc.close();
    }

    // PRINT BOARD FUNCTION
    public static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int row = 0; row < 3; row++) {
            System.out.print("| ");
            for (int col = 0; col < 3; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    // CHECK WINNER
    public static boolean haveWon(char[][] board, char player) {

        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == player) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == player && board[1][col] == player && board[2][col] == player) {
                return true;
            }
        }

        // Check diagonals
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }

        return false;
    }
}
