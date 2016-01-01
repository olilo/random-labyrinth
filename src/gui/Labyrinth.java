package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.event.*;

import utilities.*;

/**
 * 
 * This Class starts the whole Labyrinth. It has a main method, which creates an
 * object of itself.
 * 
 * @author Oliver
 * 
 * @version 1.1
 */
public class Labyrinth implements ActionListener{

	public Background background;
	public JMenuBar menu;
	public PathGenerator paths;
	private ImagePath currentPath;
	public Player2 player;
	public JDialog victory;
	private JLabel waiting;
	private LabColorChooser lcc;
	
	int playerID;
	int finishID;
	
	final String zielImage = System.getProperty("user.dir") + File.separator + "images" + File.separator + "ziel.png";
	final String startImage = System.getProperty("user.dir") + File.separator + "images" + File.separator + "start.png";
	

	public Labyrinth() {
		paths = new PathGenerator();
		
		setVictoryFrame();
		buildMenu();
		buildBackground();
		setColorChooser();
		setWaitingLabel();
		background.pack();
		background.setVisible(true);
	}
	
/*  ---------------------------------------------
	|	Methods called by the Constructor		|
	---------------------------------------------
*/
	
	public void setVictoryFrame() {
		victory = new JDialog();
		victory.setPreferredSize(new Dimension(300, 150));
		victory.setTitle("Sieg!");
		victory.setResizable(false);
		
		JTextArea victoryText = new JTextArea();
		victoryText.setText("Herzlichen Glückwunsch, \n Sie haben gewonnen. \n Möchten Sie noch einmal spielen?");
		victoryText.setEditable(false);
		//victoryText.setHorizontalTextPosition(JLabel.CENTER);
		victory.getContentPane().add(victoryText, BorderLayout.CENTER);
		
		JPanel buttonArea = new JPanel();
		victory.getContentPane().add(buttonArea, BorderLayout.AFTER_LAST_LINE);
		
		JButton neuSpiel = new JButton("Neues Spiel");
		neuSpiel.setActionCommand("neues_spiel");
		neuSpiel.addActionListener(this);
		buttonArea.add(neuSpiel, BorderLayout.WEST);
		
		JButton ende = new JButton("Spiel beenden");
		ende.setActionCommand("ende");
		ende.addActionListener(this);
		buttonArea.add(ende, BorderLayout.EAST);
		
		victory.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		victory.addComponentListener(new FocusToBackground());
		
		victory.pack();
		victory.setLocationRelativeTo(background);
	}
	
	public void buildMenu() {
		
		menu = new JMenuBar();
		
// ----------------------- First Menu --------------------------
		JMenu m1 = new JMenu("Spiel");
		menu.add(m1);
		
		JMenuItem mi1_1 = new JMenuItem("Neues Spiel starten");
		mi1_1.setEnabled(true);
		mi1_1.setActionCommand("neues_spiel");
		mi1_1.addActionListener(this);
		m1.add(mi1_1);
		
		JMenuItem mi1_2 = new JMenuItem("Spiel speichern");
		mi1_2.setEnabled(false);
		m1.add(mi1_2);
		
		JMenuItem mi1_3 = new JMenuItem("Spiel laden");
		mi1_3.setEnabled(false);
		m1.add(mi1_3);
		
		JMenuItem mi1_4 = new JMenuItem("Spiel beenden");
		mi1_4.setEnabled(true);
		mi1_4.setActionCommand("ende");
		mi1_4.addActionListener(this);
		m1.add(mi1_4);
		
// ---------------------- Second Menu --------------------------
		JMenu m2 = new JMenu("System");
		menu.add(m2);
		
		JMenuItem mi2_1 = new JMenuItem("Farben ändern");
		mi2_1.setEnabled(true);
		mi2_1.setActionCommand("farbe_aendern");
		mi2_1.addActionListener(this);
		m2.add(mi2_1);

// --------------------- End of Menu Initialization ------------
	}
	
	public void buildBackground() {
		background = new Background();
		background.addKeyListener(new WorkspaceKeyListener());
		background.setBackground(Const.backGroundColor);
		background.setJMenuBar(menu);
		background.setPreferredSize(new Dimension(Const.frameWidth, Const.frameHeight));
		background.setTitle("Das Labyrinth");
		background.buildGraphicsInterface();
		background.graphicsInterface.setPreferredSize(new Dimension(Const.playingWidthReal, Const.playingHeightReal));
	}
	
	private void setWaitingLabel() {
		waiting = new JLabel("Bitte warten Sie einen Moment. Das Labyrinth wird erstellt.");
		waiting.setFont(waiting.getFont().deriveFont((float)25));
		waiting.setHorizontalTextPosition(JLabel.CENTER);
		waiting.setVerticalTextPosition(JLabel.CENTER);
		background.graphicsInterface.add(waiting);
	}
	
	public void setColorChooser() {
		lcc = new LabColorChooser(background);
		//lcc.addSource("ImagePath", "Farbe des Pfades ändern.", Const.pathColor);
		lcc.addSource("Background", "Hintergrundfarbe ändern", Const.backGroundColor);
		//lcc.addSource("Player", Const.playerColor);
		lcc.addSource("Finish", Const.finishColor);
		
		lcc.addColorChangeListener(new ColorChangeListener());
		lcc.addComponentListener(new FocusToBackground());
		lcc.pack();
	}
	
	public void buildLabyrinth() {
		waiting.setVisible(true);
		
		background.graphicsInterface.cleanUp();
		currentPath = paths.buildNewPath();
		JScrollPane pane = new JScrollPane(currentPath);
		pane.setBounds(0, 0, background.getWidth(), background.getHeight());
		background.graphicsInterface.add(pane, GraphicsInterface.DEFAULT_LAYER);
		background.graphicsInterface.moveToBack(pane);
		player = new Player2(paths.getStart(), paths.getFinish());
		//playerID = background.graphicsInterface.addLP(player, Const.playerColor);
		final JPanel startPanel = new JPanel () {
			private static final long serialVersionUID = -46545424L;
			private final Image toDraw = getToolkit().createImage(startImage);
			@Override
			protected void paintComponent(Graphics g) {
				System.out.println("ziel...");
				super.paintComponent(g);
				g.drawImage(toDraw, 0, 0, this);
			}
		};
		startPanel.setPreferredSize(new Dimension(30, 30));
		startPanel.setLocation(0, 0);
		startPanel.setOpaque(false);
		final JPanel finishPanel = new JPanel () {
			private static final long serialVersionUID = -987534L;
			private final Image toDraw = getToolkit().createImage(zielImage);
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(toDraw, 0, 0, this);
			}
		};
		finishPanel.setPreferredSize(new Dimension(30, 30));
		finishPanel.setBounds(paths.getFinish().toRealRectangle());
		finishPanel.setLocation(paths.getFinish().toRealRectangle().getLocation());
		finishPanel.setOpaque(false);
		background.graphicsInterface.add(startPanel, GraphicsInterface.DEFAULT_LAYER);
		background.graphicsInterface.moveToFront(startPanel);
		startPanel.repaint();
		background.graphicsInterface.add(player, GraphicsInterface.DEFAULT_LAYER);
		background.graphicsInterface.moveToFront(player);
		finishID = background.graphicsInterface.addLP(paths.getFinish(), Const.finishColor);
		background.graphicsInterface.add(finishPanel, GraphicsInterface.DEFAULT_LAYER);
		background.graphicsInterface.moveToFront(finishPanel);
		finishPanel.repaint();
		background.validate();
		waiting.setVisible(false);
	}
	
/*  ---------------------------------------------
	|			The main-Method					|
	---------------------------------------------
*/

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(false);
		Labyrinth s = new Labyrinth();
		s.buildLabyrinth();
	}
	
/*  ---------------------------------------------
	|	Methods for the implemented Listeners	|
	---------------------------------------------
*/
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("neues_spiel")) {
			victory.setVisible(false);
			background.graphicsInterface.cleanUp();
			buildLabyrinth();
		}
		if (arg0.getActionCommand().equals("farbe_aendern")) {
			lcc.setLocationRelativeTo(background);
			lcc.setVisible(true);
		}
		if (arg0.getActionCommand().equals("ende")) {
			System.exit(0);
		}
	}
	
/*  ---------------------------------------------
	|		Additional Inner Classes			|
	---------------------------------------------
*/
	
	class WorkspaceKeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			Direction dir = Direction.NONE;	// don't move
			switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					dir = Direction.EAST;
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					dir = Direction.WEST;
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					dir = Direction.NORTH;
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					dir = Direction.SOUTH;
					break;
				case KeyEvent.VK_L:
					if (e.isAltDown() && e.isControlDown()) {
						player.setPosition(player.getFinish());
					}
					break;
				default :
					break;
			}
			player.move(dir);
			if (!player.isPartOf(currentPath)) {
				player.move(dir.revert());
			}
			if (player.atFinish()) {
				victory.setVisible(true);
				//background.showVictoryDialog();
			}
		}
	}
	
	class TextualOutputWFL extends WindowAdapter {
		public void windowGainedFocus(WindowEvent e) {
			System.out.println("Fokus f�r das Fenster : " + e.getWindow().toString());
		}
		public void windowLostFocus(WindowEvent e) {
			System.out.println("Kein Fokus mehr f�r das Fenster : " + e.getWindow().toString());
		}
		
	}

	class ColorChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			Color temp = (Color)lcc.colors.get(lcc.colorSource);
			if ((lcc.colorSource.equals("ImagePath")) && (paths != null)) {
				background.graphicsInterface.changeColor(temp, paths.wrongPathsID);
				background.graphicsInterface.changeColor(temp, paths.rightPathID);
			}
			if (lcc.colorSource.equals("Player")) {
				background.graphicsInterface.changeColor(temp, playerID);
			}
			if (lcc.colorSource.equals("Finish")) {
				background.graphicsInterface.changeColor(temp, finishID);
			}
			if (lcc.colorSource.equals("Background")) {
				background.graphicsInterface.setBackground(temp);
			}
		}
	}

	class FocusToBackground extends ComponentAdapter {
		public void componentHidden(ComponentEvent arg0) {
			background.requestFocus();
		}
		
		public void componentShown(ComponentEvent arg0) {
			((Window)arg0.getSource()).setLocationRelativeTo(background);
			((Component)arg0.getSource()).requestFocus();
		}
	}
}