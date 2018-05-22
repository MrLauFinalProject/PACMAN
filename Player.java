import java.util.*;
class Player extends Mover
{
  
	Sounds audio;
  char direction;//demo mode use only
  char currDirection;//normal play
  char desiredDirection;//normal play

  // when foods eaten is = total pelets end the game
  int foodsEaten;

  long lastTime;
  
  int lastX;
  int lastY;
 //current location
  int x;
  int y;
 
  //used to tell which food pacman is on top of
  int foodX;
  int foodY;

  //for teleport tunnels
  boolean teleport;
  
  //pacman is dead or facing wall
  boolean stopped = false;

  //initial pos and orientation
  public Player(int x, int y)
  {

    teleport=false;
    foodsEaten=0;
    foodX = x/gridSize-1;
    foodY = y/gridSize-1;
    this.lastX=x;
    this.lastY=y;
    this.x = x;
    this.y = y;
    currDirection='L';
    desiredDirection='L';
    lastTime = System.currentTimeMillis();
  }


  //demo use only class
  public char newDirection()
  { 
     int random;
     char backwards='U';
     int newX=x,newY=y;
     int lookX=x,lookY=y;
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
     while (newDirection == backwards || !isValidDest(lookX,lookY))
     {
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
         newX-=FPS; 
         lookX-= FPS;
       }
       else if (random == 2)
       {
         newDirection = 'R';
         newX+=FPS; 
         lookX+= gridSize;
       }
       else if (random == 3)
       {
         newDirection = 'U';
         newY-=FPS; 
         lookY-=FPS;
       }
       else if (random == 4)
       {
         newDirection = 'D';
         newY+=FPS; 
         lookY+=gridSize;
       }
       if (newDirection != backwards)
       {
         set.add(new Character(newDirection));
       }
     } 
     return newDirection;
  }
  public int getPlayerX(){
	  return x;
  }
  public int getPlayerY(){
	  return y;
  }
  public boolean isChoiceDest()
  {
    if (  x%gridSize==0&& y%gridSize==0 )
    {
      return true;
    }
    return false;
  }

  public void demoMove()
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
         {
           x -= FPS;
         }
         else if (y == 9*gridSize && x < 2 * gridSize)
         {
           x = max - gridSize*1;
           teleport = true; 
         }
         break;     
      case 'R':
         if ( isValidDest(x+gridSize,y))
         {
           x+= FPS;
         }
         else if (y == 9*gridSize && x > max - gridSize*2)
         {
           x = 1*gridSize;
           teleport=true;
         }
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
    currDirection = direction;
    frameCount ++;
  }

  //moves pacman one frame
  public void move()
  {
    int gridSize=20;
    lastX=x;
    lastY=y;
     
 
    if (x %20==0 && y%20==0 ||
       (desiredDirection=='L' && currDirection=='R')  ||
       (desiredDirection=='R' && currDirection=='L')  ||
       (desiredDirection=='U' && currDirection=='D')  ||
       (desiredDirection=='D' && currDirection=='U')
       )
    {
      switch(desiredDirection)
      {
        case 'L':
           if (this.isValidDest(x-FPS,y))
             x -= FPS;
           break;     
        case 'R':
           if ( this.isValidDest(x+gridSize,y))
             x+= FPS;
           break;     
        case 'U':
           if ( this.isValidDest(x,y-FPS))
             y-= FPS;
           break;     
        case 'D':
           if ( this.isValidDest(x,y+gridSize))
             y+= FPS;
           break;     
      }
    }
    if (lastX==x && lastY==y)
    {
      switch(currDirection)
      {
        case 'L':
           if ( this.isValidDest(x-FPS,y))
             x -= FPS;
           else if (y == 9*gridSize && x < 2 * gridSize)
           {
             x = max - gridSize*1;
             teleport = true; 
           }
           break;     
        case 'R':
           if ( this.isValidDest(x+gridSize,y))
             x+= FPS;
           else if (y == 9*gridSize && x > max - gridSize*2)
           {
             x = 1*gridSize;
             teleport=true;
           }
           break;     
        case 'U':
           if ( this.isValidDest(x,y-FPS))
             y-= FPS;
           break;     
        case 'D':
           if ( this.isValidDest(x,y+gridSize))
             y+= FPS;
           break;     
      }
    }

    else
    {
      currDirection=desiredDirection;
    }
   
    if (lastX == x && lastY==y)
      stopped=true;
  
    else
    {
      stopped=false;
      frameCount ++;
    }
  }

  public void updatefood()//pacman ate the food
  {
    if (x%gridSize ==0 && y%gridSize == 0)
    {
    foodX = x/gridSize-1;
    foodY = y/gridSize-1;
    }
  } 
  public boolean isValidDest(int x, int y)
  { 
	// is the new location within play area and not a wall
    if ((((x)%20==0) || ((y)%20)==0) && 20<=x && x<400 && 20<= y && y<400 && (map[x/20-1][y/20-1]) != 0 ){
      if(System.currentTimeMillis() - lastTime > 500) {
    	audio.playOnce("nomnom.WAV");
    	lastTime = System.currentTimeMillis();
      }
    	return true;
    }
    else {
    	return false;
    }
    
  }
}
