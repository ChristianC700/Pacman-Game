import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.event.*;

public class Game extends JPanel implements ActionListener{//Pacman

	//declaration
	public static final int WIDTH = 800, HEIGHT = 800;
	public static final int WIDTH_GAME = 600, HEIGHT_GAME = 600;
	private Timer timer;
	private JButton returnToMenu, pauseBtn , newGame;
	private Map m;
	private Player p;
	private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
	public static boolean gameStarted = false, gameOver = false, mapChanged = false;
	private boolean hasWon;
	private Color myBlue = new Color(48,61,198);
	private int highScore = 0;
	private Font font1 = new Font("Serif", Font.PLAIN, 24);
	private Font font2 = new Font("Serif", Font.PLAIN, 36);
	//Images
	private ImageIcon gameOverImg = new ImageIcon ("images/gameOver.jpg");
	
	
	
	
	public Game(){//constructor
		timer = new Timer(20, this);//timer
		
		//Initialize map and player
		Map.changeMap(1);//Default
		m = new Map();
		p = new Player();
		Player.setBoundaries(m.getWalls());
		
		//Adding all ghosts
		ghosts.add(new Ghost("blue", "easy"));
		ghosts.add(new Ghost("orange", "easy"));
		ghosts.add(new Ghost("red", "easy"));
		ghosts.add(new Ghost("pink", "hard"));
		
		//Initializing if player has won
		hasWon = false;
		
		//Adding buttons 
		
		//pause button
		pauseBtn = new JButton("||");
		pauseBtn.addActionListener(this);
		pauseBtn.setFocusable(false);
		pauseBtn.setBounds(WIDTH_GAME - 200, HEIGHT_GAME, 50, 20);
		this.add(pauseBtn);//Always have pause button
		
		//Return to menu button (only added when game is over)
		returnToMenu=new JButton("Return to menu");
	    returnToMenu.addActionListener(this);
		returnToMenu.setBounds(350, 380, 150, 40);
		
		//New game button (only added when game is over)
		newGame=new JButton("Reset game");
	    newGame.addActionListener(this);
		newGame.setBounds(350, 430, 150, 40);
		
		timer.start();//Start timer
	}
	
   
   	public void keyPressed(int x){//Input key movement
		p.setDirection(x);
		//System.out.println(x); Debug
	}

	public void actionPerformed(ActionEvent e){//Main program
	if(mapChanged){//If map changed reset it
		m.setMap();
		mapChanged = false;
		Player.setBoundaries(m.getWalls());//Give player information of its new boundaries
	}
	
	if(!gameOver){//Play if game is not over
		if(e.getSource() == pauseBtn){
			gameStarted = !gameStarted;
			/*Debug
			Game.setGameStatus(false);
			Game.setGameOver(true);
			hasWon = true;
			*/	
		}
		
		p.checkPlayerStatus(ghosts);//Checks if player has hit ghosts
		
		if(gameStarted){//If game is currently resumed
			if(p.canMove()){//Move Players
				p.move();
				
			}
		
			for (Ghost gho : ghosts ) {//Move ghosts
				gho.setPlayerLocation(p.getRectangle());//Will give location for hard ghosts to track
				gho.move();
				
			}
		

			if(m.cheeseEatenn(p.getRectangle().getBounds())){//When Player eats cheese
				p.addScore();
			}
		
			if(p.getScore() >= m.getMaxScore()){//If player hits maxScore it wins
				Game.setGameStatus(false);
				Game.setGameOver(true);
				hasWon = true;
				
			}
		}
	}
	
		if(e.getSource()==returnToMenu){//Return to menu
			Game.setGameStatus(false);
			Main.cardsL.show(Main.c, "MenuNickName");
			
		}
		
		if(e.getSource() == newGame){//Restart game
			reinitialize();
			gameOver = false;
			gameStarted = true;
			hasWon = false;
		}
		
		
		repaint();
	
	}
   
	public void paintComponent(Graphics g){//main painting
		super.paintComponent(g);
		this.setBackground(Color.black);
		
		//Draw all components
		m.drawMap(g);
		p.draw(g);
		p.drawStatus(g);
		for (Ghost gho : ghosts ) {//Draw all Ghosts
			gho.draw(g);
		}
		
		if(gameOver){//When game over draw end screen
			drawEndScreen(g);
		}
		
	}
	
	//setters
	public static void setGameStatus(boolean game){
		gameStarted = game;
	}
	
	public static void setGameOver(boolean game){
		gameOver = game;
	}
	
	public void drawEndScreen (Graphics g){//End screen
		g.setColor(myBlue);
		g.fillRect(100, 100, 400, 400);
		g.setColor(Color.BLACK);
		//g.setFont(font2);
		g.drawImage(gameOverImg.getImage(),130,130,340,100,null);
		g.setFont(font1);
		if(p.getScore() >= highScore){//Checks if highscore is achieved (tied or greater than)
			g.setColor(Color.BLACK);
			g.drawString("New High Score!", 130, 300);
			highScore = p.getScore();
		}
		if(hasWon){
			g.drawString("You Won!", 130, 250);
		}
		g.drawString("Score: " + p.getScore(), 130, 400);
		g.drawString("High Score: " + highScore, 130, 450);
		
		this.add(returnToMenu);
		this.add(newGame);
	}
	
	public void reinitialize(){//new Game
		
		//Initialize map and player
		m.setMap();
		p.reset();
		Player.setBoundaries(m.getWalls());
		
		for (Ghost gho : ghosts ) {//Reset ghosts
			gho.reset();
		}
	
		//Remove endscreen buttons
		this.remove(returnToMenu);
		this.remove(newGame);
		
	}
}