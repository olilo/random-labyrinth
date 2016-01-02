package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.swing.GraphicsInterface;

public class TestGraphicsInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test GraphicsInterface");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GraphicsInterface graphics = new GraphicsInterface();
		graphics.setPreferredSize(new Dimension(500, 200));
		
		graphics.add(new Rectangle(100, 200), Color.RED);
		
		JPanel testPanel = new JPanel();
		testPanel.add(new JLabel("TestLabel mit gaaaaaaaaanz viel Text .... blubblkajdölfkjasdölfkjasdölksjdöflaksjdfölkasjlgöfasd"));
		testPanel.setPreferredSize(new Dimension(500, 200));
		testPanel.setLocation(0, 0);
		testPanel.setBounds(0, 0, 500, 200);
		testPanel.setOpaque(false);
		graphics.add(testPanel);
		graphics.drawBehindPanels();
		
		frame.add(graphics);
		frame.pack();
		frame.setVisible(true);
		
		graphics.add(new Rectangle(150, 0, 50, 100));
		graphics.add(new Polygon(new int[]{50,40,30,20,10}, new int[]{40,30,0,10,30}, 5));
	}

}
