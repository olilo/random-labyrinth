package utilities;

/**
 * 
 * 
 * @author Oliver Lorenz
 */
public enum Direction {
	
	NORTH,
	EAST,
	SOUTH,
	WEST,
    NONE;


	public int getDirection() {
		return this.ordinal();
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
        if (this.equals(NONE)) {
            return NONE;
        }
		return Direction.values()[(this.ordinal() + 3) % 4];
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
        if (this.equals(NONE)) {
            return NONE;
        }
		return Direction.values()[(this.ordinal() + 1) % 4];
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
        if (this.equals(NONE)) {
            return NONE;
        }
		return Direction.values()[(this.ordinal() + 2) % 4];
	}

}