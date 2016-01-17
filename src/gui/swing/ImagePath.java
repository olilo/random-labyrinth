package gui.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;

import gui.Const;
import utilities.Path;
import utilities.VirtualPoint;


/**
 * @author OlilO
 */
public class ImagePath extends JPanel implements Path {

	public final int width;
	public final int height;
	
	private static final long serialVersionUID = 2L;

	private int length = 0;

	private Square[][] squares = null;
	private Vector<VirtualPoint> vps;
	private boolean[][] content;
	
	private static final HashMap<Image, Square> allSquares = new HashMap<Image, Square>();

	private static final Square noIMG = getDefaultSquare("noIMG.png");
	
	private static final Square brick = getSquare("brick.png");
	private static final Square left = getSquare("left.png");
	private static final Square top = getSquare("top.png");
	private static final Square right = getSquare("right.png");
	private static final Square bottom = getSquare("bottom.png");
	private static final Square top_left = getSquare("top_left.png");
	private static final Square top_right = getSquare("top_right.png");
	private static final Square bottom_left = getSquare("bottom_left.png");
	private static final Square bottom_right = getSquare("bottom_right.png");
	private static final Square left_right = getSquare("left_right.png");
	private static final Square top_bottom = getSquare("top_bottom.png");
	private static final Square bottom_left_top = getSquare("bottom_left_top.png");
	private static final Square left_top_right = getSquare("left_top_right.png");
	private static final Square top_right_bottom = getSquare("top_right_bottom.png");
	private static final Square left_bottom_right = getSquare("left_bottom_right.png");
	private static final Square all = getSquare("all.png");


	/**
	 * Builds a new ImagePath with a maximum width and height of 50.
	 */
	public ImagePath() {
		this(Const.playingWidth, Const.playingHeight);
	}
	
	/**
	 * Builds a new ImagePath with the specified maximum width and height.
	 * @param width : The maximum width of the path
	 * @param height : The maximum height of the path
	 */
	public ImagePath(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		vps = new Vector<VirtualPoint>(width*height/10, width*height/10);
		content = new boolean[width][height];
		this.setOpaque(false);
	}

	/**
	 * This method adds the rectangle, that belongs to the VirtualPoint lp, to
	 * the ImagePath. <br>
	 * If the VirtualPoint is already in the ImagePath (means : contains(lp) returns true),
	 * then nothing happens to this path.
	 * 
	 * @param lp
	 *            the VirtualPoint to be added to the ImagePath
	 */
	@Override
	public void add(VirtualPoint lp) {
		if (isInBounds(lp) && !content[(int)lp.getX()][(int)lp.getY()]) {
			length++;
			content[(int)lp.getX()][(int)lp.getY()] = true;
			vps.add(lp);
		}
	}

	/**
	 * This method subtracts the rectangle, that belongs to the VirtualPoint
	 * lp, from the ImagePath. (== removes the rectangle from the path) <br>
	 * If the VirtualPoint is not in the ImagePath (means : contains(lp) returns false),
	 * then nothing happens to this path.
	 * 
	 * @param lp
	 *            the VirtualPoint to be subtracted from the ImagePath
	 */
	@Override
	public void subtract(VirtualPoint lp) {
		if (this.contains(lp)){
			length--;
			content[(int)lp.getX()][(int)lp.getY()] = false;
			vps.remove(lp);
		}
	}

	@Override
	public boolean contains(VirtualPoint lp) {
		if (lp == null)
			return false;
		
		double xDouble = lp.getX();
		double yDouble = lp.getY();
		int x = (int)xDouble;
		int y = (int)yDouble;
		boolean returnValue = isInBounds(lp) && content[x][y];
		if (returnValue && (x != xDouble)) {
			returnValue = (x + 1 < width) && content[x + 1][y];
			
			if (returnValue && (y != yDouble)) {
				returnValue &= (y+1 < height) && content[x][y+1];
				returnValue &= (y+1 < height) && content[x+1][y+1];
			}
		} else if (y != yDouble) {
			returnValue &= (y+1 < height) && content[x][y+1];
		}
		
		return returnValue;
	}	
	
	@Override
	public boolean isInBounds(VirtualPoint lp) {
		return (lp.getX() >= 0) && (lp.getX() < width) 
				&& (lp.getY() >= 0) && (lp.getY() < height);
	}
	
	/**
	 * This method returns the VirtualPoint of this ImagePath at the Position of
	 * entry. If the VirtualPoint related to this entry doesn't
	 * exist, this method returns null. <br>
	 * 
	 * @param entry
	 *            the number of the VirtualPoint added to this ImagePath
	 * @return The VirtualPoint or null.
	 */
	@Override
	public VirtualPoint get(int entry) {
		if (entry < vps.size()) {
			return vps.get(entry);
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ImagePath merge(Path path) {
		ImagePath ret;
		
		ret = new ImagePath(width, height);
		//ret.content = this.content.clone();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				ret.content[i][j] = this.content[i][j];
			}
		}
		ret.vps = (Vector<VirtualPoint>)this.vps.clone();
		ret.length = this.length;
		
		for (int x = 0; x < ret.width; x++) {
			for (int y = 0; y < ret.height; y++) {
				ret.content[x][y] |= path.contains(new VirtualPoint(x, y));
				if (!ret.content[x][y] && path.contains(new VirtualPoint(x, y))) {
					ret.length++;
					ret.content[x][y] = true;
					ret.vps.add(new VirtualPoint(x, y, Const.stoneWidth, Const.stoneHeight));
				}
			}
		}
		
		ret.squares = ret.toSquares();
		return ret;
	}

	@Override
	public int length() {
		return length;
	}
	
	@Override
	public void wipe() {
		length = 0;
		vps.clear();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				content[i][j] = false;
			}
		}
	}
	
	private Square[][] toSquares() {
		final Square[][] ret = new Square[width][height];

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if (content[i][j]) {
					boolean LEFT, TOP, RIGHT, BOTTOM;
					
					// determine whether adjacent squares are occupied
					LEFT =   (i > 0) ?          content [i-1] [j]   : false;
					TOP =    (j > 0) ?          content [i]   [j-1] : false;
					RIGHT =  (i < width-1) ?    content [i+1] [j]   : false;
					BOTTOM = (j < height - 1) ? content [i]   [j+1] : false;
					
					if (LEFT) {
						if (TOP) {
							if (RIGHT) {
								if (BOTTOM) {
									ret[i][j] = all;
								}
								else {
									ret[i][j] = left_top_right;
								}
							}
							else if (BOTTOM) {
								ret[i][j] = bottom_left_top;
							}
							else {
								ret[i][j] = top_left;
							}
						}
						else {
							if (RIGHT) {
								if (BOTTOM) {
									ret[i][j] = left_bottom_right;
								}
								else {
									ret[i][j] = left_right;
								}
							}
							else if (BOTTOM) {
								ret[i][j] = bottom_left;
							}
							else {
								ret[i][j] = left;
							}
						}
					}
					else {
						if (TOP) {
							if (RIGHT) {
								if (BOTTOM) {
									ret[i][j] = top_right_bottom;
								}
								else {
									ret[i][j] = top_right;
								}
							}
							else if (BOTTOM) {
								ret[i][j] = top_bottom;
							}
							else {
								ret[i][j] = top;
							}
						}
						else {
							if (RIGHT) {
								if (BOTTOM) {
									ret[i][j] = bottom_right;
								}
								else {
									ret[i][j] = right;
								}
							}
							else {
								ret[i][j] = bottom;
							}
						}
					}
					
				}
				else
					ret[i][j] = brick;
			}
		}
		
		return ret;
	}
	
	private void paintPath(Graphics g) {
		if (squares == null) {
			squares = toSquares();
		}

		int curX = 0;
		int curY = 0;
		
		if (g.getClipBounds() == null) {
			
			// repaint all
			for (int i = 0; i < squares.length; i++) {
				for (int j = 0; j < squares[i].length; j++) {
					Square squ = squares[i][j];
					g.drawImage(squ.img, curX, curY, this);
					curX += squ.width;
					
					System.out.println("width : " + squ.width + ", height : " + squ.height);
					
					curY += squ.height;
				}
			}
		}
		else {
			
			// repaint only the specified region
			
			Rectangle bounds = g.getClipBounds();
			int x = bounds.x;
			int y = bounds.y;
			int xEnd = x + bounds.width;
			int yEnd = y + bounds.height;
			
			System.out.println("(Re-)Paint in ImagePath : x="+x+", y="+y+", xEnd="+xEnd+", yEnd="+yEnd);
			
			int i = 0;
			while ((x > curX) && (i < this.width)) {
				curX += squares[i][0].width;
				i++;
			}
			if (i != 0)
				curX -= squares[--i][0].width;
			
			int j = 0;
			while ((y > curY) && (j < this.height)) {
				curY += squares[i][j].height;
				j++;
			}
			if (j != 0)
				curY -= squares[i][--j].height;
			
			int w = -1, h = -1;
			while ((curY < yEnd) && ((j+h) < height-1)) {
				h++;
				int deltaX = 0;
				while ((curX < xEnd) && ((i+w) < width-1)) {
					w++;
					g.drawImage(squares[i+w][j+h].img, curX, curY, this);
					curX += squares[i+w][j+h].width;
					deltaX += squares[i+w][j+h].width;
				}
				curY +=	squares[i+w][j+h].height;
				w = -1;
				curX -= deltaX;
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		paintPath(g);
	}
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
		if (allSquares.containsKey(img) && (infoflags % 4 == 3)) {
			final Square squ = allSquares.get(img);
			squ.height = h;
			squ.width = w;
			allSquares.remove(img);
			System.out.println("Image updated (" + squ.source + ") - width=" + w + ", height=" + h);
			System.out.println("Remaining images in queue: " + allSquares.size());
		}
		return super.imageUpdate(img, infoflags, x, y, w, h);
	}

	private static Square getSquare(String imgPath) {
		try {
			return new Square(imgPath);
		}
		catch (ImageNotFoundException infe) {
			return noIMG;
		}
	}
	private static Square getDefaultSquare(String def) {
		try {
			return new Square(def);
		}
		catch (ImageNotFoundException infe) {
			return null;
		}
	}
	
	/**
	 * A square of the path.
	 * 
	 * @author OlilO,
	 * created on 02.03.2007
	 *
	 */
	private static class Square {
		public final Image img;
		public int width = -1;
		public int height = -1;
		
		private String source;
		
		public Square(String url) throws ImageNotFoundException{
			final String filename = System.getProperty("user.dir") + File.separator + "images" + File.separator + url;
			if (filename != null && new File(filename).exists()) {
				img = Toolkit.getDefaultToolkit().createImage(filename);
			} else throw new ImageNotFoundException("The Image " + url + " couldn't be found");
			source = url;
			allSquares.put(img, this);
		}
		
		@Override
		public String toString() {
			return "Square : width="+width+", height="+height+", source="+source;
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(noIMG);
		Square squ = new Square("noIMG.png");
		System.out.println(squ);
		for (int i = 0; i < 100; i++) {
			System.out.println(squ.img.getWidth(new ImagePath()));
			System.out.println(squ);
			Thread.sleep(200);
		}
	}
}