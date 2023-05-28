import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args)
	{
		JFrame window = new JFrame("Sudoku");
		window.setSize(800,800);
		window.add(new Game());
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

}
