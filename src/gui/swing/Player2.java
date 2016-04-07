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

	private int attentionSeekerPos = 0;
	private boolean attentionSeekerStop = false;

	public Player2(VirtualPoint start, VirtualPoint finish) {
		super();
		setPosition(start);
		this.finish = finish;
		setOpaque(false);
		width = 30;
		height = 30;
		new Thread(new AttentionSeeker()).start();
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
		return this.getPosition().equals(finish.getPosition());
	}
	
	public void move(Direction dir) {
		switch(dir) {
			case NORTH : y -= bewegungsschritte; break;
			case EAST : x += bewegungsschritte; break;
			case SOUTH : y += bewegungsschritte; break;
			case WEST : x -= bewegungsschritte; break;
		}
		setLocation(x, y);
		attentionSeekerStop = true;
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

		Rectangle rect = getBounds();

		if (!attentionSeekerStop) {
			arg0.setColor(Color.WHITE);
			arg0.drawOval(attentionSeekerPos, attentionSeekerPos, rect.width - 2 * attentionSeekerPos - 1,
					rect.height - 2 * attentionSeekerPos - 1);
		}
	}

	public Point getPosition() {
		return new Point((double)x/width, (double)y/height);
	}

	private class AttentionSeeker implements Runnable {

		private boolean attentionSeekerExpanding = false;

		@Override
		public void run() {
			while (true) {
				if (attentionSeekerStop) {
					return;
				}

				if (attentionSeekerExpanding) {
					attentionSeekerPos--;
				} else {
					attentionSeekerPos++;
				}
				if (attentionSeekerPos >= 10) {
					attentionSeekerExpanding = true;
					attentionSeekerPos = 10;
				} else if (attentionSeekerPos <= 0) {
					attentionSeekerExpanding = false;
					attentionSeekerPos = 0;
				}

				repaint();

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
}