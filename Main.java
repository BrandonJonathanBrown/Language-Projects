import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements Runnable{
	
	private final static int width = 800;
	private final static int height = 800;
	private final static int size = 25;
	private static int index = 0, counter = 0, oddeven = 0, evenodd = 0;
	private static int Camera = 0, playerscore = 1, enemyscore = 1;
	private static boolean attack = false;
	private static Circle[] background = new Circle[800/size];
	private static Pane pane = new Pane();
	private static ImageView player;
	private static ImageView[] enemy = new ImageView[size];
	private static Rectangle c,e;
	private static ArrayList<ImageView> rockets = new ArrayList<ImageView>();
	private static ArrayList<ImageView> oppeo = new ArrayList<ImageView>();
	private static ArrayList<ImageView> oppet = new ArrayList<ImageView>();
	private static int[] Y = new int[(int) Math.pow(size,4)];
	private static int[] P = new int[(int) Math.pow(size,4)];
	private static ImageView[] spaceship = new ImageView[3];
	
	public enum Colors
	{
		Red,
		Blue,
		Green,
		Yellow,
		Orange
	};
	
	public static void main(String[] args) {
		
		Thread thread = new Thread(new Main());
		thread.start();

	}
	
	public static Text Score(String name, int y)
	{
		Text text = new Text(name);
		Font font = new Font("Times New Roman", 18);
		text.setFont(font);
		text.setFill(Color.WHITE);
		text.setX(size * 10);
		text.setY(y);
		return text;
	}
	
	public static Rectangle Life(int r, int g, int b, int x, int y)
	{
		Rectangle Life = new Rectangle();
		Life.setFill(Color.rgb(r,g,b));
		Life.setHeight(size);
		Life.setWidth(size * 8);
		Life.setX(x);
		Life.setY(y);
		return Life;
	}
	
	public static void GameOver() throws InterruptedException
	{
		
		Text Game = new Text();
		
		if(e.getWidth() == 0)
		{
			Text text = new Text("Enemy Wins!");
			Font font = new Font("Times New Roman", 40);
			text.setFont(font);
			text.setFill(Color.WHITE);
			text.setX(width/3);
			text.setY(height/2);
			pane.getChildren().add(text);
			
		}
		if(c.getWidth() == 0)
		{
			Text text = new Text("Player Wins!");
			Font font = new Font("Times New Roman",40);
			text.setFont(font);
			text.setFill(Color.WHITE);
			text.setX(width/3);
			text.setY(height/2);
			pane.getChildren().add(text);
		}
		
	}
	
	
	
	public static void Collisition()
	{
		for(int j = 0; j < oppeo.size(); j++)
		{
			if(oppeo.get(j).getBoundsInParent().intersects(player.getBoundsInParent())) 
			{
				System.out.println("Player Hit!");
				e.setWidth(e.getWidth() - enemyscore);
			}
		}
		
		for(int j = 0; j < oppet.size(); j++)
		{
			if(oppet.get(j).getBoundsInParent().intersects(player.getBoundsInParent())) 
			{
				System.out.println("Player Hit!");
				e.setWidth(e.getWidth() - enemyscore);
			}
		}
		
		for(int j = 0; j < rockets.size(); j++)
		{
			if(rockets.get(j).getBoundsInParent().intersects(enemy[0].getBoundsInParent())) 
			{
				System.out.println("Enemy Hit!");
				c.setWidth(c.getWidth() - playerscore);
			}
			
			if(rockets.get(j).getBoundsInParent().intersects(enemy[1].getBoundsInParent())) 
			{
				System.out.println("Enemy Hit!");
				c.setWidth(c.getWidth() - playerscore);
			}
		}

	}
	
	public static void ScoreTrack(int playerscore, int enemyscore)
	{
		
		pane.getChildren().add(Score("Player", 45));
		pane.getChildren().add(Score("Enemy", 70));
		
	}

	public static void EnemyMovement(String direction, int index, int speed)
	{
		speed = 3;
		
		if(oddeven % 2 == 0)
		{
			if(index == 1)
				direction = "E";
			if(index == 0)
				direction = "W";
		}
		else
		{
			
			if(index == 1)
				direction = "W";
			if(index == 0)
				direction = "E";
			
		}
					
		
		if(enemy[index].getX() + speed < width && direction == "E")
		{	
			if(direction != "W")
			{
				
				enemy[index].setX(enemy[index].getX() + speed);
				enemy[index].setY(enemy[index].getY());
			}
		}
		
		if(enemy[index].getX() + speed >= width - size * 2)
		{
				direction = "W";
				oddeven++;
		}
		
		
		if(enemy[index].getX() + speed < 0)
		{
			direction = "E";
			oddeven++;
		}
		
		if(enemy[index].getX() + speed > 0 && direction == "W")
		{	
			if(direction != "E")
			{
				enemy[index].setX(enemy[index].getX() - speed);
				enemy[index].setY(enemy[index].getY());
			}
			
		}
		
	}
	
	
	public static void EnemyPosition() throws FileNotFoundException
	{
		enemy[0].setX(width);
		enemy[0].setY(height/5);
		enemy[0].setRotate(180);
		
		enemy[1].setX(width - width);
		enemy[1].setY(height/5);
		enemy[1].setRotate(180);

	}
	
	public static ImageView Enemy() throws FileNotFoundException
	{
		ImageView enemy = new ImageView(new Image(new FileInputStream("Enemy.png")));
	    enemy.setFitWidth(size * 1.5); 
	    enemy.setFitHeight(size * 1.5);
	    return enemy;
	}
	
	public static ImageView Rocket() throws FileNotFoundException
	{
		ImageView rocket = new ImageView(new Image(new FileInputStream("PWeapon.png")));
	    rocket.setFitWidth(size/2); 
	    rocket.setFitHeight(size * 2);
	    return rocket;
	}
	
	public static ImageView EnemyRocket() throws FileNotFoundException
	{
		ImageView rocket = new ImageView(new Image(new FileInputStream("EWeapon.png"))); 
	    rocket.setFitWidth(size/2.5); 
	    rocket.setFitHeight(size * 2);
	    return rocket;
	}
	
	public static ImageView[] Spaceship() throws FileNotFoundException
	{
		ImageView ship = new ImageView(new Image(new FileInputStream("Spaceship.png")));
		spaceship[1] = ship;
		ImageView left = new ImageView(new Image(new FileInputStream("Left.png")));
		spaceship[0] = left;
		ImageView right = new ImageView(new Image(new FileInputStream("Right.png")));
		spaceship[2] = right;
		
		for(ImageView i: spaceship)
		{
			  i.setFitHeight(size * 2);
			  i.setFitWidth(size * 2);
			  i.setX(-size * 4);
			  i.setY(-size * 4);
		}
	 
		return spaceship;
	    
	}
	
	public static Circle star()
	{
		Circle circle = new Circle();
		circle.setRadius(size/10);
		circle.setFill(Color.WHITESMOKE);
		return circle;
	}
	
	
	public static void Boundaries()
	{
		
		if(player.getX() >= width)
		{
			player.setX(width - size);
		}
		if(player.getX() <= 0)
		{
			player.setX(0);
		}
		
		if(player.getY() >= height)
		{
			player.setY(height - size);
		}
		if(player.getY() <= 0)
		{
			player.setY(0);
		}
		
		if(player.getY() == height / 2)
		{
			player.setY(height/2 + size * 4);
		}
		
		
	}

	
	public static Circle[] background()
	{
		for(int i = 0; i < background.length; i++)
		{
			background[i] = star();
		}
		
		for(int i = 0; i < background.length; i++)
			pane.getChildren().add(background[i]);
		
		return background;
		
	}
	
	public static void Arrangement()
	{
		for(int i = 0; i < background.length - 1; i++)
		{
			background[i].setTranslateX(Math.random() * ((800 - 1) + 1) + 1);
			background[i].setTranslateY(Math.random() * ((800 - 1) + 1) + 1);
		}
		
		background[background.length -1].setTranslateY(0);
		background[background.length -1].setTranslateX(-100);
	}
	
	
	public static void CreateEnemies() throws FileNotFoundException
	{
		for(int i = 0; i < enemy.length; i++)		
			enemy[i] = Enemy();

	
		for(int j = 0; j < 2; j++)
		pane.getChildren().add(enemy[j]);
	}
	
	public static Pane AddNodes() throws FileNotFoundException
	{
		
		background();
		
		Spaceship();
		
		for(int i = 0; i < spaceship.length; i++)
			pane.getChildren().add(spaceship[i]);
		
		c = Life(255,0,0,size,size * 2);
		pane.getChildren().add(c);
		
		e = Life(0,255,0,size,size);
		pane.getChildren().add(e);
		
		player = spaceship[1];
		
		player.setX(width/2);
		player.setY(height - size * 2);
		
		CreateEnemies();
		
		EnemyPosition();
		
		return pane;
		
	}
	
	public static void Camera()
	{
	
			for(int x = 0; x < background.length - 1; x++)
			{
					
				Camera = 15;				
				background[x].setTranslateY(background[x].getTranslateY() + Camera);
				
				
				if(background[x].getTranslateY() >= height + size * 4)
				{
					
					for(int j = 0; j < background.length - 1; j++)
					{
						background[j].setTranslateY(background[j].getTranslateY() -  height);
					}
				}
				
					
			}
		
	}
	
	
	public static void EnemyReload() throws FileNotFoundException
	{
		if(evenodd % Math.pow(2,4) == 0)
		{
			
			oppeo.add(Rocket());
			oppet.add(Rocket());
			
			pane.getChildren().add(oppeo.get(counter));		
			pane.getChildren().add(oppet.get(counter));		
			
			oppeo.get(counter).setX(enemy[0].getX());
			oppeo.get(counter).setY(enemy[0].getY() - size * size);
			
			oppet.get(counter).setX(enemy[1].getX());
			oppet.get(counter).setY(enemy[1].getY() - size * size);
			
			counter++;
			
			evenodd++;
		}
		else
		{
			evenodd++;
		}
		
		for(int p = 0; p < counter - 1; p++)
		{
			Y[p] += size;
			oppeo.get(p).setY(enemy[0].getY() + Y[p]);
			oppet.get(p).setY(enemy[1].getY() + Y[p]);
		
		}
	}
	
	
	public static void PlayerReload() throws FileNotFoundException
	{
				
		rockets.add(Rocket());
		
		System.out.println(index);
		
		pane.getChildren().add(rockets.get(index));		
		
		rockets.get(index).setX(player.getX());
		rockets.get(index).setY(-size * 2);
		
		index++;
	    attack = true;	
		
	}
	
	public static void PlayerAttack() throws FileNotFoundException
	{
		if(attack == true)
		{
			
			for(int p = 0; p < index - 1; p++)
			{
				P[p] += size;
				rockets.get(p).setY(player.getY() - P[p]);
				
			}
				
		}
		
	}
	
	
	public static void Image(int index)
	{
		
	}
	

	public static Scene root() throws FileNotFoundException
	{
		Scene root = new Scene(AddNodes(),width,height); 
		root.setFill(Color.BLACK);
	
		Arrangement();

		
		AnimationTimer Timer = new AnimationTimer() {
					
	      
			
			@Override
	        public void handle(long l) {
	        	
	        	root.setOnKeyPressed(new EventHandler<KeyEvent>()
	   		 {
	   		   @Override
	   		   public void handle(KeyEvent key)
	   		   {
	   		     if (key.getCode().equals(KeyCode.LEFT))
	   		     {
	   		       System.out.println("Left!");
	   		       
	   		       player = spaceship[0];
	   		       player.setX(spaceship[1].getX() - size);
	   		       spaceship[1].setY(-size * 4);
	   		       player.setY(height - size * 2);
	   		     }
	   		     if (key.getCode().equals(KeyCode.RIGHT))
	   		     {
	   		       System.out.println("Right!");
	   		       player = spaceship[2];
	   		       player.setX(spaceship[1].getX() + size);
	   		       spaceship[1].setY(-size * 4);
	   		       player.setY(height - size * 2);
		   		
	   		     }

	   		     if(key.getCode().equals(KeyCode.SPACE))
	   		     {
	   		    	
	   		    	System.out.println("Space!");
	   		    	
	   				try {
						PlayerReload();
					} catch (FileNotFoundException ex) {
						ex.printStackTrace();
					}
	   				          	
	   		     }
	   		   
	   		 };
	   		
	   		 });
	        	
	        	root.setOnKeyReleased(new EventHandler<KeyEvent>()
		   		 {
		   		   @Override
		   		   public void handle(KeyEvent key)
		   		   {
		   		     if (key.getCode().equals(KeyCode.LEFT))
		   		     {
		   		       try {
						Thread.sleep(1000/10);
					    System.out.println("Left!");
			   		    player = spaceship[1];
			   		    player.setX(spaceship[0].getX() - size);
			   		    spaceship[0].setY(-size * 4);
			   		    player.setY(height - size * 2);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
		   		

		   		     }
		   		     if (key.getCode().equals(KeyCode.RIGHT))
		   		     {
		   		    	 
		   		    try {
						Thread.sleep(1000/10);
					    System.out.println("Right!");
			   		    player = spaceship[1];
			   		    player.setX(spaceship[2].getX() + size);
			   		    spaceship[2].setY(-size * 4);
			   		    player.setY(height - size * 2);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
		   		     
		   		     }

		   		     if(key.getCode().equals(KeyCode.SPACE))
		   		     {
		   		    	
		   		    	System.out.println("Space!");
		   		    	
		   				try {
							PlayerReload();
						} catch (FileNotFoundException ex) {
							ex.printStackTrace();
						}
		   				          	
		   		     }
		   		   
		   		 };
		   		
		   		 });
		        	
	        	
	        		
	        		
	        	
	        	
	        	try {
		        		
	        			Camera();
	        			Collisition();
		        		Boundaries();
		        		ScoreTrack(playerscore,enemyscore);
		        		EnemyMovement(" ",1,2);
		        		EnemyMovement(" ",0,2);
		        		EnemyReload();
		        		PlayerAttack();
		        		GameOver();
		        		
		        		
		        	Thread.sleep(1000/60);
				
	        	} catch (InterruptedException | FileNotFoundException ex) { 
					ex.printStackTrace();
				}
	        	   	
	        	
	        }
	    };
	    Timer.start();
		
			return root;
	}


	@Override
	public void run() {
		Application.launch();
	}


	@Override
	public void start(Stage primarystage) throws Exception {
		
		primarystage.setResizable(false);
		primarystage.setScene(root()); 
		primarystage.setTitle("Space Invaders");
		primarystage.centerOnScreen();
		primarystage.show();
		
	}
	

}
