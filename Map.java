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
public class Map{
	//Declaration
	public static final int WALL_SIZE = 30;
	public static final int NUMBER_BLOCKS = 20;
	public static int mapChoice = 1; //Default
	private int map[][] = new int[NUMBER_BLOCKS][NUMBER_BLOCKS];
	private int mapLength = WALL_SIZE * NUMBER_BLOCKS;
	private ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
	private Rectangle[] cheese;
	private boolean[] cheeseEaten;
	private int maxScore = 0;
	private Scanner scFile;


	
	public Map(){//Constructor
		//Initialization
		scFile = null;
		cheese = new Rectangle[NUMBER_BLOCKS * NUMBER_BLOCKS];
		cheeseEaten = new boolean [NUMBER_BLOCKS * NUMBER_BLOCKS];
		
		//Converting file info into walls
		setMap();
	}
	
	//Drawing Map Method
	public void drawMap(Graphics g){
	int cnt = 0;
		for(int r=0; r<NUMBER_BLOCKS; r++){
			for(int c=0; c<NUMBER_BLOCKS; c++){
				//Converting map array to array list and drawing
				if(map[r][c] == 1){
				g.setColor(Color.blue);
				g.fillRect(c * WALL_SIZE, r * WALL_SIZE, WALL_SIZE, WALL_SIZE);
				}
				else{
					if(!cheeseEaten[cnt]){//If cheese eaten
						g.setColor(Color.white);
						g.fillRect(cheese[cnt].x, cheese[cnt].y, cheese[cnt].width, cheese[cnt].height);
					}
				}
				cnt++;
			}
		}

		//Borders
		//Has negative values because player starts at 0, 0
		g.setColor(Color.blue);
		g.fillRect(-1, -1, 1, mapLength);
		g.fillRect(-1, -1, mapLength, 1);
		g.fillRect(mapLength, 0, 1, mapLength);
		g.fillRect(0, mapLength, mapLength, 1);
		
	}
	
	//Returns if at that location cheese is eaten
	public boolean cheeseEatenn(Rectangle rec){
		for(int i = 0; i < 400; i++){
			//System.out.println(cheese[i]);
			if(rec.intersects(cheese[i]) && !cheeseEaten[i]){
				cheeseEaten[i] = true;
				return true;
			}
		}
		return false;
	}
	
	//Getters and setters
	public int getMaxScore(){
		return maxScore;
	}
	
	public ArrayList<Rectangle> getWalls(){
		return walls;
	}
	
	public static int getSize(){
		return WALL_SIZE;
	}
	
	public void setMap(){//Puts map into array and array list
		walls = new ArrayList<Rectangle>();
		scFile = null;//Reset scanner
		try{
			scFile = new Scanner(new File("Maps/Map" + mapChoice + ".txt"));//Scans the map chosen
		}catch (FileNotFoundException e){
			System.out.println("File reading error");
		}
		
		//Calculate max score and set wall and cheese locations and values
		maxScore = 0;
		int cnt = 0;
		for(int r=0; r<NUMBER_BLOCKS; r++){
			for(int c=0; c<NUMBER_BLOCKS; c++){
				map[r][c] = scFile.nextInt();
				cheeseEaten[cnt] = false;//Setting current state of cheese to be uneaten
				cheese[cnt] = new Rectangle(c * WALL_SIZE + 12, r * WALL_SIZE + 12, WALL_SIZE - 24, WALL_SIZE - 24);
				cnt++;
				
				if(map[r][c] == 1){
					//Add wall to array list
					walls.add(new Rectangle(c * WALL_SIZE, r * WALL_SIZE, WALL_SIZE, WALL_SIZE));
				}
				else{
					//if it is not a wall a cheese can be placed
					maxScore += 50;
				}
			}
		}
		
		//Add borders to wall list
		walls.add(new Rectangle(-1, -1, 1, mapLength));
		walls.add(new Rectangle(-1, -1, mapLength, 1));
		walls.add(new Rectangle(mapLength, 0, 1, mapLength));
		walls.add(new Rectangle(0, mapLength, mapLength, 1));
	}
	
	public static void changeMap(int x){//Will set the map chosen
		mapChoice = x;
	}
	
}