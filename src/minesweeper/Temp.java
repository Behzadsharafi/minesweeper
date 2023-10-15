package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Temp {

    private static final int BOARD_SIZE = 8;
    private static final int NUM_MINES = 10;
    private static char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private static char[][] mines = new char[BOARD_SIZE][BOARD_SIZE];
    private static boolean[][] revealed = new boolean[BOARD_SIZE][BOARD_SIZE];
    private static boolean gameOver = false;

    public static void main(String[] args) {
        initializeGame();
        printBoard();

        while (!gameOver) {
            int[] move = getMove();
            int row = move[0];
            int col = move[1];

            if (mines[row][col] == '*') {
                System.out.println("Game over! You hit a mine!");
                gameOver = true;
            } else {
                revealCell(row, col);
                printBoard();
                checkWin();
            }
        }
    }

    private static void initializeGame() {
        // Initialize the board and mines
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = '.';
                mines[i][j] = '.';
                revealed[i][j] = false;
            }
        }

        // Place mines randomly on the board
        Random rand = new Random();
        for (int i = 0; i < NUM_MINES; i++) {
            int row, col;
            do {
                row = rand.nextInt(BOARD_SIZE);
                col = rand.nextInt(BOARD_SIZE);
            } while (mines[row][col] == '*');
            mines[row][col] = '*';
        }
    }

    private static void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (revealed[i][j]) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    private static int[] getMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        do {
            System.out.print("Enter your move (row and column): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
        } while (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE || revealed[row][col]);
        int[] move = {row, col};
        return move;
    }

    private static void revealCell(int row, int col) {
        int minesNearby = 0;
        revealed[row][col] = true;

        if (mines[row][col] == '*') {
            return;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < BOARD_SIZE && newCol >= 0 && newCol < BOARD_SIZE && mines[newRow][newCol] == '*') {
                    minesNearby++;
                }
            }
        }

        if (minesNearby == 0) {
            board[row][col] = ' ';
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newRow = row + i;
                    int newCol = col + j;
                    if (newRow >= 0 && newRow < BOARD_SIZE && newCol >= 0 && newCol < BOARD_SIZE && !revealed[newRow][newCol]) {
                        revealCell(newRow, newCol);
                    }
                }
            }
        } else {
            board[row][col] = (char) (minesNearby + '0');
        }
    }

    private static void checkWin() {
        int unrevealedCount = 0;
 
		for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!revealed[i][j]) {
                    unrevealedCount++;
                }
            }
        }
        if (unrevealedCount == NUM_MINES) {
            System.out.println("Congratulations! You win!");
            gameOver = true;
        }
    }

}
