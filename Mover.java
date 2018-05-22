

class Mover
{
  //counts the frames in the animation
  int frameCount=0;

  //game map
  int[][] map;

  /* gridSize is the size of one square in the game.
     max is the height/width of the game.
     FPS is the speed at which the object moves,
     1 FPS per move() call */
  int gridSize;
  int max;
  int FPS;

  /* Generic constructor */
  public Mover()
  {
    gridSize=20;
    FPS = 4;
    max = 400;
    map = new int[20][20];
    for(int i =0;i<20;i++){
      for(int j=0;j<20;j++){
        map[i][j] = 0;
      }
    }
  }

  /* Updates the map information */
  public void updatemap(int[][] map)
  {
    for(int i =0;i<20;i++){
      for(int j=0;j<20;j++){
        this.map[i][j] = map[i][j];
      }
    }
  }

  /* Determines if a set of coordinates is a valid destination.*/
  public boolean isValidDest(int x, int y)
  {
    // is the new location within play area and not a wall
    if ((((x)%20==0) || ((y)%20)==0) && 20<=x && x<400 && 20<= y && y<400 && (map[x/20-1][y/20-1]) != 0 ){
      return true;
    }
    return false;
  } 
}
