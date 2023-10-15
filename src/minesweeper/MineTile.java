package minesweeper;

import java.awt.Color;

import javax.swing.JButton;

public class MineTile extends JButton {
    int row;
    int column;


    public MineTile(int r, int c) {
        this.row = r;
        this.column = c;
  
    }
}
