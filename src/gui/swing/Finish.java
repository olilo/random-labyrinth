package gui.swing;

import utilities.*;
import utilities.Point;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Finish extends JPanel implements IField {

	/**
	 *
	 */
	private static final long serialVersionUID = 1092457236127L;
	private final Image image = getToolkit().createImage(
			System.getProperty("user.dir") + File.separator + "images" + File.separator + "ziel.png");
	public int bewegungsschritte = 10;

	public int x;
	public int y;
	public final int width;
	public final int height;

	public Finish(VirtualPoint finish) {
		super();
		setPosition(finish);
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