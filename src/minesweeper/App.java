package minesweeper;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class App {
    public static void main(String[] args) {
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.showDialog(new SettingsDialogCallback() {
            @Override
            public void onSettingsConfirmed(int rows, int columns, int mines) {
            	 Minesweeper minesweeper = new Minesweeper(rows, columns, mines);
            }
        });
    }
}

class SettingsDialog extends JDialog {
    private JTextField rowsField;
    private JTextField columnsField;
    private JTextField minesField;

    SettingsDialog() {
        setTitle("Settings");
        setSize(300, 200);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    	setResizable(false);

        rowsField = new JTextField("8");
        columnsField = new JTextField("8");
        minesField = new JTextField("10");

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                int rows = Integer.parseInt(rowsField.getText());
                int columns = Integer.parseInt(columnsField.getText());
                int mines = Integer.parseInt(minesField.getText());
                Minesweeper minesweeper = new Minesweeper(rows, columns, mines);
            }
        });

        add(new JLabel("Rows:"));
        add(rowsField);
        add(new JLabel("Columns:"));
        add(columnsField);
        add(new JLabel("Mines:"));
        add(minesField);
        add(confirmButton);
        setLocationRelativeTo(null);
    }

    void showDialog(SettingsDialogCallback callback) {
        setVisible(true);
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                int rows = Integer.parseInt(rowsField.getText());
                int columns = Integer.parseInt(columnsField.getText());
                int mines = Integer.parseInt(minesField.getText());
                callback.onSettingsConfirmed(rows, columns, mines);
            }
        });
        add(confirmButton);
    }
}

interface SettingsDialogCallback {
    void onSettingsConfirmed(int rows, int columns, int mines);
}
