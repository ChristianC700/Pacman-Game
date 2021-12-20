/*
Christian Canlas
333586014
Ms. Strelkovska
ICS4U

June 22 2021
Final Project
Pacman
*/
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.*;


import javax.swing.*;


public class Main extends JFrame implements KeyListener{
	static CardLayout cardsL;
	static Container c;
	
	Menu  menuP;  // object of my customized class Menu
	Game gameP;   // object of my customized class Game 
	ChooseMap mapP; // object of my customized class Choose Map
	

	
	public Main(){    //constructor
	   c=getContentPane();
	   cardsL=new CardLayout(); //Put all frames on cardLayout
	   c.setLayout(cardsL);
	   
	   menuP = new Menu();
	   gameP = new Game();
	   mapP = new ChooseMap();
	  
  	   c.add("MenuNickName",menuP);
	   c.add("ChooseMap", mapP);
  	   c.add("GameNickName", gameP);
	   gameP.setLayout(null);
	   
	    this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
  	  
	}
	
	//Keylistener methods
    public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		//System.out.println(key); //Debug
		gameP.keyPressed(key);
	}
	public void keyTyped(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}

	public static void main(String[] args) {
		Main a = new Main();
		a.setSize(800, 800);
		a.setVisible(true);
		a.setLocationRelativeTo(null);
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // make frame closed when x button is pressed

	}
    
}