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
public class Player{
	//Declaration
	private final int LEFT = 37, UP = 38, RIGHT = 39, DOWN = 40;
	private int score;
	private int lives = 3;
	private int direction;
	private Rectangle player;
	public static ArrayList<Rectangle> boundaries;
	private int resetCount = 0;
	private boolean visible;
	private boolean vulnerable;
	private int vulCounter = 0;
	private String vulStatus = "";
	private Font font = new Font("Serif", Font.PLAIN, 24);
	private ImageIcon heart = new ImageIcon("images/heart.png");
	private int imgCount = 0;

	//Constructor
	public Player(){
		score = 0;
		player = new Rectangle(0,0, Map.getSize() - 5, Map.getSize() - 5);
		vulnerable = true;
		visible = true;
		vulStatus = "vulnerable";
	}
	
	public void eatCheese(){
		score += 20;//When cheese is eaten add to score
	}
	
	public void draw(Graphics g){//Main player painting
		if(visible){
			g.drawImage(myImages().getImage(), player.x, player.y, player.width, player.height, null);
			//g.setColor(Color.yellow);
			//g.fillRect(player.x, player.y, player.width, player.height);
		}
	}
	
	public void drawStatus(Graphics g){//Draws lives, score and vulnerability status
		if(visible){
			for(int i = 0; i < lives; i++){
				g.drawImage(heart.getImage(), i * Map.getSize() - 5, Game.HEIGHT_GAME, Map.getSize() - 5, Map.getSize() - 5, null);
				//g.setColor(Color.red); debug(normal rectangle)
				//g.fillRect(i * Map.getSize() - 5, Game.HEIGHT_GAME, Map.getSize() - 5, Map.getSize() - 5);
			}
		}
		
		//Draw score and vulnerability status
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("Score " + score, Game.WIDTH_GAME - 20, Game.WIDTH_GAME + 20);
		g.drawString(vulStatus, Game.WIDTH_GAME / 2 - 50, Game.WIDTH_GAME + 20);
		
	}
	
	public void move(){//Movement
		if(direction ==  LEFT){
			player.x -= 4;
		}
		if(direction == UP){
			player.y -= 4;
		}
		if(direction == RIGHT){
			player.x += 4;
		}
		if(direction == DOWN){
			player.y += 4;
		}
	}
	
	//getters and setters
	public void setDirection(int x){
		direction = x;
	}
	
	public Rectangle getRectangle(){
		return player;
	}
	
	public void setRectangle(Rectangle rec){
		player = rec;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void addScore(){
		score += 50;
	}
	
	public int getScore(){
		return score;
	}
	
	//Sets boundaries for player to not intersect
	public static void setBoundaries(ArrayList<Rectangle> walls){
		boundaries = walls;
	}
	
	public int getLives(){
		return lives;
	}

   
	public boolean canMove(){//Returns if player can move to next position using test rectangle
		Player test = new Player();
		test.setRectangle(this.getRectangle().getBounds());
		test.setDirection(this.getDirection());
		test.move();
		
		boolean intersects = false;
		for (Rectangle bounds : boundaries ) {
		if(test.getRectangle().intersects(bounds))
		intersects = true;
		}
		
		return !intersects;
	}
	
	public void checkPlayerStatus(ArrayList<Ghost> ghosts){//Checks if player hits ghost or lives are done
	
		boolean intersects = false;
		for(Ghost ghost : ghosts){
			if(player.intersects(ghost.getRectangle())){
				if(vulnerable){
				intersects = true;
				}
			}
		}
		
		if(intersects){//Game stoppage
			Game.setGameStatus(false);
			resetPlayer();
		}
		
		if(lives <= 0){
			Game.setGameStatus(false);
			Game.setGameOver(true);
		}
		
		if(!vulnerable){
			vulCounter++;
			if (vulCounter == 100){//Invulnerable for 100 ticks
				vulnerable = true;
				vulCounter = 0;
				vulStatus = "vulnerable";
			}
		}
	}
	
	public void resetPlayer(){//Resets player during game (adds invulnerable period)
		resetCount++;
		
		if(resetCount%10 == 5)//Animation
		visible = false;
		if(resetCount%10 == 0)
		visible = true;
		
		
		
		if(resetCount == 40){//resetting player location and removing life
			Game.setGameStatus(true);
			lives--;
			player.x = 0;
			player.y = 0;
			resetCount = 0;
			visible = true;
			vulnerable = false;
			vulCounter = 0;
			vulStatus = "invulnerable";
		}
	}
	
	public void reset(){//Resets player
		player.x = 0;
		player.y = 0;
		lives = 3;
		score = 0;
	}
	
	public ImageIcon myImages(){//Gets images
		if(Game.gameStarted)
		imgCount++;
	
		int x = 1;
		if(imgCount< 5)//Animation
		x = 1;
		else if(imgCount<= 10)
		x = 2;
		else
		imgCount = 0;
	
		if(direction ==  LEFT){
			return new ImageIcon("images/pman/left" + x + ".png");
		}
		if(direction == UP){
			return new ImageIcon("images/pman/up" + x + ".png");
		}
		if(direction == DOWN){
			return new ImageIcon("images/pman/down" + x + ".png");
		}
		else{
			return new ImageIcon("images/pman/right" + x + ".png");
		}
		
	}		


}