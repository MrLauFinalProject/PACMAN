
/*
 * we worte this entire class ourselves
 */
import java.util.HashSet;
import java.util.Set;
import java.awt.*;
import javax.imageio.*;
import javax.swing.JPanel;
import java.lang.Math;
import java.util.*;
import java.io.*;


class Ghost extends Mover
{ 


	Player pac = new Player(200, 300);
	
  char direction;

  int lastX;
  int lastY;

  int x;
  int y;

  int foodX,foodY;
  int p1X, p1Y;//upper left powerup
  int p2X, p2Y;//upper right powerup
  int p3X, p3Y;//bottom left powerup
  int p4X, p4Y; //bottom right powerup	
 

  int lastfoodX,lastfoodY;

  public Ghost(int x, int y)
  {
    direction='L';
    foodX=x/gridSize-1;
    foodY=x/gridSize-1;
    lastfoodX=foodX;
    lastfoodY=foodY;
    this.lastX = x;
    this.lastY = y;
    this.x = x;
    this.y = y;
    p1X = 0;
    p1Y = 0;
    
    p2X = gridSize-1;
    p2Y = 0;
    
    p3X = gridSize-1;
    p3Y = gridSize-1;
    
    p4X = 0;
    p4Y = gridSize-1;
  }

  public void updatefood()//makes it so that the ghosts can not eat foods. if removed the ghosts also eat the foods
  {
	  System.out.println(foodX + " " + foodY);
    int tempX,tempY;
    tempX = x/gridSize-1;
    tempY = y/gridSize-1;
    if (tempX != foodX || tempY != foodY)
    {
      lastfoodX = foodX;
      lastfoodY = foodY;
      foodX=tempX;
      foodY = tempY;
    }
     
  }
 
  // Determines if the location is one where the ghost has to make a decision like at a intersection
  public boolean isChoiceDest()
  {
    if (  x%gridSize==0&& y%gridSize==0 )
    {
      return true;
    }
    return false;
  }

  // Chooses a new direction
  public char newDirection()
  { 
    int random;
    char backwards = 'U';
    int newX = x,newY = y;
    int lookX = x,lookY = y;
    Set<Character> set = new HashSet<Character>();
    
    switch(direction)
    {
      case 'L':
         backwards='R';
         break;     
      case 'R':
         backwards='L';
         break;     
      case 'U':
         backwards='D';
         break;     
      case 'D':
         backwards='U';
         break;     
    }

    char newDirection = backwards;
    // While we still haven't found a direction
    while (newDirection == backwards || !isValidDest(lookX,lookY))
    {
    	//if now valid direction found then we go back do where we came from
    	if (set.size()==3)
      {
        newDirection=backwards;
        break;
      }

      newX=x;
      newY=y;
      lookX=x;
      lookY=y;
      
      random = (int)(Math.random()*4) + 1;
      if (random == 1)
      {
        newDirection = 'L';
        newX -= FPS; 
        lookX -= FPS;
      }
      else if (random == 2)
      {
        newDirection = 'R';
        newX += FPS; 
        lookX += gridSize;
      }
      else if (random == 3)
      {
        newDirection = 'U';
        newY -= FPS; 
        lookY -= FPS;
      }
      else if (random == 4)
      {
        newDirection = 'D';
        newY += FPS; 
        lookY += gridSize;
      }
      if (newDirection != backwards)
      {
        set.add(new Character(newDirection));
      }
    } 
    return newDirection;
  }

  public void move()
  {
    lastX=x;
    lastY=y;
 
    if (isChoiceDest())
    {
      direction = newDirection();
    }
    
    
    switch(direction)
    {
      case 'L':
         if ( isValidDest(x-FPS,y))
           x -= FPS;
         break;     
      case 'R':
         if ( isValidDest(x+gridSize,y))
           x+= FPS;
         break;     
      case 'U':
         if ( isValidDest(x,y-FPS))
           y-= FPS;
         break;     
      case 'D':
         if ( isValidDest(x,y+gridSize))
           y+= FPS;
         break;     
    }
  }
}


