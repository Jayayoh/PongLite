//**************************************
//**          John Omeljaniuk         **
//**          October 29 2011         **
//**              PC Pong             **
//**  To be converted for Android use **
//**************************************

/*
	CardLayout cl = (CardLayout)(cards.getLayout());
    cl.show(cards, (String)evt.getItem());
*/

//Imports

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Collision.*;
import MouseBounds.*;
import java.io.*;
import javax.sound.sampled.*;



//Main Class

class Main extends JFrame implements KeyListener
{

	static int gameWidth = 1000; //the game's width
	static int gameHeight = 500; //the Game's Height
	static JPanel cards = new JPanel(new CardLayout()); // this will organize JPanles, and allow for dynamic switching
	static Toolkit tool = Toolkit.getDefaultToolkit(); //this will allow the importing of images
	static int lvl = 0;
	static ball mainBall = new ball(225,225,35,35,5,5);
	static subBall titleBall = new subBall(225,225,35,35,5,5);
	static player player_1 = new player(5,50,175,25,125,1);
	static player player_2 = new player(5,900,175,25,125,2);

	public static void main(String[] args)
	{
		//Create an instance of the main class(window)
		Main window = new Main();
	}
	
	//constructor -- set the windows parameters, add Panels
	public Main()
	{
		super("Pong"); //give the window a sub-title
		setSize(gameWidth,gameHeight); // set the windoe's height
		setDefaultCloseOperation(EXIT_ON_CLOSE); //allow the window to properly close
		setVisible(true); //make the window visible
		setResizable(false); //make the window "unresiable"
		add(cards); //add the organizer to the main game
		cards.add(new MainScreen(),"title"); //add the mainscreen to the organizer
		cards.add(new lvl_1(),"lvl1"); //add the first level to the organizer
		this.addKeyListener(this); //add key listener to the program
		setFocusable(true); //allow the program to grab the focus
	}
	
	//get key input from the computer
	public void keyPressed(KeyEvent e)
	{
		if(lvl_1.Enabled == true)
		{
			if(e.getKeyCode() == 83)
			{
				player_1.down = true;
			}
			else if(e.getKeyCode() == 87)
			{
				player_1.up = true;
			}
			
			if(e.getKeyCode() == 38)
			{
				player_2.up = true;
			}
			else if(e.getKeyCode() == 40)
			{
				player_2.down = true;
			}
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
	}
			
	public void keyReleased(KeyEvent e)
	{
		if(lvl_1.Enabled == true)
		{
			if(e.getKeyCode() == 83)
			{
				player_1.down = false;
			}
			else if(e.getKeyCode() == 87)
			{
				player_1.up = false;
			}
			
			if(e.getKeyCode() == 38)
			{
				player_2.up = false;
			}
			else if(e.getKeyCode() == 40)
			{
				player_2.down = false;
			}
		}
	}
}	

//Main Title Screen
	class MainScreen extends JPanel implements MouseListener
	{
			//variables
			Image title = Main.tool.getImage("Pong_title.png"); //get the title image
			Image start = Main.tool.getImage("pong_start.png"); //get the start button image (width : 150, height : 50)
			Image multi = Main.tool.getImage("pong_multi.png"); //get the image of the multiplayer button (width : 300, height : 50)
			static boolean Enabled = true;
			
			//start button integers
			static int startx = 220;
			static int starty = 200;
			
			//multiplayer button integers
			static int multix = 480;
			static int multiy = 200;
	
			//constructor
			public MainScreen()
			{
				setLayout(null);
				setVisible(true); //make the JPanel visible
				setFocusable(true); //make the JPanel focusable
				update.start(); //start the timer!
				addMouseListener(this);
			}
			
			public void mouseEntered( MouseEvent e ) 
			{
			}
			public void mouseExited( MouseEvent e ) 
			{
			}
			public void mouseClicked( MouseEvent e ) 
			{
			}
			public void mousePressed( MouseEvent e ) 
			{
				if(mouseBounds.mouseCollide(e.getX(),e.getY(),startx,starty,150,50) == true)
				{
					CardLayout cl = (CardLayout)(Main.cards.getLayout()); cl.show(Main.cards, "lvl1"); //get the next "card" in the layout
					Enabled = false; //tell the program that the mainscreen is not open
					lvl_1.Enabled = true; //tell the program that the game is open
					Main.mainBall.reset(); //reset the ball's location
					lvl_1.Timer_Enabled = false; //stop the timer
					Main.mainBall.resetTimer.start(); //give 1 second for the players to get ready
					Main.player_2.dy = 3; //set the second player's speed to three for the AI
					Main.lvl = 1; // tell the main program that the lvl is 1
					
					//reset the scores
					Main.player_1.resetScore();
					Main.player_2.resetScore();
					
					//allow the pause button to work
					lvl_1.pausable = true;
				}
				else if(mouseBounds.mouseCollide(e.getX(),e.getY(),multix,multiy,300,50) == true)
				{
					CardLayout cl = (CardLayout)(Main.cards.getLayout()); cl.show(Main.cards, "lvl1"); //get the next "card" in the layout
					Enabled = false; //tell the program that the mainscreen is not open
					lvl_1.Enabled = true; //tell the program that the game is open
					Main.mainBall.reset(); //reset the ball's location
					lvl_1.Timer_Enabled = false; //stop the timer
					Main.mainBall.resetTimer.start(); //give the players 1 second
					Main.player_2.dy = 5; //set the second players's speed to five for multiplayer
					Main.lvl = 2; //tell the program that the lvl is 2
					
					//reset the scores
					Main.player_1.resetScore();
					Main.player_2.resetScore();
					
					//allow the pause button to work
					lvl_1.pausable = true;
				}
			}
			public void mouseReleased( MouseEvent e ) 
			{  
			}
			
			//paint method
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g); //allows the user to paint manually
				g.setColor(Color.black); //draw a background
				g.fillRect(0,0,Main.gameWidth,Main.gameHeight);
				g.drawImage(title,300,10,this); //draw the title
				g.drawImage(start,startx,starty,this); //draw the start button
				g.drawImage(multi,multix,multiy,this); //draw the multiplayer button
				
				//call the other class's paint functions
				Main.titleBall.paintBall(g);
			}
			
			//swing timer, all of the game will run off this (at least for the main screen);
			Timer update = new Timer(10,new ActionListener(){public void actionPerformed(ActionEvent e){
				if(Enabled == true)
				{
					Main.titleBall.move();
					repaint();
				}
			}});
	}
	
	//the gameplay JPanel
	class lvl_1 extends JPanel implements MouseListener
	{
		//variables
		Image menu = Main.tool.getImage("pong_menu.png"); //get the image for the menu (width : 115, height : 50)
		Image pause = Main.tool.getImage("pong_pause.png"); //get the image of the pause button (width : 25, height : 50)
		static boolean Enabled = false; //the program sees that this JPanel isnt active
		static boolean Timer_Enabled = false;
		static boolean pausable = false;
		private static int menux = 450;  //the coordinates of the menu
		private static int menuy = 2; //the coordinates of the menu
		private static int pausex = 415; //the coordinates of the pause button
		private static int pausey = 4; //the coordinates of the pause button
		static Clip clip;
		
		//constructor
			public lvl_1()
			{
				setVisible(true); //make the JPanel visible
				setLayout(null); //go to absolute positioning
				update.start(); //start the update timer
				setFocusable(true); //this will allow us to get focus
				addMouseListener(this);
				try
				{
					File soundFile = new File("beep-7.wav");
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
					clip = AudioSystem.getClip();
					clip.open(audioIn);
				}
				catch(Exception e)
				{
					
				}
			}
			
			public void mouseEntered( MouseEvent e ) 
			{
			}
			public void mouseExited( MouseEvent e ) 
			{
			}
			public void mouseClicked( MouseEvent e ) 
			{
			}
			public void mousePressed( MouseEvent e ) 
			{
				//check to see if the menu button was clicked, and act accordingly
				if(mouseBounds.mouseCollide(e.getX(),e.getY(),menux,menuy,150,50) == true)
				{
					CardLayout cl = (CardLayout)(Main.cards.getLayout()); cl.show(Main.cards, "title"); //get the next "card" in the layout
					MainScreen.Enabled = true;
					Enabled = false;
					
				}
				//check to see if the pause button was clicked, and act accordingly
				if(mouseBounds.mouseCollide(e.getX(),e.getY(),pausex,pausey,25,50) == true && pausable == true)
				{
					Timer_Enabled = !Timer_Enabled;
				}
				
				
			}
			public void mouseReleased( MouseEvent e ) 
			{  
			}

			//paint method
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g); //allows the user to paint manually
				g.setColor(Color.black);
				g.fillRect(0,0,Main.gameWidth,Main.gameHeight);
				g.setColor(Color.white);
				g.fillRect(0,55,Main.gameWidth,3);
				g.fillRect(Main.gameWidth / 2, 55,3,Main.gameHeight - 55);
				g.drawImage(menu,menux,menuy,this); //draw the menu button
				g.drawImage(pause,pausex,pausey,this); //draw the pause button
				g.drawImage(Main.player_1.scoreSprite,10,2,this); //draw the first player's score
				g.drawImage(Main.player_2.scoreSprite, 900,2,this); //draw the second player's score
				
				//call the other classes paint methods
				Main.mainBall.paintBall(g);
				Main.player_1.paintPaddle(g);
				Main.player_2.paintPaddle(g);
				scoreIMG.endGame(g,scoreIMG.victor);
			}
			
			//swing timer, all of the game will run off this (at least for the main screen);
			Timer update = new Timer(10,new ActionListener(){public void actionPerformed(ActionEvent e){
				if(Enabled == true && Timer_Enabled == true) //check to see if the timer actually does something...
				{
					Main.mainBall.move(); //move the ball
					Main.player_1.move(); //move player 1
					if(Main.lvl == 1)
					{
						Main.player_2.AI(); //move player 2
					}
					else if(Main.lvl == 2)
					{
						Main.player_2.move();
					}
				}
				repaint(); //repaint the screen
			}});
	}
	
	class ball
	{
		int x; //the game ball's horizontal coordinate
		int y; //the game ball's vertical coordinate
		int width; //the game ball's width
		int height; //the game ball's height
		int dx; //the game ball's horizontal speed
		int dy; //the game ball's vertical speed
		
		public ball(int px, int py, int pwidth, int pheight, int pdx, int pdy)
		{
			x = px;
			y = py;
			width = pwidth;
			height = pheight;
			dx = pdx;
			dy = pdy;
		}
		
		//paint method
		public void paintBall(Graphics g)
		{
			g.setColor(Color.white);
			g.fillOval(x,y,width,height); //draw the ball
		}
		
		public void bounds()
		{
			if(x <= 0) //the left
			{
				reset();
				Main.player_2.updateScore(1);
				//lvl_1.Timer_Enabled = false;
				//resetTimer.start();
			}
			else if(x + width + 10 >= Main.gameWidth) //the right
			{
				reset();
				Main.player_1.updateScore(1);
				//lvl_1.Timer_Enabled = false;
				//resetTimer.start();
			}
			
			if(y <= 55) //the top
			{
				y += 4; //fix the glitch
				dy = -dy;
			}
			else if(y + height + 25 >= Main.gameHeight) //the bottom
			{
				y -= 4; //fix the glitch
				dy = -dy;
			}
			
			//check collision of ball with paddles
			if(collision.checkCollide(x,y,width,height,Main.player_1.x,Main.player_1.y,Main.player_1.width,Main.player_1.height) == true)
			{
				if(collision.checkLocationRight(x,y,width,height,Main.player_1.x,Main.player_1.y,Main.player_1.width,Main.player_1.height) == true)
				{
					dx = -dx;
					randomSpeed(dx,dy);
					x = Main.player_1.x + Main.player_1.width + 2;
					
				}
				
				if(collision.checkLocationTop(x,y,width,height,Main.player_1.x,Main.player_1.y,Main.player_1.width,Main.player_1.height) == true)
				{

					y = Main.player_1.y - height - 2;
					
					if(Main.player_1.up == true)
					{
						dy = -Main.player_1.dy - dy;
					}
					else
					{
						dy = -dy;
					}

				}
				else if(collision.checkLocationBottom(x,y,width,height,Main.player_1.x,Main.player_1.y,Main.player_1.width,Main.player_1.height) == true)
				{
					y = Main.player_1.y + Main.player_1.height + 2;
					
					if(Main.player_1.down == true)
					{
						dy = Main.player_1.dy + dy;
					}
					else
					{
						dy = -dy;
					}
				}
				
				lvl_1.clip.stop(); //stop the clip
				lvl_1.clip.setFramePosition(0); //rewind the clip
				lvl_1.clip.start(); //start the clip
			}
			else if(collision.checkCollide(x,y,width,height,Main.player_2.x,Main.player_2.y,Main.player_2.width,Main.player_2.height) == true)
			{
				if(collision.checkLocationLeft(x,y,width,height,Main.player_2.x,Main.player_2.y,Main.player_2.width,Main.player_2.height) == true)
				{
					dx = -dx;
					randomSpeed(dx,dy);
					x = Main.player_2.x - width - 2;
				}
				
				if(collision.checkLocationTop(x,y,width,height,Main.player_2.x,Main.player_2.y,Main.player_2.width,Main.player_2.height) == true)
				{
					y = Main.player_2.y - height - 2;
					
					if(Main.player_2.up == true)
					{
						dy = -Main.player_2.dy - dy;
					}
					else
					{
						dy = -dy;
					}
					
				}
				else if(collision.checkLocationBottom(x,y,width,height,Main.player_2.x,Main.player_2.y,Main.player_2.width,Main.player_2.height) == true)
				{
					y = Main.player_2.y + Main.player_2.height + 2;
					
					
					if(Main.player_2.down == true)
					{
						dy = Main.player_2.dy + dy;
					}
					else
					{
						dy = -dy;
					}
				}
				
				lvl_1.clip.stop(); //stop the clip
				lvl_1.clip.setFramePosition(0); //rewind the clip
				lvl_1.clip.start(); //start the clip
			}
		}
		
		//make a randomizer for the ball's speed
		public void randomSpeed(int xspeed, int yspeed)
		{
		
			if(xspeed > 0) //if the ball's speed is posotive
			{
				dx = (int)Math.ceil(Math.random() * 5) + 2;
			}
			else if(xspeed < 0) //if the ball's speed is negative
			{
				dx = (int)Math.ceil(Math.random() * 3) + 2;
				dx = -dx;
			}
			
			if(yspeed > 0) //if the ball's speed is posotive
			{
				dy = (int)Math.ceil(Math.random() * 5) + 2;
			}
			else if(yspeed < 0) //if the ball's speed is negative
			{
				dy = (int)Math.ceil(Math.random() * 5) + 2;
				dy = -dy;
			}
		}
		
		//move the ball
		public void move()
		{
			//check the bounds
			bounds();
			
			x += dx;
			y += dy;
		}
		
		//reset the ball's location
		public void reset()
		{
			x = (Main.gameWidth / 2) - (width / 2); 
			y = (Main.gameHeight / 2) - (height / 2); ;
			randomSpeed(dx,dy); //reset the ball's speed for excitement!
			dx = -dx; //reverse the ball's direction
			
			//reset the players paddles
			
			Main.player_1.y = 200;
			Main.player_2.y = 200;
		}
		
		static Timer resetTimer = new Timer(1000,new ActionListener(){public void actionPerformed(ActionEvent e){
			lvl_1.Timer_Enabled = true;
			resetTimer.stop();
		}});
	}
	
	class subBall
	{
		int x; //the game ball's horizontal coordinate
		int y; //the game ball's vertical coordinate
		int width; //the game ball's width
		int height; //the game ball's height
		int dx; //the game ball's horizontal speed
		int dy; //the game ball's vertical speed
		
		public subBall(int px, int py, int pwidth, int pheight, int pdx, int pdy)
		{
			x = px;
			y = py;
			width = pwidth;
			height = pheight;
			dx = pdx;
			dy = pdy;
		}
		
		//paint method
		public void paintBall(Graphics g)
		{
			g.setColor(Color.white);
			g.fillOval(x,y,width,height); //draw the ball
		}
		
		public void bounds()
		{
			if(x <= 0) //the left
			{
				dx = -dx;
			}
			else if(x + width + 10 >= Main.gameWidth) //the right
			{
				dx = -dx;
			}
			
			if(y <= 0) //the top
			{
				dy = -dy;
			}
			else if(y + height + 25 >= Main.gameHeight) //the bottom
			{
				dy = -dy;
			}
		}
		
		//make a randomizer for the ball's speed
		public void randomSpeed(int xspeed, int yspeed)
		{
		
			if(xspeed > 0) //if the ball's speed is posotive
			{
				dx = (int)Math.ceil(Math.random() * 5);
				
				if(dx < 3)
				{
					dx = 3;
				}
			}
			else if(xspeed < 0) //if the ball's speed is negative
			{
				dx = (int)Math.ceil(Math.random() * 3);
				dx = -dx;
				
				if(dx > 3)
				{
					dx = -3;
				}
			}
			
			if(yspeed > 0) //if the ball's speed is posotive
			{
				dy = (int)Math.ceil(Math.random() * 5);
				
				if(dy < 3)
				{
					dy = 3;
				}
			}
			else if(yspeed < 0) //if the ball's speed is negative
			{
				dy = (int)Math.ceil(Math.random() * 5);
				dy = -dy;
				
				if(dy > 3)
				{
					dy = -3;
				}
			}
		}
		
		//move the ball
		public void move()
		{
			//check the bounds
			bounds();
			
			x += dx;
			y += dy;
		}
	}
	
	class player
	{
		int x; //the paddle's horizontal location
		int y; //the paddle's verticle location
		int width; //the paddle's width
		int height; //the paddle's height
		int dy; //the paddle's verticle speed
		int score = 0;
		int ID;
		Image scoreSprite = scoreIMG.numbers[0];
		boolean up = false;
		boolean down = false;
		
		public player(int pdy,int px, int py, int pwidth, int pheight,int pID)
		{
			dy = pdy;
			x = px;
			y = py;
			width = pwidth;
			height = pheight;
			ID = pID;
		}
		
		//paint method
		public void paintPaddle(Graphics g)
		{
			g.setColor(Color.white);
			g.fillRect(x,y,width,height); //paint the paddle on the screen
		}
		
		//give the paddle boundaries
		public void bounds()
		{
			if(y <= 55) //top
			{
				y = 55;
			}
			
			if(y + height >= Main.gameHeight - 30) //bottom
			{
				y = Main.gameHeight - height - 30;
			}
		}
		
		public void AI()
		{
			//check boundaries
			bounds();
		
			if(y + (height / 2) > Main.mainBall.y)
			{
				y -= dy;
			}
			else if(y + (height / 2) < Main.mainBall.y)
			{
				y += dy;
			}
		}
		
		public void move()
		{
			//check boundaries
			bounds();
	
			if(down == true) //check to see if you can move up via key input
			{
				y += dy;
			}
			else if(up == true) //check to see if you can move down via key input
			{
				y -= dy;
			}
		}
		
		public void updateScore(int increment)
		{
			score += increment;
			
			if(score <= 9)
			{
				scoreSprite = scoreIMG.numbers[score];
				lvl_1.Timer_Enabled = false;
				ball.resetTimer.start();
			}
			
			//win or lose the game
			if(score > 9)
			{
				scoreIMG.victor = ID;
			}
		}
		
		public void resetScore()
		{
			score = 0;
			scoreSprite = scoreIMG.numbers[0];
			scoreIMG.victor = 0;
		}
		
	}
	
	class scoreIMG
	{
		
		static int victor = 0;

		//import all of the score images
		static Image numbers[] = {
			Main.tool.getImage("pong_0.png"), 
			Main.tool.getImage("pong_1.png"), 
			Main.tool.getImage("pong_2.png"), 
			Main.tool.getImage("pong_3.png"), 
			Main.tool.getImage("pong_4.png"), 
			Main.tool.getImage("pong_5.png"), 
			Main.tool.getImage("pong_6.png"),
			Main.tool.getImage("pong_7.png"),
			Main.tool.getImage("pong_8.png"),
			Main.tool.getImage("pong_9.png")
		};
		
		static Image winner = Main.tool.getImage("pong_winner.png");
		static Image loser = Main.tool.getImage("pong_loser.png");
		
		public static void endGame(Graphics g,int victor)
		{
			if(victor == 0)
			{
				//do nothing
			}
			else if(victor == 1)
			{
				g.drawImage(winner,Main.gameWidth / 2 - 250, 200,null);
				g.drawImage(loser,Main.gameWidth / 2 + 10, 200,null);
				lvl_1.Timer_Enabled = false;
				lvl_1.pausable = false;
			}
			else if(victor == 2)
			{
				g.drawImage(loser,Main.gameWidth / 2 - 250, 200,null);
				g.drawImage(winner,Main.gameWidth / 2 + 10, 200,null);
				lvl_1.Timer_Enabled = false;
				lvl_1.pausable = false;
			}
		}
	}
