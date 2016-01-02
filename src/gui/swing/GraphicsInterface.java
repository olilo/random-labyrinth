package gui.swing;

import utilities.VirtualPoint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;


/**
 * This Class draws Shapes and VirtualPoints. It extends JLayeredPane, so
 * the main functions can be reviewed there. <br>
 * The only thing, where this Class differs from JPanel, is the ability to draw
 * VirtualPoints and to add any shape you like to add. It gives every entry a
 * unique id, so you can edit specific entries with the editEntry method. <br>
 * In the future there will also be a possibility to remove entries. <br>
 * 
 * @author OlilO
 */
public class GraphicsInterface extends JLayeredPane {
	
	private static final long serialVersionUID = 2L;

	private int i = -1; // the number of drawn objects -1

	private Color defaultColor;
	private DrawingSurface surface;
	private ArrayList<Shape> shapes;
	private ArrayList<Color> color;

	public GraphicsInterface() {
		this(Color.WHITE);
	}
	
	public GraphicsInterface(Color defaultColor) {
		super();
		
		this.defaultColor = defaultColor;

		this.surface = new DrawingSurface();
		this.surface.setOpaque(false);
		this.surface.setLocation(0, 0);
		addDrawingSurface();
		
		shapes = new ArrayList<Shape>();
		color = new ArrayList<Color>();
	}
	

	/**
	 * This method adds a Shape to the graphical Output (instead of a
	 * Shape-Object you can use any Class, that implements the Shape Class).
	 * <br>
	 * The Shape is drawn in default Color (see also defaultColor). <br>
	 * If the Shape s is null, then this method does nothing
	 * else than returning -1 (to indicate a failure). <br>
	 * 
	 * @param s :
	 *            The Shape that is added to the Graphical Interface.
	 */
	public int add(Shape s) {
		if (s != null) {
			i++;
			shapes.add(s);
			color.add(defaultColor);
			repaint(s.getBounds());
			return i;
		}
		return -1;
	}

	/**
	 * This method works like addShape(Shape) with the exception, that
	 * the Shape is drawn in the specified Color. <br>
	 * If the Shape s is null, then this method does nothing
	 * else than returning -1 (to indicate a failure). <br>
	 * 
	 * @param s :
	 *            The Shape that is added to the Graphical Interface
	 * @param c :
	 * 			  The specific Color of the Shape. If it is null, it will be ignored.
	 */
	public int add(Shape s, Color c) {
		if (s != null) {
			i++;
			shapes.add(s);
			if (c != null)
				color.add(c);
			repaint(s.getBounds());
			return i;
		}
		return -1;
	}

	/**
	 * This method adds an array of Shapes to the graphical Output. 
	 * Every Shape gets its own ID, which allows to remove or edit
	 * every Shape lateron. <br>
	 * Every Shape of the array of Shapes is drawn in the default Color (
	 * see also defaultColor). <br>
	 * If a shape in the shapes-array is null, then it won't be added
	 * to the graphical Output. The returned ID for this shape is -1.
	 * 
	 * @param s :
	 *            The array of Shapes that is added to the Graphical Interface
	 */
	public int[] addMultipleShapes(Shape[] s) {
		if (s != null) {
			int[] temp;
			temp = new int[s.length];
			for (int j = 0; j < s.length; j++) {
				if (s[j] != null) {
					i++;
					shapes.add(s[j]);
					color.add(defaultColor);
					temp[j] = i;
					repaint(s[j].getBounds());
				} else
					temp[j] = -1;
			}
			return temp;
		}
		return null;
	}

	/**
	 * This method works like addMultipleShapes(Shape[]), with the exception, that
	 * every Shape of the array of Shapes is drawn in the specified Color. <br>
	 * 
	 * @param s :
	 *            The array of Shapes that is added to the Graphical Interface
	 * @param c :
	 * 			  The specific Color of the Shapes. If it is null, it will be ignored.
	 */
	public int[] addMultipleShapes(Shape[] s, Color c) {
		if (s != null) {
			int[] temp;
			temp = new int[s.length];
			for (int j = 0; j < s.length; j++) {
				if (s[j] != null) {
					i++;
					shapes.add(s[j]);
					color.add(c);
					temp[j] = i;
				} else
					temp[j] = -1;
			}
			repaint();
			return temp;
		}
		return null;
	}

	/**
	 * This method adds the Rectangle of a VirtualPoint to the graphical
	 * Output. <br>
	 * The Rectangle is drawn in the default Color. <br>
	 * If the VirtualPoint lp is null, then this method does nothing
	 * else than returning -1 (to indicate a failure). <br>
	 * 
	 * @param lp :
	 *            The VirtualPoint that is added to the Graphical Interface.
	 */
	public int addLP(VirtualPoint lp) {
		if (lp != null) {
			i++;
			shapes.add(lp.toRealRectangle());
			color.add(defaultColor);
			repaint(lp.toRealRectangle());
			return i;
		}
		return -1;
	}

	/**
	 * This method works like addLP(VirtualPoint lp),
	 * with the exception, that the VirtualPoint is drawn in the specified Color. <br>
	 * If the VirtualPoint lp is null, then this method does nothing
	 * else than returning -1. (to indicate a failure) <br>
	 * 
	 * @param lp :
	 *            The VirtualPoint that is added to the Graphical Interface.
	 * @param c :
	 *            The specific Color of the VirtualPoint. If it is null, it will be ignored.
	 */
	public int addLP(VirtualPoint lp, Color c) {
		if (lp != null) {
			i++;
			shapes.add(lp.toRealRectangle());
			if (c != null)
				color.add(c);
			repaint(lp.toRealRectangle());
			return i;
		}
		return -1;
	}

	/**
	 * This method lets you edit an entry, that you've added.
	 * It uses the unique id of that entry. 
	 * The entry is replaced by the parameter entry. <br>
	 * The Color of that entry remains. If the parameter entry is null,
	 * then this method does nothing. <br>
	 * 
	 * @param entry :
	 *            The shape that will replace the existing entry.
	 * @param id :
	 *            The unique id of the entry.
	 */
	public void editEntry(Shape entry, int id) {
		if (entry != null) {
			shapes.set(id, entry);
		}
		repaint();
	}

	/**
	 * This method works like editEntry(Shape, int).
	 * Additionally the Shape's Color will be replaced by the specified Color,
	 * as long as the specified Color isn't null. <br>
	 * 
	 * @param entry :
	 *            The shape that will replace the existing entry. If it is null, it will be ignored.
	 * @param c :
	 *            The Color that will be used for that entry. If it is null, it will be ignored.
	 * @param id :
	 *            The unique id of the entry.
	 */
	public void editEntry(Shape entry, Color c, int id) {
		if (entry != null)
			shapes.set(id, entry);
		if (c != null)
			color.set(id, c);
		repaint();
	}
	
	/**
	 * This method lets you update the color of the shape with 
	 * the given id. <br>
	 * If the Color is null, the method does nothing. else than repaint 
	 * the graphical Interface.
	 * @param c :
	 * 			The Color that will be used for that entry. If it is null, nothing happens.
	 * @param id :
	 * 			The unique id of the entry
	 */
	public void changeColor(Color c, int id) {
		if ((c != null) && id >= 0)
			color.set(id, c);
		repaint();
	}

	/**
	 * This method lets you edit an entry, that you've added.
	 * It uses the unique id of that entry. 
	 * The entry is replaced by the parameter entry. <br>
	 * The Color of that entry remains. If the parameter entry is null,
	 * then this method does nothing. <br>
	 * 
	 * @param entry :
	 *            The VirtualPoint that replaces the existing entry.
	 * @param id :
	 *            The unique id of the entry.
	 */
	public void editEntry(VirtualPoint entry, int id) {
		if (entry != null)
			shapes.set(id, entry.toRealRectangle());
		repaint();
	}

	/**
	 * This method works like editEntry(VirtualPoint, int).
	 * Additionally the VirtualPoint's Color will be replaced by the specified Color,
	 * as long as the specified Color isn't null. <br>
	 * 
	 * @param entry :
	 *            The VirtualPoint that replaces the existing entry. If it is null, it will be ignored.
	 * @param c :
	 *            The Color that will be used for that entry. If it is null, it will be ignored.
	 * @param id :
	 *            The unique id of the entry.
	 */
	public void editEntry(VirtualPoint entry, Color c, int id) {
		if (entry != null)
			shapes.set(id, entry.toRealRectangle());
		if (c != null)
			color.set(id, c);
		repaint();
	}

	/**
	 * This method removes everything that has been drawn on the Graphical
	 * Interface. <br>
	 */
	public void cleanUp() {
		i = -1;
		removeAll();
		addDrawingSurface();
		shapes.clear();
		color.clear();
		repaint();
	}
	
	

	public void drawBehindPanels() {
		this.setLayer(surface, new Integer(-1));
	}
	
	public void drawAforePanels() {
		this.setLayer(surface, new Integer(99));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		surface.paintComponent(g);
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		surface.setPreferredSize(preferredSize);
		surface.setBounds(0, 0, preferredSize.width, preferredSize.height);
		super.setPreferredSize(preferredSize);
	}
	
	private void addDrawingSurface() {
		this.add(surface);
		this.drawAforePanels();
		this.moveToFront(surface);
	}
	
	private class DrawingSurface extends JPanel {

		private static final long serialVersionUID = -6522887121687348062L;

		public void paintComponent(Graphics g) {
			System.out.println("repaint in Surface");
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			for (int j = 0; j <= i; j++) {
				g2.setColor(color.get(j));
				g2.fill(shapes.get(j));
			}
		}
	}
}