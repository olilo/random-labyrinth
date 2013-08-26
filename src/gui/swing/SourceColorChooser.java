package gui.swing;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.event.*;

/**
 * This Class, which extends JPanel, provides the possibility for the user
 * to change every color in the game.
 * 
 * @author Oliver
 */
public class SourceColorChooser extends JPanel implements ActionListener {
	
	
	private static final long serialVersionUID = -5686969487176481541L;
	protected JColorChooser absColorChooser;
	protected ColorSelectionModel csm;
	private HashMap<String, Color> colors;
	
	/**
	 * This String tells the ChangeListener, that observes the color changes
	 * in this ColorChooser, which color is actually chosen to be changed.
	 */
	private String colorSource;
	protected ButtonGroup chooseSource;
	protected JPanel radioPanel;
	
/*  ---------------------------------------------
	|			The Constructors				|
	---------------------------------------------
*/
	
//	 A method, that initializes the whole Class (after the "super" Constructor calls)
	
	private void initialize() {

		colors = new HashMap<String, Color>();
		absColorChooser = new JColorChooser(Color.BLUE);
		csm = absColorChooser.getSelectionModel();
		chooseSource = new ButtonGroup();
		radioPanel = new JPanel(new GridLayout(0, 1));
		absColorChooser.remove(1);
		absColorChooser.add(radioPanel, BorderLayout.SOUTH);
		this.add(absColorChooser);
		
		JButton ende = new JButton("Close");
		ende.setActionCommand("ende");
		ende.addActionListener(this);
		this.add(ende, BorderLayout.AFTER_LAST_LINE);
		this.addColorChangeListener(new SourceChangeListener());
	}
	
	
	public SourceColorChooser() {
		super();
		initialize ();
	}
	
	public SourceColorChooser(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initialize ();
	}


	public SourceColorChooser(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initialize ();
	}


	public SourceColorChooser(LayoutManager layout) {
		super(layout);
		initialize ();
	}
	
	
	
/*  ---------------------------------------------
	|		Public accessable methods			|
	---------------------------------------------
*/
	

	public String getColorSource() {
		return colorSource;
	}


	public void setColorSource(String colorSource) {
		this.colorSource = colorSource;
	}


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
		
		// if this is the first button ever added: set it as active
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
		radioPanel.validate ();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ende")) {
			this.setVisible(false);
		}
		else {
			colorSource = e.getActionCommand();
			absColorChooser.setColor( colors.get(colorSource) );
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
