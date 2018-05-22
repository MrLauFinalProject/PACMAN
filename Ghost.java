import java.util.HashSet;
import java.util.Set;

class Ghost extends Mover//currently ramdom
{ 


	Player pac = new Player(200, 300);
	
  char direction;

  int lastX;
  int lastY;

  int x;
  int y;

  int foodX,foodY;

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
  }

  public void updatefood()//makes it so that the ghosts can not eat foods. if removed the ghosts also eat the foods
  {
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
 
  /* Determines if the location is one where the ghost has to make a decision*/ 
  public boolean isChoiceDest()
  {
    if (  x%gridSize==0&& y%gridSize==0 )
    {
      return true;
    }
    return false;
  }

  /* Chooses a new direction randomly for the ghost to move */
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
    /* While we still haven't found a valid direction */
    while (newDirection == backwards || !isValidDest(lookX,lookY))
    {
      /* If we've tried every location, turn around and break the loop */
      if (set.size()==3)
      {
        newDirection=backwards;
        break;
      }

      newX=x;
      newY=y;
      lookX=x;
      lookY=y;
      
      /* Randomly choose a direction */
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

  public char detectPacMan(){
	
	  if(pac.getPlayerX() - this.x >= 0 && pac.getPlayerX() - this.x <= 3){
		  return 'R';
	  }
	  else if(pac.getPlayerX() - this.x >= -3 && pac.getPlayerX() - this.x <= 0){
		  return 'L';
	  }
	  else if(pac.getPlayerY() - this.y >= 0 && pac.getPlayerY() - this.y <= 3){
		  return 'D';
	  }
	  else if(pac.getPlayerY() - this.y >= -3 && pac.getPlayerY() - this.y <= 0){
		  return 'U';
	  }
	  return 'N';
  }
  /* Random move function for ghost */
  public void move()
  {
    lastX=x;
    lastY=y;
 
    /* If we can make a decision, pick a new direction randomly */
    if(isChoiceDest() && detectPacMan() != 'N') {
    	
    	direction = detectPacMan();
    }
    if (isChoiceDest())
    {
      direction = newDirection();
    }
    
    
    /* If that direction is valid, move that way */
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
