
public abstract class MoveableEntity {
private int speed = 0;
final int fps = 60;
private String currentDirection = "North";

	public MoveableEntity(int speed) {
		this.speed = speed;
	}
	public void turn(String direction) {
		currentDirection = direction;
	}
	public Boolean checkWall(int[][] currentWallMap, int row, int column, String direction) {
		if(direction.toLowerCase() == "north") 
			if(currentWallMap[row - 1][column] == 1)
				return true;
		if(direction.toLowerCase() == "south") 
			if(currentWallMap[row + 1][column] == 1)
				return true;
		if(direction.toLowerCase() == "east") 
			if(currentWallMap[row][column + 1] == 1)
				return true;
		if(direction.toLowerCase() == "west") 
			if(currentWallMap[row][column - 1] == 1)
				return true;
		return false;
	}
	public void moveFoward(int[][] currentWallMap, int row, int column, String direction) {
		if(direction.toLowerCase() == "north") {
			currentWallMap[row][column] = 0;
			currentWallMap[row - 1][column] = 2;
		}
			
		if(direction.toLowerCase() == "south") {
			currentWallMap[row][column] = 0;
			currentWallMap[row + 1][column] = 2;
		}
			
		if(direction.toLowerCase() == "east") {
			currentWallMap[row][column] = 0;
			currentWallMap[row][column + 1] = 2;
		}
			
		if(direction.toLowerCase() == "west") {
			currentWallMap[row][column] = 0;
			currentWallMap[row][column - 1] = 2;
		}
			
	}
}
