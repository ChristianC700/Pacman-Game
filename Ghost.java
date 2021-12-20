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
public class Ghost extends Player{
	//Declaration
	private String colour;
	private final int LEFT = 37, UP = 38, RIGHT = 39, DOWN = 40;
	private int count = 0;
	private boolean willTrack;
	private int playerX = 0, playerY = 0;
	private boolean trackX = true;//Counter for tracker
	private ImageIcon img;
	
	public Ghost(String colour, String s){//Constructor
		this.colour = colour;
		img = new ImageIcon("images/ghosts/" + colour + ".png");
		setRectangle(new Rectangle(500,500, Map.getSize() - 5, Map.getSize() - 5));
		if(s.equals("hard")){//Hard ghost will attempt to track player
			willTrack = true;
		}else
			willTrack = false;
	}
	
	public void draw(Graphics g){//Main painting
		g.drawImage(img.getImage(), getRectangle().x -4,getRectangle().y, getRectangle().width, getRectangle().height, null);
		//g.setColor(red); For debug purposes
		//g.fillRect(getRectangle().x -4,getRectangle().y, getRectangle().width, getRectangle().height);
	}
	
	public void move(){//Ghost movement
		chooseDirection();
		
		if(canMove()){
			super.move();
		}
		else
			move();//Move till finds a direction it can move
	}
	
	public void chooseDirection(){
		if(count == 10){//Change direction every 10 ticks
			if(willTrack && canMove()){//Will try to trackPlayer if it can move in that direction
				setDirection(trackPlayer());
			}
			else{
				setDirection((int)(Math.random() * 4 + 37));
			}
		count = 0;
		}
		count++;
	}
	
	public int trackPlayer(){//Tracker for hard ghost
		trackX = !trackX;
		if(trackX){//Taking turns to move in horizontal and vertical direcrion
			if(getRectangle().x >= playerX){//If ghost is right of player
				return LEFT;
			}
			else
				return RIGHT;
		}
		else{
			if(getRectangle().y >= playerY){//If ghost is below player
				return UP;
			}
			else
				return DOWN;
		}
		
			
		
	}
	
	public void setPlayerLocation(Rectangle player){//Giving ghost player location
		playerX = player.x;
		playerY = player.y;
	}
	
	public void reset(){//Resets ghosts
		setRectangle(new Rectangle(500,500, Map.getSize() - 5, Map.getSize() - 5));
	}
}