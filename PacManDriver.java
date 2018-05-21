import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.JApplet;
import java.awt.*;
import java.util.*;
import java.lang.*;

// This class has the gui creation and the keyboard and mouse listeners 
public class Pacman extends JApplet implements MouseListener, KeyListener
{ 

  // this is for timing the gameover and win screens
  long mainScreenTimer = -1;
  long timer = -1;


  Board board = new Board(); 

  //framerate
  javax.swing.Timer frameTimer;
 
   
  public Pacman()
  {
	  board.requestFocus();

    JFrame frame = new JFrame(); 
    frame.setSize(420,460);

    frame.add(board,BorderLayout.CENTER);

    board.addMouseListener(this);  
    board.addKeyListener(this);  

    frame.setVisible(true);
    frame.setResizable(false);

    board.New=1;

    // this starts the first frame of the game which starts the game
    stepFrame(true);

    frameTimer = new javax.swing.Timer(30,new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          stepFrame(false);
        }});

    frameTimer.start();

    board.requestFocus();
  }

  // the idea for this function was found online and it basically updates the area around the screen where something might have changed instead of the whole screen
  public void repaint()
  {
    if (board.player.teleport){
    	board.repaint(board.player.lastX-20,board.player.lastY-20,80,80);
    	board.player.teleport=false;
    }
    board.repaint(0,0,600,20);
    board.repaint(0,420,600,40);
    board.repaint(board.player.x-20,board.player.y-20,80,80);
    board.repaint(board.ghost1.x-20,board.ghost1.y-20,80,80);
    board.repaint(board.ghost2.x-20,board.ghost2.y-20,80,80);
    board.repaint(board.ghost3.x-20,board.ghost3.y-20,80,80);
    board.repaint(board.ghost4.x-20,board.ghost4.y-20,80,80);
  }

  public void stepFrame(boolean New){
	  
	
    if (!board.titleScreen && !board.winScreen && !board.overScreen){
      timer = -1;
      mainScreenTimer = -1;
    }

    if (board.dying>0){
      board.repaint();
      return;
    }

 
    New = New || (board.New !=0) ;


    if (board.titleScreen){
      if (mainScreenTimer == -1){
        mainScreenTimer = System.currentTimeMillis();
      }

      long currTime = System.currentTimeMillis();
      if (currTime - mainScreenTimer >= 5000) {//if the user dosnt start the game in 5 seconds it starts the demo screen
        board.titleScreen = false;
        board.demo = true;
        mainScreenTimer = -1;
      }
      board.repaint();
      return;
    }
 

    else if (board.winScreen || board.overScreen){
      if (timer == -1) {
        timer = System.currentTimeMillis();
      }

      long currTime = System.currentTimeMillis();
      if (currTime - timer >= 5000){
        board.winScreen = false;
        board.overScreen = false;
        board.titleScreen = true;
        timer = -1;
      }
      board.repaint();
      return;
    }


    if (!New){

      if (board.demo){
        board.player.demoMove();
      }
      else{
        board.player.move();
      }

      /* Also move the ghosts, and update the food states */
      board.ghost1.chasePacMan(); 
      board.ghost2.chasePacMan(); 
      board.ghost3.chasePacMan(); 
      board.ghost4.chasePacMan(); 
      board.player.updatePellet();
      board.ghost1.updatePellet();
      board.ghost2.updatePellet();
      board.ghost3.updatePellet();
      board.ghost4.updatePellet();
    }

    // We reset the board here 
    if (board.stopped || New){
      frameTimer.stop();

      while (board.dying >0){
        /* Play dying animation. */
        stepFrame(false);
      }

      // resets the players to starting positions
      board.player.currDirection='L';
      board.player.direction='L';
      board.player.desiredDirection='L';
      board.player.x = 200;
      board.player.y = 300;
      board.ghost1.x = 180;
      board.ghost1.y = 180;
      board.ghost2.x = 200;
      board.ghost2.y = 180;
      board.ghost3.x = 220;
      board.ghost3.y = 180;
      board.ghost4.x = 220;
      board.ghost4.y = 180;

      board.repaint(0,0,600,600);

      board.stopped=false;
      frameTimer.start();
    }
    else{
      repaint(); 
    }
  }  

  // key listener class
  public void keyPressed(KeyEvent e){
    if (board.titleScreen) {//any key is pressed
      board.titleScreen = false;//end title screen and go into game
      return;
    }
    
    else if (board.winScreen || board.overScreen)//if your in win or game over
    {
      board.titleScreen = true;//go to title screen
      board.winScreen = false;
      board.overScreen = false;
      return;
    }
    else if (board.demo){
      board.demo=false;
      
      board.New=1;
      return;
    }

    switch(e.getKeyCode()){//change direction based on key clicks
      case KeyEvent.VK_LEFT:
       board.player.desiredDirection='L';
       break;     
      case KeyEvent.VK_RIGHT:
       board.player.desiredDirection='R';
       break;     
      case KeyEvent.VK_UP:
       board.player.desiredDirection='U';
       break;     
      case KeyEvent.VK_DOWN:
       board.player.desiredDirection='D';
       break;     
    }

    repaint();
  }

  public void mousePressed(MouseEvent e){
    if (board.titleScreen || board.winScreen || board.overScreen){
      return;
    }

    int x = e.getX();
    int y = e.getY();
    if ( 400 <= y && y <= 460){
      if ( 100 <= x && x <= 150){
        board.New = 1;
      }
      else if (180 <= x && x <= 300){
        board.clearHighScores();
      }
      else if (350 <= x && x <= 420){
        /* Exit has been clicked */
        System.exit(0);
      }
    }
  }
  
 
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseClicked(MouseEvent e){}
  public void keyReleased(KeyEvent e){}
  public void keyTyped(KeyEvent e){}
  
  
  public static void main(String [] args)
  {
      Pacman temp = new Pacman();
  }
}
