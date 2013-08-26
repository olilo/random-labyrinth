package utilities;

/**
 * 
 * 
 * @author Oliver Lorenz
 */
public class Direction {

	private int direction;
	
	public static final int NORTH = 0;
	public static final int north = 0;
	public static final int EAST = 1;
	public static final int east = 1;
	public static final int SOUTH = 2;
	public static final int south = 2;
	public static final int WEST = 3;
	public static final int west = 3;

	/**
	 * Creates a new direction from dir. <br>
	 * Valid values for dir are : 0,1,2,3. <br>
	 * You can also use Direction.north, Direction.east, Direction.south, Direction.west for dir.
	 * 
	 * @param dir
	 */
	public Direction(int dir) {
		if ((dir == 0) || (dir == 1) || (dir == 2) || (dir == 3))
			direction = dir;
		else
			direction = -1;
	}
	
	/**
	 * Creates a new direction from dir. <br>
	 * Valid values for dir are : "north", "east", "south", "west"
	 * 
	 * @param dir : 
	 */
	public Direction(String dir) {
		if (dir.equals("north"))
			direction = 0;
		else if (dir.equals("east"))
			direction = 1;
		else if (dir.equals("south"))
			direction = 2;
		else if (dir.equals("west"))
			direction = 3;
		else
			direction = -1;
	}	

	/**
	 * This creates a Direction out of an already existing direction.
	 * 
	 * @param dir : an already existing direction
	 */
	public Direction(Direction dir) {
		direction = dir.getDirection();
	}

	public int getDirection() {
		return direction;
	}

	public boolean isDirection() {
		if ((direction >= 0) && (direction <= 3))
			return true;
		else
			return false;
	}

	public boolean isEqual(Direction dir) {
		if (direction == dir.getDirection())
			return true;
		else
			return false;
	}

	/**
	 * returns the Direction in anti-clockwise rotation (-90�) (e.g. : north ->
	 * west). <br>
	 * When this Direction isn't a valid Direction, then this method returns
	 * null.
	 * 
	 * @return the direction after the rotation.
	 */
	public Direction left() {
		if (this.isDirection())
			return new Direction((direction + 3) % 4);
		else
			return null;
	}

	/**
	 * returns the Direction in clockwise rotation (90�) (e.g. : north -> east).
	 * <br>
	 * When this Direction isn't a valid Direction, then this method returns
	 * null.
	 * 
	 * @return the direction after the rotation.
	 */
	public Direction right() {
		if (this.isDirection())
			return new Direction((direction + 1) % 4);
		else
			return null;
	}

	/**
	 * returns the Direction in opposite direction (180�) (e.g. : north ->
	 * west). <br>
	 * When this Direction isn't a valid Direction, then this method returns
	 * null.
	 * 
	 * @return the direction after the rotation.
	 */
	public Direction revert() {
		if (this.isDirection())
			return new Direction((direction + 2) % 4);
		else
			return null;
	}

	/**
	 * This method returns the direction as a String
	 */
	public String toString() {
		switch (direction) {
		case 0:
			return "north";
		case 1:
			return "east";
		case 2:
			return "south";
		case 3:
			return "west";
		default:
			return "no Direction";
		}
	}
}