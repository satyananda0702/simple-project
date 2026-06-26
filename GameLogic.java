package game;

import java.util.ArrayList;
import java.util.Random;
//gameLogic digunakan untuk mengatur logika permainan Tic Tac Toe
public class GameLogic {

    private char[] board;
    private Random random;

    public GameLogic() {
        board = new char[9];
        random = new Random();
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    public boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= 9) return false;
        if (board[index] != ' ') return false;
        board[index] = symbol;
        return true;
    }

    public boolean checkWinner(char symbol) {
        int[][] patterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        for (int i = 0; i < patterns.length; i++) {
            int a = patterns[i][0];
            int b = patterns[i][1];
            int c = patterns[i][2];
            if (board[a] == symbol && board[b] == symbol && board[c] == symbol) {
                return true;
            }
        }
        return false;
    }

    public boolean isDraw() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') return false;
        }
        return true;
    }
//Select an empty cell for computer move.
    //ini untuk menentukan gerakan komputer
    public int computerMove() {
        // Prioritas 1: coba menang
        int winning = findBestMove('O');
        if (winning != -1) return winning;

        // Prioritas 2: blok pemain
        int blocking = findBestMove('X');
        if (blocking != -1) return blocking;

        // Prioritas 3: ambil tengah
        if (board[4] == ' ') return 4;

        // Prioritas 4: acak
        ArrayList<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') emptyCells.add(i);
        }
        if (!emptyCells.isEmpty()) {
            return emptyCells.get(random.nextInt(emptyCells.size()));
        }
        return -1;
    }

    private int findBestMove(char symbol) {
        int[][] patterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        for (int i = 0; i < patterns.length; i++) {
            int a = patterns[i][0];
            int b = patterns[i][1];
            int c = patterns[i][2];
            if (board[a] == symbol && board[b] == symbol && board[c] == ' ') return c;
            if (board[a] == symbol && board[c] == symbol && board[b] == ' ') return b;
            if (board[b] == symbol && board[c] == symbol && board[a] == ' ') return a;
        }
        return -1;
    }

    public char[] getBoard() {
        return board;
    }
}