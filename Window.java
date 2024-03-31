import javax.swing.JFrame;

/***
 * 
 * @author Brandon Jonathan Brown
 *
 */

public class Window extends JFrame {

    private final Board board = new Board(600,900);
    public Window(String Title, int Width, int Height) 
    {

        this.setSize(Width,Height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addKeyListener(board);
        board.init();
        this.add(board);
        this.revalidate();
        this.setVisible(true);
    }


    public static void main(String[] args) {
        new Window("Tetris",Board.DEFAULT_WIDTH,Board.DEFAULT_HEIGHT);
    }

}
