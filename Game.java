import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Game extends JPanel {

	/**
 		Author Brandon Jonathan Brown
	*/

    private JTextField[][] board = new JTextField[9][9];
    // Sample predefined puzzle (0s are empty cells to be filled by the user)
    private int[][] puzzle = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    public Game() {
        setLayout(new GridLayout(9, 9));
        initBoard();
    }

    private void initBoard() {
        Font font = new Font("Arial", Font.BOLD, 24);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(SwingConstants.CENTER);
                field.setFont(font);

                if (puzzle[i][j] != 0) {
                    field.setText(String.valueOf(puzzle[i][j]));
                    field.setEditable(false);
                } else {
                    field.addKeyListener(new SudokuKeyAdapter());
                }

                board[i][j] = field;
                add(field);
            }
        }
    }

    private class SudokuKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isDigit(c) || c == '0') {
                e.consume();
            }
        }
    }

    public boolean checkBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String text = board[row][col].getText();
                int num = text.isEmpty() ? 0 : Integer.parseInt(text);
                if (num == 0 || !isValid(row, col, num)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < 9; i++) {
            if (num == getIntFromField(row, i) || num == getIntFromField(i, col)) {
                return false;
            }
        }
        // Check 3x3 grid
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (num == getIntFromField(r, c)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getIntFromField(int row, int col) {
        String text = board[row][col].getText();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

public void resetBoard() {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (puzzle[i][j] == 0) { 
                board[i][j].setText("");
                board[i][j].setEditable(true);
            }
        }
    }
}

public static void main(String[] args) {
        
	SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sudoku");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);

            Game gamePanel = new Game();
            frame.add(gamePanel, BorderLayout.CENTER);

            JPanel controlPanel = new JPanel();
            JButton checkButton = new JButton("Check Solution");
            checkButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gamePanel.checkBoard()) {
                        JOptionPane.showMessageDialog(frame, "Congratulations, You've solved the puzzle!", "Sudoku", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Incorrect solution, try again.", "Sudoku", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            
            JButton resetButton = new JButton("Reset");
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gamePanel.resetBoard();  // Implement this method to reset the game
                }
            });

            controlPanel.add(checkButton);
            controlPanel.add(resetButton);
            frame.add(controlPanel, BorderLayout.SOUTH);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    
}
