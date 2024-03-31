import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	public final static int DEFAULT_WIDTH = 600;
	public final static int DEFAULT_HEIGHT = 900;
	private int width;
	private int height;
	private int blockSize;
	private Point origin;
	private Stack<Point> origins;
	private int current, rotation;
	private boolean left = true, right = true;
	private int index = 0;
	private Color[][] board;
	private boolean collide = false;
	private boolean space;
	private boolean points = true;
	private boolean gameOver = false;
	private int fps = 1000 / 6;
	private final Timer timer;
	private int score, line;
	private int x, y;

	private final Point[][][] tetrominos = {
			{
					{new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
					{new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
					{new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
					{new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
			},

			{
					{new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
					{new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
					{new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
					{new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)}
			},


			{
					{new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
					{new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
					{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
					{new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)}
			},


			{
					{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
					{new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)},
					{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
					{new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)}
			},
			{
					{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
					{new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
					{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
					{new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
			},


			{
					{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},
					{new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
					{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)},
					{new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)}
			},


			{
					{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
					{new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)},
					{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0)},
					{new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)}
			},


	};

	private final Color[] colors = {
			Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
	};


	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.blockSize = height / 20;
		this.origin = new Point(0, 0);
		this.origins = new Stack<>();
		origins.add(origin);
		this.board = new Color[width / blockSize][height / blockSize];

		timer = new Timer(fps, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}
		});
		timer.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		init();
	}


	public void init() {
		for (int i = 0; i < width / blockSize; ++i) {
			for (int x = 0; x < height / blockSize; ++x) {
				board[i][x] = Color.BLACK;
			}
		}
	}

	public void board(Graphics2D g) {
		for (int i = 0; i < width / blockSize; ++i) {
			for (int j = 0; j < height / blockSize; ++j) {
				g.setColor(board[i][j]);
				g.fillRect(blockSize * i, blockSize * j, blockSize, blockSize);
			}
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, height / 36));
		g.drawString("Score " + score, (width - 200), height / 9);
		g.drawString("Lines " + line, (width - 200), height / 4);
	}

	public void check() {
		if (rotation >= 4)
			rotation = 0;

		if (current >= 7 || current < 0)
			current = 0;
	}

	public void collision() {
		check();

		for (Point p : tetrominos[current][rotation]) {
			int newX = p.x + origins.get(index).x;
			int newY = p.y + origins.get(index).y;


			if (newY + 1 >= height / blockSize || board[newX][newY + 1] != Color.BLACK) {
				collide = true;
				if (points)
					score += 150;
				if (!gameOver)
					newPiece();
			}

			if (newX + 1 < width / blockSize && board[newX + 1][newY] != Color.BLACK) {
				right = false;
			}

			if (newX - 1 >= 0 && board[newX - 1][newY] != Color.BLACK) {
				left = false;
			}

		}
	}



	public void bounds(Graphics2D g) {
		check();
		if (!collide)
			origins.get(index).y += 1;

		for (Point p : tetrominos[current][rotation]) {
			if ((p.x + origins.get(index).x) < 0) {
				origins.get(index).x = 0;
				left = false;
			}
			if ((p.x + origins.get(index).x) > width / blockSize - 1) {
				origins.get(index).x -= 1;
				right = false;
			}
			if ((p.x + origins.get(index).x) >= 0 && (p.x + origins.get(index).x) < width / blockSize - 1) {
				left = true;
				right = true;
			}

		}
	}

	public void gameOver(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, height / 15));
		g.drawString("Game Over", (width / 4), (height / 2));
	}

	public void delete(int row) {
		for (int j = row - 1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				board[i][j + 1] = board[i][j];
			}
		}
	}

	public void clear() {
		for (int j = height / blockSize - 1; j > 0; j--) {
			space = false;
			for (int i = 1; i < width / blockSize - 1; i++) {
				if (board[i][j] == Color.BLACK) {
					space = true;
					break;
				}
			}
			if (!space) {
				delete(j);
				j += 1;
				line += 1;
				if (points)
					score += 1000;
			}
		}
	}

	public void tetromino(Graphics2D g) {
		check();
		for (Point p : tetrominos[current][rotation]) {
			g.setColor(colors[current]);
			g.fillRect((p.x + origins.get(index).x) * blockSize, (p.y + origins.get(index).y) * blockSize, blockSize, blockSize);
		}
	}

	public void newPiece() {
		x = origins.get(index).x;
		y = origins.get(index).y;
		current++;
		if (current == 0)
			current = 7;
		for (Point f : tetrominos[current - 1][rotation]) {
			board[x + f.x][y + f.y] = colors[current - 1];
		}
		origins.get(index).setLocation(new Point(5, 0));
		clear();
		collide = false;
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);

		bounds(g2);
		collision();
		board(g2);

		if (!(gameOver))
			tetromino(g2);

		if (gameOver) {
			gameOver(g2);
			points = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			rotation += 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && right && !collide) {
			origins.get(index).x += 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && left && !collide) {
			origins.get(index).x -= 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			origins.get(index).y += 1;
		}
	}
}
