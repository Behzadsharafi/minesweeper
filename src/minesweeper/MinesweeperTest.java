package minesweeper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MinesweeperTest {

	private int countMines(Minesweeper minesweeper) {
		int mineCount = 0;
		Tile[][] board = minesweeper.board;
		for (int r = 0; r < minesweeper.numRows; r++) {
			for (int c = 0; c < minesweeper.numCols; c++) {
				if (minesweeper.mineList.contains(board[r][c])) {
					mineCount++;
				}
			}
		}
		return mineCount;
	}

	@Test
	void testMinePlacement() {
		Minesweeper minesweeper = new Minesweeper(5, 5, 5);
		int mineCount = countMines(minesweeper);
		assertEquals(5, mineCount, "Incorrect number of mines placed");

	}

	@Test
	void testGameReset() {
		Minesweeper minesweeper = new Minesweeper(5, 5, 5);
		Tile[][] board = minesweeper.board;
		// Click on a tile
		board[0][0].doClick(1);
		minesweeper.resetGame();
		assertFalse(minesweeper.gameOver, "Game should not be over after reset");
		assertEquals(0, minesweeper.tilesClicked, "Tiles clicked should be reset");
		int mineCount = countMines(minesweeper);
		assertEquals(5, mineCount, "Mines should be reset");
	}

}
