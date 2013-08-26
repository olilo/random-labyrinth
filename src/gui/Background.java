package gui;

import java.awt.event.*;

import javax.swing.*;

import utilities.GraphicsInterface;


/**
 * This is the Main Frame. It extends JFrame. <br>
 * It contains the GraphicsInterface which draws Shapes.
 * 
 * @author Oliver
 */
public class Background extends JFrame {

	
	public GraphicsInterface graphicsInterface;
	
	public Background() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Background(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void showVictoryDialog() {
		Object[] options = {"Neues Spiel",
        	"Beenden"};
		int n = JOptionPane.showOptionDialog(this,
				"Herzlichen Glückwunsch, Sie haben gewonnen. Möchten Sie noch einmal spielen?",
				"Sieg",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //don't use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
		if (n == 1)
			System.exit(0);
			
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