package gui.swing;

import java.awt.*;
import java.io.File;

import javax.swing.JPanel;

import utilities.*;
import utilities.Point;

public class Player2 extends JPanel implements IField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1096633925916536127L;
	private VirtualPoint finish;
	private final Image image = getToolkit().createImage(
			System.getProperty("user.dir") + File.separator + "images" + File.separator + "spieler.png");
	public int bewegungsschritte = 10;
	
	public int x;
	public int y;
	public final int width;
	public final int height;

	public Player2(VirtualPoint start, VirtualPoint finish) {
		super();
		setPosition(start);
		this.finish = finish;
		setOpaque(false);
		width = 30;
		height = 30;
	}
	
	public void setPosition(VirtualPoint start) {
		x = (int)(start.getX()*start.getWidth());
		y = (int)(start.getY()*start.getWidth());
		setPreferredSize(new Dimension(30, 30));
		setBounds(x, y, 30, 30);
	}
	
	public VirtualPoint getFinish() {
		return finish;
	}
	
	public void setFinish(VirtualPoint finish) {
		this.finish = finish;
	}

	public boolean atFinish() {
		System.out.println(this.getPosition());
		System.out.println(finish.getPosition());
		if (this.getPosition().equals(finish.getPosition()))
			return true;
		else
			return false;
	}
	
	public void move(Direction dir) {
		switch(dir) {
			case NORTH : y -= bewegungsschritte; break;
			case EAST : x += bewegungsschritte; break;
			case SOUTH : y += bewegungsschritte; break;
			case WEST : x -= bewegungsschritte; break;
		}
		setLocation(x, y);
	}
	
	public boolean isPartOf(Path p) {
		Rectangle rect = getBounds();
		double width = rect.width;
		double height = rect.height;
		double x = rect.x / width;
		double y = rect.y / height;
		return new VirtualPoint(x, y, width, height).isPartOf(p);
	}
	
	
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		
		arg0.drawImage(image, 0, 0, this);
	}

	public Point getPosition() {
		return new Point((double)x/width, (double)y/height);
	}
}