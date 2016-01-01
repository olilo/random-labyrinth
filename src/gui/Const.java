package gui;

import java.awt.Color;

import utilities.Direction;
import utilities.VirtualPoint;

/**
 * This Class saves some constants that are needed throughout the game. The
 * constants can be changed by simply referencing them. <br>
 * <br>
 * Most constants refer to x- and y-coordinates. These constants are saved in 2
 * different ways : in Labyrinth- and in real coordinates. <br>
 * stoneWidth and stoneHeight give more information about the transformation
 * between these two coordinate-types.
 * 
 * @author Oliver
 *  
 */
public class Const {

	/**
	 * This int represents the width of one stone. It is used to convert
	 * realCoordinates into Labyrinth-Coordinates and the other way round :<br>
	 * 1. (real to Labyrinth) : x_inLabyrinthCoordinates =
	 * (int)Math.floor(x_inRealCoordinates / stoneWidth) <br>
	 * 2. (Labyrinth to real) : x_inRealCoordinates = x_inLabyrinthCoordinates *
	 * stoneWidth<br>
	 * Default : 30
	 */

	public static int stoneWidth = 30;

	/**
	 * This int represents the height of one stone. It is used to convert
	 * realCoordinates into Labyrinth-Coordinates and the other way round :<br>
	 * 1. (real to Labyrinth) : y_inLabyrinthCoordinates =
	 * (int)Math.floor(y_inRealCoordinates / stoneHeight) <br>
	 * 2. (Labyrinth to real) : y_inRealCoordinates = y_inLabyrinthCoordinates *
	 * stoneHeight<br>
	 * Default : 30
	 */

	public static int stoneHeight = 30;
	
	/**
	 * This int represents the width of the playing-area in
	 * Labyrinth-coordinates. <br>
	 * Default : 35
	 * 
	 * @see #playingWidthReal
	 */
	public static int playingWidth = 30;

	/**
	 * This int represents the width of the playing-area in real coordinates. <br>
	 */
	public static int playingWidthReal = stoneWidth*playingWidth; //inklusive Rahmen : von x == 0 bis x
										// == 999

	

	/**
	 * The left border of the playing-area in Labyrinth-Coordinates. (0 is default)
	 */
	public static int leftPlayingBorder = 0;

	/**
	 * The right border of the playing-area in Labyrinth-Coordinates.
	 */
	public static int rightPlayingBorder = leftPlayingBorder + playingWidth - 1;

	
	

	/**
	 * This int represents the height of the playing-area in
	 * Labyrinth-coordinates. <br>
	 * Default : 25
	 * 
	 * @see playingHeightReal
	 */
	public static int playingHeight = 20;
	
	/**
	 * This int represents the height of the playing-area in real coordinates.
	 * <br>
	 */
	public static int playingHeightReal = stoneHeight*playingHeight;

	/**
	 * The highest border of the playing-area. (0 is default)
	 */
	public static int topPlayingBorder = 0;

	/**
	 * the lowest border of the playing-area.
	 */
	public static int bottomPlayingBorder = topPlayingBorder + playingHeight - 1;
	

	/**
	 * This represents the maximum length of the right ImagePath, which
	 * is computed from the constants playingWidth and playinghHeight
	 */
	public static int rightPathLength = (playingWidth * playingHeight) / 40;

	
	
	/**
	 * This int represents the width of the Frame for the Labyrinth. It can be
	 * simply changed by a reference. (e.g. Const.frameWidth = 1280). <br>
	 * frameWidth is by default on 1024
	 */
	public static int frameWidth = 1050;

	/**
	 * This int represents the height of the Frame for the Labyrinth. It can be
	 * simply changed by a reference. (e.g. Const.frameHeight = 1024). <br>
	 * frameHeight is by default on 768
	 */
	public static int frameHeight = 880;

	/**
	 * This int sets the direction, in which the path begins to wander. <br>
	 * It is by default on north.
	 */
	public static Direction startDirection = Direction.NORTH;

	public static Color playerColor = new Color(232, 187, 62);

	public static Color pathColor = new Color(177, 87, 38);

	public static Color backGroundColor = new Color(118, 187, 62);
	
	public static Color finishColor = new Color(213, 187, 106);

	public static final Color defaultColor = Color.WHITE;
	

	/**
	 * This method determines, whether the point lies in the playing-area
	 * (excluding the borders) or outside of it.
	 * 
	 * @param vp
	 *            the point which needs to be checked
	 * @return true when the point lies in the playing borders. false otherwise.
	 */
	public static boolean inPlayingArea(VirtualPoint vp) {
		if (vp.getX() >= leftPlayingBorder)
			if (vp.getX() <= rightPlayingBorder)
				if (vp.getY() >= topPlayingBorder)
					if (vp.getY() <= bottomPlayingBorder)
						return true;
		return false;
	}

}