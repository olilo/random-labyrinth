package utilities;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.event.*;

/**
 * This Class, which extends JWindow, provides the possibility for the user
 * to change every color in the game.
 * 
 * @author Oliver
 */
public class LabColorChooser extends JWindow implements ActionListener{
	
	protected JColorChooser absColorChooser;
	protected ColorSelectionModel csm;
	public HashMap colors;
	
	/**
	 * This String tells the ChangeListener, that observes the color changes
	 * in this JWindow, which color is actually chosen to be changed.
	 */
	public String colorSource;
	protected ButtonGroup chooseSource;
	protected JPanel radioPanel;
	
/*  ---------------------------------------------
	|			The Constructors				|
	---------------------------------------------
*/
	
//	 A method, that initializes the whole Class (after the "super" Constructor calls)
	
	private void initialize() {

		colors = new HashMap();
		absColorChooser = new JColorChooser(Color.BLUE);
		csm = absColorChooser.getSelectionModel();
		chooseSource = new ButtonGroup();
		radioPanel = new JPanel(new GridLayout(0, 1));
		absColorChooser.remove(1);
		absColorChooser.add(radioPanel, BorderLayout.SOUTH);
		getContentPane().add(absColorChooser);
		
		JButton ende = new JButton("Close");
		ende.setActionCommand("ende");
		ende.addActionListener(this);
		getContentPane().add(ende, BorderLayout.AFTER_LAST_LINE);
		addColorChangeListener(new SourceChangeListener());
	}
	
	
	public LabColorChooser() {
		super();
		initialize();
	}

	public LabColorChooser(Frame arg0) {
		super(arg0);
		initialize();
	}

	public LabColorChooser(GraphicsConfiguration arg0) {
		super(arg0);
		initialize();
	}

	public LabColorChooser(Window arg0) {
		super(arg0);
		initialize();
	}

	public LabColorChooser(Window arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		initialize();
	}
	
/*  ---------------------------------------------
	|		Public accessable methods			|
	---------------------------------------------
*/
	
	public void addColorChangeListener(ChangeListener cl) {
		csm.addChangeListener(cl);
	}
	
	/**
	 * Adds a JRadioButton to the list of choosable Sources.
	 * 
	 * @param source The source, that you want to change the color of.
	 * @param c The initial Color of the source. If it's null, the initial color is set to Color.white.
	 */
	public void addSource(String source, Color c) {
		JRadioButton button = new JRadioButton(source);
		button.setActionCommand(source);
		if (chooseSource.getButtonCount() == 0) {
			colorSource = source;
			button.setSelected(true);
		}
		if (c != null) {
			colors.put(source, c);
		}
		else {
			colors.put(source, Color.white);
		}
		chooseSource.add(button);
		button.addActionListener(this);
		radioPanel.add(button);
		radioPanel.repaint();
	}
	
	/**
	 * Adds a JRadioButton to the list of choosable Sources.
	 * 
	 * @param source The source, that you want to change the color of. (e.g. "background")
	 * @param tooltip A Tooltip for the source (e.g. "Changes the color of the background")
	 * @param c The initial Color of the source. If it's null, the initial color is set to Color.white.
	 */
	public void addSource(String source, String tooltip, Color c) {
		JRadioButton button = new JRadioButton(source);
		button.setActionCommand(source);
		button.setToolTipText(tooltip);
		if (chooseSource.getButtonCount() == 0) {
			colorSource = source;
			button.setSelected(true);
		}
		if (c != null) {
			colors.put(source, c);
		}
		else {
			colors.put(source, Color.white);
		}
		chooseSource.add(button);
		button.addActionListener(this);
		radioPanel.add(button);
		radioPanel.repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ende")) {
			this.setVisible(false);
		}
		else {
			colorSource = e.getActionCommand();
			csm.setSelectedColor((Color)colors.get(colorSource));
		}
	}
	
/*  ---------------------------------------------
	|		Additional Inner Classes			|
	---------------------------------------------
*/
	
	protected class SourceChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e){
			colors.put(colorSource, absColorChooser.getColor());
		}
	}

}
