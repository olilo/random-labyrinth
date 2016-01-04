package test;

import java.awt.*;

import javax.swing.*;

import gui.swing.LabColorChooser;


/**
 * @author Oliver
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestColors {
	
	public static void main(String args[]) {
		JFrame hintergrund = new JFrame();
		hintergrund.setSize(500, 500);
		hintergrund.show();
		
		LabColorChooser lcc = new LabColorChooser(hintergrund);
		lcc.setSize(400, 400);
		lcc.addSource("test", "Blubbs", Color.pink);
		lcc.addSource("test2", null);
		lcc.show();
		hintergrund.validate();
	}

}
