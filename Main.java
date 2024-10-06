import javax.swing.JFrame;

public class Main implements Runnable {
	
	private Canvas canvas = new Canvas();
	private Menu menu = new Menu();
	
	
	public static void main(String [] args)
	{
		Thread thread = new Thread(new Main());
		thread.start();
	}
	
	
	public JFrame window()
	{
		
		JFrame window = new JFrame("Ping Pong Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(canvas.getWidth(), canvas.getHeight());
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setJMenuBar(Menu.createMenuBar());
		window.add(canvas);
		

		return window;
	}
	
	

	@Override
	public void run() {
			
		window().setVisible(true);
		
	}

}
