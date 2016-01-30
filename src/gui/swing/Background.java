package gui.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


/**
 * This is the Main Frame. It extends JFrame. <br>
 * It contains the GraphicsInterface which draws Shapes.
 * 
 * @author Oliver
 */
public class Background extends JFrame {

	public static Color backGroundColor = new Color(118, 187, 62);

	public GraphicsInterface graphicsInterface;

	public Background() {
		super();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public Background(String title) {
		super(title);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void buildGraphicsInterface() {
		graphicsInterface = new GraphicsInterface();
		graphicsInterface.addNotify();
		graphicsInterface.setBackground(getBackground());
		getContentPane().add(graphicsInterface);
	}
	
	public void activateSizeDetector() {
		addComponentListener(new WorkspaceSizeDetector());
	}

	class WorkspaceSizeDetector extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			System.out.println(WIDTH);
			System.out.println(HEIGHT);
		}
	}
}