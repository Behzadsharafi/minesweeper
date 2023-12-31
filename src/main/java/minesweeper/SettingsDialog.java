package minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;



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

        JButton confirmButton = new JButton("Let's Play!");

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

    void showDialog() {
        setVisible(true);
    }
}