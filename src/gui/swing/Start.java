package gui.swing;

import utilities.IField;
import utilities.Point;
import utilities.VirtualPoint;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Start extends JPanel implements IField {

	/**
	 *
	 */
	private static final long serialVersionUID = 109249440136127L;
	private final Image image = getToolkit().createImage(
			System.getProperty("user.dir") + File.separator + "images" + File.separator + "start.png");

	public int x;
	public int y;
	public final int width;
	public final int height;

	public Start(VirtualPoint start) {
		super();
		setPosition(start);
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
	
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		arg0.drawImage(image, 0, 0, this);
	}

	public Point getPosition() {
		return new Point((double)x/width, (double)y/height);
	}
}