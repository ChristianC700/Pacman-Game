import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.event.*;
class Menu extends JPanel implements ActionListener{//Menu
	//Declaration
	private JButton  returnToGame, changeMap;// buttons
	private ImageIcon bgMenu, playBtn, changeMapImg, title; //Images
	public int WIDTH = 800, HEIGHT = 800;//Frame dimention
		
	public Menu(){
		//Importing images
		bgMenu = new ImageIcon("images/MenuImg.png");
		playBtn = new ImageIcon("images/playBtn.png");
		changeMapImg = new ImageIcon("images/changeMap.png");
		title = new ImageIcon("images/title.png");
			
		//Return to game button
        returnToGame=new JButton(playBtn);
	    returnToGame.addActionListener(this);
		returnToGame.setBounds(WIDTH / 2 - 120, HEIGHT / 2 + 30, 200, 100);
		
		//Change Map button
		changeMap =new JButton(changeMapImg);
	    changeMap.addActionListener(this);
		changeMap.setBounds(WIDTH / 2 - 120, HEIGHT / 2 + 200, 200, 100);
			
		//Initalizing frame
		this.setFocusable(true);
		this.requestFocusInWindow();
	    this.setLayout(null); //Allows for buttons to be positioned
	    this.add(returnToGame);
		this.add(changeMap);
	}	  
	
	public void actionPerformed(ActionEvent e) {
		//Return to game once button is pressed
		if(e.getSource()==returnToGame){
			Game.setGameStatus(true);
			Main.cardsL.show(Main.c, "GameNickName");
		}
		//Go to choose menu options
		if(e.getSource() == changeMap){
			Main.cardsL.show(Main.c, "ChooseMap");
		}
	}
		
	//Paint
	public void paintComponent(Graphics g){
	   super.paintComponent(g);
	   g.drawImage(bgMenu.getImage(),0,0,WIDTH,HEIGHT,null);
	   g.drawImage(title.getImage(),WIDTH/4,100,400,100,null);
	}//end of paint 

}

class ChooseMap extends JPanel implements ActionListener{//Choose Map class
	//Declaration
	private JButton map1, map2, map3;// buttons
	private ImageIcon map1Img, map2Img, map3Img; //Images
	public int WIDTH = 800, HEIGHT = 800;//Frame dimention
	private ImageIcon chooseMapImg; //Title image
		
	public ChooseMap(){
		//Importing images
		map1Img = new ImageIcon("images/map1Img.png");
		map2Img = new ImageIcon("images/map2Img.png");
		map3Img = new ImageIcon("images/map3Img.png");
		chooseMapImg = new ImageIcon("images/chooseMap.png");
		
			
		//Map buttons (displays map image in button)
        map1=new JButton(map1Img);
	    map1.addActionListener(this);
		map1.setBounds(WIDTH / 4 - 150, HEIGHT / 2 + 30, 200, 200);
		
		map2=new JButton(map2Img);
	    map2.addActionListener(this);
		map2.setBounds(WIDTH / 2 - 100, HEIGHT / 2 + 30, 200, 200);
		
		map3=new JButton(map3Img);
	    map3.addActionListener(this);
		map3.setBounds(WIDTH / 4 * 3 - 50, HEIGHT / 2 + 30, 200, 200);
		
			
		this.setFocusable(true);
		this.requestFocusInWindow();
	    this.setLayout(null);
	    this.add(map1);
		this.add(map2);
		this.add(map3);
	}	  
	
	public void actionPerformed(ActionEvent e) {
		//Sets up map once the player has chose one
		if(e.getSource()==map1){
			Main.cardsL.show(Main.c, "MenuNickName");
			Map.changeMap(1);
			Game.mapChanged = true;
		}
		if(e.getSource()==map2){
			Main.cardsL.show(Main.c, "MenuNickName");
			Map.changeMap(2);
			Game.mapChanged = true;	
		}
		if(e.getSource()==map3){
			Main.cardsL.show(Main.c, "MenuNickName");
			Map.changeMap(3);
			Game.mapChanged = true;
		}
	}
		
	//Paint
	public void paintComponent(Graphics g){
	   super.paintComponent(g);
	   this.setBackground(Color.black);
	   g.drawImage(chooseMapImg.getImage(), 200, 200, WIDTH - 400, 100, null);
	}//end of paint 

}