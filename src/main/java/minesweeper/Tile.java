package minesweeper;



import javax.swing.JButton;

public class Tile extends JButton {
    int row;
    int column;


    public Tile(int r, int c) {
        this.row = r;
        this.column = c;
  
    }
}
