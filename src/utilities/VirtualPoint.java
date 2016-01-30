package utilities;

/**
 * 
 * This Class serves as a representation of single rectangles on the screen. On
 * the Screen, <br>
 * those rectangles have the dimensions width and height,
 * but this <br>
 * VirtualPoint shrinks this big rectangle into one small Point. <br>
 * <br>
 * There are 4 ways of constructing a VirtualPoint :<br>
 * (1) from a real Point (which is then rather the Bottom Left <br>
 * Corner of one of those rectangles) <br>
 * (2) from one of those rectangles on the screen <br>
 * (3) from an already existing VirtualPoint <br>
 * (4) by manually setting x- and y-coordinate of the VirtualPoint <br>
 * 
 * @author Oliver Lorenz
 */
public class VirtualPoint implements IField{

	private double x;
	private double y;
	
	private double width;
	private double height;
	
	/**
	 * Constructs an empty VirtualPoint at (0,0) with no Dimension (width = 0, height = 0)
	 */
	public VirtualPoint() {
		x = 0;
		y = 0;
		width = 0;
		height = 0;
	}

	/**
	 * Builds a VirtualPoint from an x- and an y-coordinate. It works just
	 * like Point(int, int). <br>
	 * Width and Height of the VirtualPoint are by default 1.<br>
	 * 
	 * @param x
	 *            the x-coordinate of the VirtualPoint
	 * @param y
	 *            the y-coordinate of the VirtualPoint
	 */
	public VirtualPoint(double x, double y) {
		this.x = x;
		this.y = y;
		this.width = 1;
		this.height = 1;
	}
	
	/**
	 * This constructor works like VirtualPoint(int, int), but it allows to specify the object's width and height. <br>
	 * 
	 * @param x  the x-coordinate of the VirtualPoint
	 * @param y  the y-coordinate of the VirtualPoint
	 * @param width  the width of the VirtualPoint
	 * @param height  the height of the VirtualPoint
	 */
	public VirtualPoint(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Builds a VirtualPoint out of an existing VirtualPoint. (In other <br>
	 * Words, this VirtualPoint is a copy of the existing VirtualPoint) <br>
	 * 
	 * @param point the VirtualPoint, from which the new VirtualPoint is
	 *            constructed
	 */
	public VirtualPoint(VirtualPoint point) {
		this.x = point.x;
		this.y = point.y;
		this.width = point.width;
		this.height = point.height;
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public utilities.Point getPosition() {
		return new utilities.Point(x, y);
	}
	
	/**
	 * This method moves this Virtualpoint into the given direction. <br>
	 * If direction isn't a direction (means : direction isn't north, east,
	 * south or west), then nothing happens to this Virtualpoint and false is returned. <br>
	 * If the direction is null, nothing happens and false is returned. Otherwise, true is
	 * returned. <br>
	 * 
	 * @param direction
	 *            the direction (north, east, south, west) in which the point is
	 *            moved
	 * @return the result, if the movement succeeded (if direction isn't north,
	 *         east, south or west, the movement succeded by default, but the
	 *         Virtualpoint stays where it is).
	 */
	public boolean move(Direction direction) {
		if (direction != null) {
			switch (direction.getDirection()) {
				case 0:
					y -= 1;
					break;
				case 1:
					x += 1;
					break;
				case 2:
					y += 1;
					break;
				case 3:
					x -= 1;
					break;
				default :
					return false;
			}
			return true;
		} else
			return false;
	}

	/**
	 * This method creates a new Virtualpoint doubleo the given direction <br>
	 * 
	 * @param direction
	 *            the direction in which the new Virtualpoint is created
	 * @return the Virtualpoint in front of this Virtualpoint (doubleo the
	 *         given direction).
	 */
	public VirtualPoint createLP(Direction direction) {
		VirtualPoint pointInDirection = new VirtualPoint(x, y, width, height);
		pointInDirection.move(direction);
		return pointInDirection;
	}

	/**
	 * This method determines, if the Virtualpoint is part of the given path.
	 * <br>
	 * if path is null, it returns true. <br>
	 * 
	 * @param path
	 *            the given path
	 * @return whether this Virtualpoint is part of the path
	 */
	public boolean isPartOf(Path path) {
		if (path != null) {
			return path.contains(this);
		}
		return true;
	}

	/**
	 * This method determines, if the Virtualpoint is part of any of the given
	 * paths. <br>
	 * It returns true, if any of the paths contain this Virtualpoint. False
	 * otherwise. <br>
	 * if paths is null, it returns false. <br>
	 * If a ImagePath in paths is null, this ImagePath is ignored. <br>
	 * 
	 * @param paths
	 *            the given paths
	 * @return whether this Virtualpoint is part in any of the paths
	 */
	public boolean isPartOf(Path[] paths) {
		boolean temp = false;
		if (paths != null) {
			for (int i = 0; i < paths.length; i++) {
				if (paths[i] != null)
					temp |= paths[i].contains(this);
			}
			return temp;
		}
		return false;
	}

	/**
	 * This method returns the distance (in air-line) between this
	 * Virtualpoint and another Virtualpoint. <br>
	 * <br>
	 * e.g. :<br>
	 * Virtualpoint a = new Virtualpoint(1,2); <br>
	 * Virtualpoint b = new Virtualpoint(4,6); <br>
	 * <br>
	 * In this example, a.distanceTo(b) would be 5.
	 * 
	 * @param aim : The other Virtualpoint
	 * @return the distance as a double value
	 */
	public double distanceTo(VirtualPoint aim) {
		double x_difference = Math.abs(x - aim.getX());
		double y_difference = Math.abs(y - aim.getY());
		return (Math.sqrt(Math.pow(y_difference, 2) + Math.pow(x_difference, 2)));
	}
	
	@Override
	public String toString() {
		return super.toString() + " (" + x + ", " + y + ")";
	}
}