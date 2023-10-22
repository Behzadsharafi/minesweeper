package minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import javax.swing.ImageIcon;

public class Minesweeper {

	int tileSize = 70;
	int numRows;
	int numCols;
	int boardWidth;
	int boardHeight;
	int mineCount;

	JFrame frame = new JFrame("Minesweeper");
	JLabel textLabel = new JLabel();
	JPanel textPanel = new JPanel();
	JPanel boardPanel = new JPanel();

	Tile[][] board;
	ArrayList<Tile> mineList;
	Random random = new Random();

	int tilesClicked = 0;
	boolean gameOver = false;

	JButton resetButton = new JButton("Reset");
	JPanel buttonPanel = new JPanel();

	Minesweeper(int rows, int columns, int mines) {
		this.numRows = rows;
		this.numCols = columns;
		this.mineCount = mines;
		this.boardWidth = numCols * tileSize;
		this.boardHeight = numRows * tileSize;
		this.board = new Tile[numRows][numCols];
		// frame.setVisible(true);
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		textLabel.setFont(new Font("Arial", Font.BOLD, 25));
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		textLabel.setText("Mines: " + Integer.toString(mineCount) + " | Flags: 0");
		textLabel.setOpaque(true);

		textPanel.setLayout(new BorderLayout());
		textPanel.add(textLabel);
		frame.add(textPanel, BorderLayout.NORTH);

		boardPanel.setLayout(new GridLayout(numRows, numCols));
		frame.add(boardPanel);

		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGame();
			}
		});

		buttonPanel.add(resetButton);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
	

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				Tile tile = new Tile(r, c);
				board[r][c] = tile;
			

				tile.setFocusable(false);
				tile.setMargin(new Insets(0, 0, 0, 0));
				tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
				tile.addMouseListener(new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent e) {
						if (gameOver) {
							return;
						}
						Tile tile = (Tile) e.getSource();

						// Left Click
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (tile.getIcon() == null) {
								if (mineList.contains(tile)) {
									revealMines();
								} else {
									checkMine(tile.row, tile.column);
								}
							}
						}
						// Right Click
						else if (e.getButton() == MouseEvent.BUTTON3) {
							if (tile.getIcon() == null && tile.isEnabled()) {
								ImageIcon flagIcon = new ImageIcon(getClass().getResource("/flag.png"));
								Image image = flagIcon.getImage();
								int iconWidth = tile.getWidth() - 10;
								int iconHeight = tile.getHeight() - 10;
								Image scaledImage = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
								ImageIcon scaledFlagIcon = new ImageIcon(scaledImage);
								tile.setIcon(scaledFlagIcon);
								int flagsPlaced = countFlags();
								textLabel.setText("Mines: " + mineCount + " | Flags: " + flagsPlaced);
							} else {
								tile.setIcon(null);
								int flagsPlaced = countFlags();
								textLabel.setText("Mines: " + mineCount + " | Flags: " + flagsPlaced);
							}
						}
					}
				});

				boardPanel.add(tile);

			}
		}

		frame.setVisible(true);

		setMines();

	

	}

	void setMines() {
		mineList = new ArrayList<Tile>();
		
		 int totalTiles = numRows * numCols;
		 
		  if (mineCount >= totalTiles) {
		        mineCount = totalTiles - 1; 
		    }

		int mineLeft = mineCount;
		while (mineLeft > 0) {
			int r = random.nextInt(numRows);
			int c = random.nextInt(numCols);

			Tile tile = board[r][c];
			if (!mineList.contains(tile)) {
				mineList.add(tile);
				mineLeft -= 1;
			}
		}
	}

	void revealMines() {
		for (int i = 0; i < mineList.size(); i++) {
			Tile tile = mineList.get(i);
			ImageIcon bombIcon = new ImageIcon(getClass().getResource("/bomb.png"));
			Image image = bombIcon.getImage();
			int iconWidth = tile.getWidth() - 10;
			int iconHeight = tile.getHeight() - 10;
			Image scaledImage = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
			ImageIcon scaledBombIcon = new ImageIcon(scaledImage);
			tile.setIcon(scaledBombIcon);
		}

		gameOver = true;
		textLabel.setText("Game Over!");

	}

	void checkMine(int r, int c) {
		if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
			return;
		}

		Tile tile = board[r][c];
		if (!tile.isEnabled()) {
			return;
		}
		tile.setEnabled(false);
		tilesClicked += 1;

		int minesFound = 0;

		// top 3
		minesFound += countMine(r - 1, c - 1); // top left
		minesFound += countMine(r - 1, c); // top
		minesFound += countMine(r - 1, c + 1); // top right

		// left and right
		minesFound += countMine(r, c - 1); // left
		minesFound += countMine(r, c + 1); // right

		// bottom 3
		minesFound += countMine(r + 1, c - 1); // bottom left
		minesFound += countMine(r + 1, c); // bottom
		minesFound += countMine(r + 1, c + 1); // bottom right

		if (minesFound > 0) {
			tile.setText(Integer.toString(minesFound));
		} else {
			if (tile.getIcon() != null && minesFound == 0) {
				tile.setIcon(null);
			}
			tile.setText("");

			// top 3
			checkMine(r - 1, c - 1); // top left
			checkMine(r - 1, c); // top
			checkMine(r - 1, c + 1); // top right

			// left and right
			checkMine(r, c - 1); // left
			checkMine(r, c + 1); // right

			// bottom 3
			checkMine(r + 1, c - 1); // bottom left
			checkMine(r + 1, c); // bottom
			checkMine(r + 1, c + 1); // bottom right
		}

		if (tile.getIcon() != null && minesFound == 0) {
			tile.setIcon(null);
		}

		if (tilesClicked == numRows * numCols - mineList.size()) {
			gameOver = true;
			textLabel.setText("Mines Cleared!");
		}

	}

	void resetGame() {
		tilesClicked = 0;
		gameOver = false;
		mineList.clear();
		setMines();
		textLabel.setText("Mines: " + Integer.toString(mineCount) + " | Flags: 0");

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				Tile tile = board[r][c];
				tile.setEnabled(true);
				tile.setText("");
				tile.setIcon(null);
			}
		}
	}

	int countMine(int r, int c) {
		if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
			return 0;
		}
		if (mineList.contains(board[r][c])) {
			return 1;
		}
		return 0;
	}

	private int countFlags() {
		int flagsPlaced = 0;
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				Tile tile = board[r][c];
				if (tile.getIcon() != null) {
					flagsPlaced++;
				}
			}
		}
		return flagsPlaced;
	}

}
