import java.awt.*;
import javax.imageio.*;
import javax.swing.JPanel;
import java.lang.Math;
import java.util.*;
import java.io.*;

/*This board class contains the player, ghosts, foods, and most of the game logic.*/
public class Board extends JPanel
{
 //get the images
	String filePath = "C:\\Users\\alexs\\Desktop\\PacManFiles\\";
  Image pacmanImage = Toolkit.getDefaultToolkit().getImage(filePath + "pacman.jpg"); //put image file location here
  Image pacmanUpImage = Toolkit.getDefaultToolkit().getImage(filePath + "pacmanup.jpg"); 
  Image pacmanDownImage = Toolkit.getDefaultToolkit().getImage(filePath + "pacmandown.jpg"); 
  Image pacmanLeftImage = Toolkit.getDefaultToolkit().getImage(filePath + "pacmanleft.jpg"); 
  Image pacmanRightImage = Toolkit.getDefaultToolkit().getImage(filePath + "pacmanright.jpg"); 
  Image ghost10 = Toolkit.getDefaultToolkit().getImage(filePath + "ghost10.jpg"); 
  Image ghost20 = Toolkit.getDefaultToolkit().getImage(filePath + "ghost20.jpg"); 
  Image ghost30 = Toolkit.getDefaultToolkit().getImage(filePath + "ghost30.jpg"); 
  Image ghost40 = Toolkit.getDefaultToolkit().getImage(filePath + "ghost40.jpg"); 
  Image ghost11 = Toolkit.getDefaultToolkit().getImage(filePath + "ghost11.jpg"); 
  Image ghost21 = Toolkit.getDefaultToolkit().getImage(filePath + "ghost21.jpg"); 
  Image ghost31 = Toolkit.getDefaultToolkit().getImage(filePath + "ghost31.jpg"); 
  Image ghost41 = Toolkit.getDefaultToolkit().getImage(filePath + "ghost41.jpg"); 
  Image titleScreenImage = Toolkit.getDefaultToolkit().getImage(filePath + "titleScreen2.jpg"); 
  Image gameOverImage = Toolkit.getDefaultToolkit().getImage(filePath + "gameOver.jpg"); 
  Image winScreenImage = Toolkit.getDefaultToolkit().getImage(filePath + "winScreen.jpg");

  // Initialize the player and ghosts 
  Player player = new Player(200,300);
  Ghost ghost1 = new Ghost(180,180);
  Ghost ghost2 = new Ghost(200,180);
  Ghost ghost3 = new Ghost(220,180);
  Ghost ghost4 = new Ghost(220,180);

  long timer = System.currentTimeMillis();

  Sounds audio;
 
  int dying=0;
 
  int currScore;
  int highScore;

  boolean clearHighScores= false;

  int numLives=2;

  int[][] map;

  boolean[][] foods;

  // Game dimensions 
  int gridSize;
  int max;
  
  //powerUps Status
  static Boolean UL = true;
  static Boolean UR = true;
  static Boolean BL = true;
  static Boolean BR = true;

  // game info
  boolean stopped;
  boolean titleScreen;
  boolean winScreen = false;
  boolean overScreen = false;
  boolean demo = false;
  int New;

  int lastfoodEatenX = 0;
  int lastfoodEatenY=0;

  Font font = new Font("Monospaced",Font.BOLD, 12);

  public Board() 
  {
    initHighScores();
    currScore=0;
    stopped=false;
    max=400;
    gridSize=20;
    New=0;
    titleScreen = true;
  }

  // Reads the high scores file and saves it 
  public void initHighScores()
  {
    File file = new File(filePath + "highScores.txt");
    Scanner sc;
    try
    {
        sc = new Scanner(file);
        highScore = sc.nextInt();
        sc.close();
    }
    catch(Exception e)
    {
    	System.out.println("Error 1 board");
    }
  }

  public void updateScore(int score)
  {
    PrintWriter out;
    try{
      out = new PrintWriter(filePath + "highScores.txt");
      out.println(score);
      out.close();
    }
    catch(Exception e){
    }
    highScore=score;
    clearHighScores=true;
  }

  public void clearHighScores()
  {
    PrintWriter out;
    try{
      out = new PrintWriter(filePath + "highScores.txt");
      out.println("0");
      out.close();
    }
    catch(Exception e){
    }
    highScore=0;
    clearHighScores=true;
  }

  //reset game for new game
  public void reset()
  {
    numLives=2;
    map = new int[20][20];
    foods = new boolean[20][20];

    // Clear map and food
    for(int i=0;i<20;i++){
      for(int j=0;j<20;j++){
        map[i][j]=1;
        foods[i][j]=true;
      }
    }

    // Handle the weird spots with no food
    for(int i = 5;i<14;i++){
      for(int j = 5;j<12;j++){
        foods[i][j]=false;
      }
    }
    foods[9][7] = false;
    foods[8][8] = false;
    foods[9][8] = false;
    foods[10][8] = false;

  }

  public void updateMap(int x,int y, int width, int height)
  {
    for (int i =x/gridSize; i<x/gridSize+width/gridSize;i++)
    {
      for (int j=y/gridSize;j<y/gridSize+height/gridSize;j++)
      {
        map[i-1][j-1]=0;
        foods[i-1][j-1]=false;
      }
    }
  } 

  public void drawLives(Graphics g)
  {
    g.setColor(Color.BLACK);

    g.fillRect(0,max+5,600,gridSize);
    g.setColor(Color.YELLOW);
    for(int i = 0;i<numLives;i++)
    {
      g.fillOval(gridSize*(i+1),max+5,gridSize,gridSize);
    }
    g.setColor(Color.YELLOW);
    g.setFont(font);
    g.drawString("Reset",100,max+5+gridSize);
    g.drawString("Clear High Scores",180,max+5+gridSize);
    g.drawString("Exit",350,max+5+gridSize);
  }
  
  public void drawBoard(Graphics g)//we couldnt find any easier way to draw the map other than manually so here is how the map is placed
  {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,600,600);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,420,420);
        
        g.setColor(Color.BLACK);
        g.fillRect(0,0,20,600);
        g.fillRect(0,0,600,20);
        g.setColor(Color.WHITE);
        g.drawRect(19,19,382,382);
        g.setColor(Color.BLUE);

        g.fillRect(40,40,60,20);
          updateMap(40,40,60,20);
        g.fillRect(120,40,60,20);
          updateMap(120,40,60,20);
        g.fillRect(200,20,20,40);
          updateMap(200,20,20,40);
        g.fillRect(240,40,60,20);
          updateMap(240,40,60,20);
        g.fillRect(320,40,60,20);
          updateMap(320,40,60,20);
        g.fillRect(40,80,60,20);
          updateMap(40,80,60,20);
        g.fillRect(160,80,100,20);
          updateMap(160,80,100,20);
        g.fillRect(200,80,20,60);
          updateMap(200,80,20,60);
        g.fillRect(320,80,60,20);
          updateMap(320,80,60,20);

        g.fillRect(20,120,80,60);
          updateMap(20,120,80,60);
        g.fillRect(320,120,80,60);
          updateMap(320,120,80,60);
        g.fillRect(20,200,80,60);
          updateMap(20,200,80,60);
        g.fillRect(320,200,80,60);
          updateMap(320,200,80,60);

        g.fillRect(160,160,40,20);
          updateMap(160,160,40,20);
        g.fillRect(220,160,40,20);
          updateMap(220,160,40,20);
        g.fillRect(160,180,20,20);
          updateMap(160,180,20,20);
        g.fillRect(160,200,100,20);
          updateMap(160,200,100,20);
        g.fillRect(240,180,20,20);
        updateMap(240,180,20,20);
        g.setColor(Color.BLUE);


        g.fillRect(120,120,60,20);
          updateMap(120,120,60,20);
        g.fillRect(120,80,20,100);
          updateMap(120,80,20,100);
        g.fillRect(280,80,20,100);
          updateMap(280,80,20,100);
        g.fillRect(240,120,60,20);
          updateMap(240,120,60,20);

        g.fillRect(280,200,20,60);
          updateMap(280,200,20,60);
        g.fillRect(120,200,20,60);
          updateMap(120,200,20,60);
        g.fillRect(160,240,100,20);
          updateMap(160,240,100,20);
        g.fillRect(200,260,20,40);
          updateMap(200,260,20,40);

        g.fillRect(120,280,60,20);
          updateMap(120,280,60,20);
        g.fillRect(240,280,60,20);
          updateMap(240,280,60,20);

        g.fillRect(40,280,60,20);
          updateMap(40,280,60,20);
        g.fillRect(80,280,20,60);
          updateMap(80,280,20,60);
        g.fillRect(320,280,60,20);
          updateMap(320,280,60,20);
        g.fillRect(320,280,20,60);
          updateMap(320,280,20,60);

        g.fillRect(20,320,40,20);
          updateMap(20,320,40,20);
        g.fillRect(360,320,40,20);
          updateMap(360,320,40,20);
        g.fillRect(160,320,100,20);
          updateMap(160,320,100,20);
        g.fillRect(200,320,20,60);
          updateMap(200,320,20,60);

        g.fillRect(40,360,140,20);
          updateMap(40,360,140,20);
        g.fillRect(240,360,140,20);
          updateMap(240,360,140,20);
        g.fillRect(280,320,20,40);
          updateMap(280,320,20,60);
        g.fillRect(120,320,20,60);
          updateMap(120,320,20,60);
        drawLives(g);
  } 

  // Draws the foods on the screen 
  public void drawfoods(Graphics g)
  {
        g.setColor(Color.YELLOW);
        for (int i=1;i<20;i++){
          for (int j=1;j<20;j++){
            if ( foods[i-1][j-1])
            g.fillOval(i*20+8,j*20+8,4,4);
          }
        }


  }
 
  // redraws food that the ghosts run over because they are also movers
  public void fillfood(int x, int y, Graphics g)
  {
    g.setColor(Color.YELLOW);
    g.fillOval(x*20+28,y*20+28,4,4);
  }
public void drawPowerups(Graphics g) {
	  
	  g.setColor(Color.BLUE);
	  if(UL) 
		  g.fillOval(23,23,14,14);
	  if(UR)
		  g.fillOval(378, 23, 14, 14);
	  if(BR)
		  g.fillOval(379, 379, 14, 14);
	  if(BL)
		  g.fillOval(23, 378, 14, 14);
  }

  // This is the main function that draws one entire frame of the game 
  public void paint(Graphics g)
  {
	  if(player.getPlayerX() == 23 && player.getPlayerY() == 23) 
		   UL = false;
	  if(player.getPlayerX() == 378 && player.getPlayerY() == 23)
		  UR = false;
	  if(player.getPlayerX() == 379 & player.getPlayerY() == 379)
		  BR = false;
	  if(player.getPlayerX() == 23 && player.getPlayerY() ==  378)
		  BL = false; 
	  
 
//draw the Powerups
 drawPowerups(g);

		
    if (dying > 0){
    	
      // Draw the pacman 
      g.drawImage(pacmanImage,player.x,player.y,Color.BLACK,null);
      g.setColor(Color.BLACK);
      
      
      // Kill the pacman 
      if (dying == 4)
        g.fillRect(player.x,player.y,20,7);
      else if ( dying == 3)
        g.fillRect(player.x,player.y,20,14);
      else if ( dying == 2)
        g.fillRect(player.x,player.y,20,20); 
      else if ( dying == 1)
        g.fillRect(player.x,player.y,20,20); 
      
     

      long currTime = System.currentTimeMillis();
      long temp;
      if (dying != 1)
        temp = 100;
      else
        temp = 2000;
      if (currTime - timer >= temp){
        dying--;
        timer = currTime;
        if (dying == 0){
          if (numLives==-1){
            if (demo)
              numLives=2;
            else{
              if (currScore > highScore){
                updateScore(currScore);
              }
              overScreen=true;
            }
          }
        }
      }
      return;
    }

    if (titleScreen){
      g.setColor(Color.BLACK);
      g.fillRect(0,0,600,600);
      g.drawImage(titleScreenImage,0,0,Color.BLACK,null);

      // Stop any pacman eating sounds 
      
      New = 1;
      return;
    } 

    else if (winScreen){
      g.setColor(Color.BLACK);
      g.fillRect(0,0,600,600);
      g.drawImage(winScreenImage,0,0,Color.BLACK,null);
      New = 1;
     // sounds.nomNomStop();
      return;
    }

    else if (overScreen){
      g.setColor(Color.BLACK);
      g.fillRect(0,0,600,600);
      g.drawImage(gameOverImage,0,0,Color.BLACK,null);
      New = 1;
      //sounds.nomNomStop();
      return;
    }

    if (clearHighScores){
      g.setColor(Color.BLACK);
      g.fillRect(0,0,600,18);
      g.setColor(Color.YELLOW);
      g.setFont(font);
      clearHighScores= false;
      if (demo)
        g.drawString("DEMO MODE PRESS ANY KEY TO START A GAME\t High Score: "+highScore,20,10);
      else
        g.drawString("Score: "+(currScore)+"\t High Score: "+highScore,20,10);
    }
   
    // lifeLost is set to true when pacman has lost a life 
    boolean lifeLost = false;
    
    if (New == 1){
      reset();
      player = new Player(200,300);
      ghost1 = new Ghost(180,180);
      ghost2 = new Ghost(200,180);
      ghost3 = new Ghost(220,180);
      ghost4 = new Ghost(220,180);
      currScore = 0;
      drawBoard(g);
      drawfoods(g);
      drawLives(g);
      player.updatemap(map);
      // player cant enter ghost box
      player.map[9][7]=0; 
      ghost1.updatemap(map);
      ghost2.updatemap(map);
      ghost3.updatemap(map);
      ghost4.updatemap(map);
   
      g.setColor(Color.YELLOW);
      g.setFont(font);
      if (demo)
        g.drawString("DEMO MODE PRESS ANY KEY TO START A GAME\t High Score: "+highScore,20,10);
      else
        g.drawString("Score: "+(currScore)+"\t High Score: "+highScore,20,10);
      New++;
    }
    else if (New == 2){
      New++;
    }
    else if (New == 3){
      New++;
      timer = System.currentTimeMillis();
      return;
    }
    else if (New == 4){
      long currTime = System.currentTimeMillis();
      if (currTime - timer >= 5000)
      {
        New=0;
      }
      else
        return;
    }
    
    g.copyArea(player.x-20,player.y-20,80,80,0,0);
    g.copyArea(ghost1.x-20,ghost1.y-20,80,80,0,0);
    g.copyArea(ghost2.x-20,ghost2.y-20,80,80,0,0);
    g.copyArea(ghost3.x-20,ghost3.y-20,80,80,0,0);
    g.copyArea(ghost4.x-20,ghost4.y-20,80,80,0,0);
    


    //collisions
    if (player.x == ghost1.x && Math.abs(player.y-ghost1.y) < 10){audio.playOnce("death.WAV");
    lifeLost=true;}
      
    else if (player.x == ghost2.x && Math.abs(player.y-ghost2.y) < 10){audio.playOnce("death.WAV");
    lifeLost=true;}
      
    else if (player.x == ghost3.x && Math.abs(player.y-ghost3.y) < 10){audio.playOnce("death.WAV");
    lifeLost=true;}
    else if (player.x == ghost4.x && Math.abs(player.y-ghost4.y) < 10){audio.playOnce("death.WAV");
    lifeLost=true;}
    else if (player.y == ghost1.y && Math.abs(player.x-ghost1.x) < 10){audio.playOnce("death.WAV");
    lifeLost=true;}
    else if (player.y == ghost2.y && Math.abs(player.x-ghost2.x) < 10){audio.playOnce("death.WAV");
    lifeLost=true;}
    else if (player.y == ghost3.y && Math.abs(player.x-ghost3.x) < 10){audio.playOnce("death.WAV");
    lifeLost=true;}
    else if (player.y == ghost4.y && Math.abs(player.x-ghost4.x) < 10){audio.playOnce("death.WAV");
    lifeLost=true;}

    
    if (lifeLost && !stopped)
    {
      dying = 4;

      numLives--;
      stopped=true;
      drawLives(g);
      timer = System.currentTimeMillis();
    }

    g.setColor(Color.BLACK);
    g.fillRect(player.lastX,player.lastY,20,20);
    g.fillRect(ghost1.lastX,ghost1.lastY,20,20);
    g.fillRect(ghost2.lastX,ghost2.lastY,20,20);
    g.fillRect(ghost3.lastX,ghost3.lastY,20,20);
    g.fillRect(ghost4.lastX,ghost4.lastY,20,20);

    if ( foods[player.foodX][player.foodY] && New!=2 && New !=3)
    {
      lastfoodEatenX = player.foodX;
      lastfoodEatenY = player.foodY;
      
      player.foodsEaten++;

      foods[player.foodX][player.foodY]=false;

      currScore += 50;

      g.setColor(Color.BLACK);
      g.fillRect(0,0,600,20);
      g.setColor(Color.YELLOW);
      g.setFont(font);
      if (demo)
        g.drawString("DEMO MODE PRESS ANY KEY TO START A GAME\t High Score: "+highScore,20,10);
      else
        g.drawString("Score: "+(currScore)+"\t High Score: "+highScore,20,10);

      if (player.foodsEaten == 173)
      {
        if (!demo)
        {
          if (currScore > highScore)
          {
            updateScore(currScore);
          }
          winScreen = true;
        }
        else
        {
          titleScreen = true;
        }
        return;
      }
    }



    if ( foods[ghost1.lastfoodX][ghost1.lastfoodY])
      fillfood(ghost1.lastfoodX,ghost1.lastfoodY,g);
    if ( foods[ghost2.lastfoodX][ghost2.lastfoodY])
      fillfood(ghost2.lastfoodX,ghost2.lastfoodY,g);
    if ( foods[ghost3.lastfoodX][ghost3.lastfoodY])
      fillfood(ghost3.lastfoodX,ghost3.lastfoodY,g);
    if ( foods[ghost4.lastfoodX][ghost4.lastfoodY])
      fillfood(ghost4.lastfoodX,ghost4.lastfoodY,g);


    if (ghost1.frameCount < 5)
    {
      g.drawImage(ghost10,ghost1.x,ghost1.y,Color.BLACK,null);
      g.drawImage(ghost20,ghost2.x,ghost2.y,Color.BLACK,null);
      g.drawImage(ghost30,ghost3.x,ghost3.y,Color.BLACK,null);
      g.drawImage(ghost40,ghost4.x,ghost4.y,Color.BLACK,null);
      ghost1.frameCount++;
    }
    else
    {
      g.drawImage(ghost11,ghost1.x,ghost1.y,Color.BLACK,null);
      g.drawImage(ghost21,ghost2.x,ghost2.y,Color.BLACK,null);
      g.drawImage(ghost31,ghost3.x,ghost3.y,Color.BLACK,null);
      g.drawImage(ghost41,ghost4.x,ghost4.y,Color.BLACK,null);
      if (ghost1.frameCount >=10)
        ghost1.frameCount=0;
      else
        ghost1.frameCount++;
    }

    if (player.frameCount < 5)
    {
      g.drawImage(pacmanImage,player.x,player.y,Color.BLACK,null);
    }
    else
    {
      if (player.frameCount >=10)
        player.frameCount=0;

      switch(player.currDirection)
      {
        case 'L':
           g.drawImage(pacmanLeftImage,player.x,player.y,Color.BLACK,null);
           break;     
        case 'R':
           g.drawImage(pacmanRightImage,player.x,player.y,Color.BLACK,null);
           break;     
        case 'U':
           g.drawImage(pacmanUpImage,player.x,player.y,Color.BLACK,null);
           break;     
        case 'D':
           g.drawImage(pacmanDownImage,player.x,player.y,Color.BLACK,null);
           break;     
      }
    }

    g.setColor(Color.WHITE);
    g.drawRect(19,19,382,382);

  }
}
