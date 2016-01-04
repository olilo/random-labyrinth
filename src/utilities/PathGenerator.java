package utilities;

import gui.Const;

import java.util.Random;

/**
 * This Class randomly creates Start- and Finishpoint for the Labyrinth and the
 * right ImagePath, that leads from the Start to the Finish. It also creates
 * wrong paths, that lead into nowhere ...
 * 
 * @author Oliver Lorenz
 * 
 * @version 1.0
 *  
 */
public class PathGenerator {

	private Random rand = new Random();

	/**
	 * The starting Point of the Labyrinth (the player also starts here). This
	 * Point is saved in Labyrinth-Coordinates.
	 */
	private VirtualPoint start;

	/**
	 * The finish of the Labyrinth (the game ends when the player reaches this
	 * Point). This Point is saved in Labyrinth-Coordinates.
	 */
	private VirtualPoint finish;

	/**
	 * The current Point in the Labyrinth (while building the paths)
	 */
	private VirtualPoint current;

	/**
	 * The current Direction (while building the paths)
	 */
	private Direction curDir;

	/**
	 * The rightWay indicates the right way from the start to the finish. There
	 * is no other Way from the start to the finish than this way.
	 * 
	 * @see #wrongWays
	 */
	public Path rightWay;
	
	public int rightPathID = -1;

	/**
	 * The wrongWays are the ways in the labyrinth, that confuse the User by
	 * leading from the rightWay to any other direction. These ways neither
	 * meet the right way (except once when they start from the rightWay) nor each other.
	 * 
	 * @see #rightWay
	 */
	public Path wrongWays;
	
	public int fullPathsID = -1;
	
	public int wrongPathsID = -1;

	int horiz_felder = Const.playingWidth - 2;

	int verti_felder = Const.playingHeight - 2;

	public PathGenerator() {
	}

	public VirtualPoint getStart() {
		return start;
	}
	public void setStart(VirtualPoint start) {
		this.start = start;
	}
	public VirtualPoint getFinish() {
		return finish;
	}
	public void setFinish(VirtualPoint finish) {
		this.finish = finish;
	}
	
	
	public <K extends Path> K buildNewPath(Class<K> clazz) {
		randomizeStart();
		randomizeFinish();

		while (!setRightPath(clazz)) {
			rightWay.wipe();
			wrongWays.wipe();
			randomizeStart();
			randomizeFinish();
		}

		setWrongPaths();
		return (K) wrongWays.merge(rightWay);
	}

	
	private void randomizeStart() {
		int startpkt = 0;
		int x_sp = 0;
		int y_sp = 0;
		startpkt = rand.nextInt(2 * (horiz_felder + verti_felder));

		if (startpkt <= horiz_felder) {
			x_sp = startpkt;
			y_sp = Const.topPlayingBorder;
			Const.startDirection = Direction.SOUTH;
		}
		if ((startpkt > horiz_felder)
				&& (startpkt <= horiz_felder + verti_felder)) {
			x_sp = Const.rightPlayingBorder;
			y_sp = startpkt - horiz_felder;
			Const.startDirection = Direction.WEST;
		}
		if ((startpkt > horiz_felder + verti_felder)
				&& (startpkt <= 2 * horiz_felder + verti_felder)) {
			x_sp = 2 * horiz_felder + verti_felder - startpkt + 2;
			y_sp = Const.bottomPlayingBorder;
			Const.startDirection = Direction.NORTH;
		}
		if (startpkt > 2 * horiz_felder + verti_felder) {
			x_sp = Const.leftPlayingBorder;
			y_sp = 2 * (horiz_felder + verti_felder) - startpkt + 2;
			Const.startDirection = Direction.EAST;
		}
		start = new VirtualPoint(x_sp, y_sp, Const.stoneWidth, Const.stoneHeight);
		System.out.println(start);
	}

	private void randomizeFinish() {
		int endpkt = 0;
		int x_ep = 0;
		int y_ep = 0;
		int hilfsstart = 1;

		if (start.getY() == 1)
			hilfsstart = (int)start.getX();
		else if (start.getX() == horiz_felder + 1)
			hilfsstart = (int) start.getY() + horiz_felder;
		else if (start.getY() == verti_felder + 1)
			hilfsstart = 2 * horiz_felder + verti_felder - (int) start.getX() + 2;
		else
			hilfsstart = 2 * horiz_felder + 2 * verti_felder -  (int) start.getY() + 2;

		endpkt = rand.nextInt(2 * (horiz_felder + verti_felder));
		while (Math.abs(hilfsstart - endpkt) <= 2) {
			endpkt = rand.nextInt(2 * (horiz_felder + verti_felder));
		}

		if (endpkt <= horiz_felder) {
			x_ep = endpkt;
			y_ep = Const.topPlayingBorder;
		}
		if ((endpkt > horiz_felder) && (endpkt <= horiz_felder + verti_felder)) {
			x_ep = Const.rightPlayingBorder;
			y_ep = endpkt - horiz_felder;
		}
		if ((endpkt > horiz_felder + verti_felder)
				&& (endpkt <= 2 * horiz_felder + verti_felder)) {
			x_ep = 2 * horiz_felder + verti_felder - endpkt + 2;
			y_ep = Const.bottomPlayingBorder;
		}
		if (endpkt > 2 * horiz_felder + verti_felder) {
			x_ep = Const.leftPlayingBorder;
			y_ep = 2 * (horiz_felder + verti_felder) - endpkt + 2;
		}
		finish = new VirtualPoint(x_ep, y_ep, Const.stoneWidth, Const.stoneHeight);
	}

	/**
	 * This method moves the given ImagePath into a random direction that is also
	 * possible/allowed by the structure of the labyrinth. <br>
	 * If the random direction leads into the path, 2 alternative directions are
	 * checked. If they fail as well, false is returned. <br>
	 * 
	 * @param goToFinish
	 *            True :  priority is given to the direction that moves current to finish
	 * 			  False : priority is given to any direction that doesn't move current to finish
	 * @return whether the step could be made or not.
	 */
	private boolean goRandomStep(final boolean goToFinish) {
		final Direction oldDir;
		final Direction altDir1; // the first alternative Direction
		final Direction altDir2; // the second alternative Direction
		final VirtualPoint[] x;
		final boolean[] x_play;
		final boolean[] x_con;
		final boolean dTF1, dTF2, dTF3;
		final double distanceToFinish = current.distanceTo(finish);
		final int decider;
		
		oldDir = curDir;
		decider = rand.nextInt(3);
		switch (decider) {
		case 0:
			curDir = oldDir.left();
			altDir1 = oldDir;
			altDir2 = oldDir.right();
			break;
		case 1:
			curDir = oldDir;
			altDir1 = oldDir.left();
			altDir2 = oldDir.right();
			break;
		default:
			curDir = oldDir.right();
			altDir1 = oldDir;
			altDir2 = oldDir.left();
			break;
		}
		x = new VirtualPoint[10];
		x_play = new boolean[3]; // bestimmt, ob die Zielpunkte im Spielfeld
								 // sind
		x_con = new boolean[10]; // bestimmt fuer x[0 bis 9],
		// ob sie nicht im rightWay drin sind

		x[0] = current.createLP(curDir);
		x[1] = x[0].createLP(altDir1);
		x[2] = x[0].createLP(curDir);
		x[3] = x[0].createLP(altDir1.revert());
		x[4] = current.createLP(altDir1);
		x[5] = x[4].createLP(altDir1);
		x[6] = x[4].createLP(curDir.revert());
		x[7] = current.createLP(altDir2);
		x[8] = x[7].createLP(altDir2);
		x[9] = x[7].createLP(oldDir.revert());

		x_play[0] = Const.inPlayingArea(x[0]);
		x_play[1] = Const.inPlayingArea(x[4]);
		x_play[2] = Const.inPlayingArea(x[7]);
		
		for (int i = 0; i < 10; i++) {
			x_con[i] = !x[i].isPartOf(wrongWays) && !x[i].isPartOf(rightWay);
		}

		if (goToFinish) {
			dTF1 = x[0].distanceTo(finish) <= distanceToFinish;
			dTF2 = x[4].distanceTo(finish) <= distanceToFinish;
			dTF3 = x[7].distanceTo(finish) <= distanceToFinish;
		} else {
			dTF1 = x[0].distanceTo(finish) >= distanceToFinish;
			dTF2 = x[4].distanceTo(finish) >= distanceToFinish;
			dTF3 = x[7].distanceTo(finish) >= distanceToFinish;
		}

		if (x_con[0] && x_con[1] && x_con[2] && x_con[3] && x_play[0] && dTF1) {
			// curDir remains the same
			current = x[0];
			return true;
		} else if (x_con[1] && x_con[4] && x_con[5] && x_con[6] && x_play[1] && dTF2) {
			curDir = altDir1;
			current = x[4];
			return true;
		} else if ((x_con[7] && x_con[8] && x_con[9] && x_play[2] && dTF3)
				&& (decider == 1 ? x_con[3] : x_con[6])) {
			curDir = altDir2;
			current = x[7];
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method moves the given ImagePath into a random direction, that is also
	 * possible. <br>
	 * If the random direction leads into the path, 2 alternative directions are
	 * checked. If they fail as well, false is returned. <br>
	 * 
	 * @return whether the step could be made or not
	 */
	private boolean goRandomStep() {
		Direction oldDir;
		Direction altDir1; // the first alternative Direction
		Direction altDir2; // the second alternative Direction
		VirtualPoint[] x;
		boolean[] x_play;
		boolean[] x_con;
		//boolean rightInWrong = false;
		boolean walkable = true; // determines, whether we can walk on or have
								 // to
		// go back on the way

		oldDir = curDir;
		int decider = rand.nextInt(3);
		switch (decider) {
		case 0:
			curDir = oldDir.left();
			altDir1 = oldDir;
			altDir2 = oldDir.right();
			break;
		case 1:
			curDir = oldDir;
			altDir1 = oldDir.left();
			altDir2 = oldDir.right();
			break;
		default:
			curDir = oldDir.right();
			altDir1 = oldDir;
			altDir2 = oldDir.left();
			break;
		}
		x = new VirtualPoint[10];
		x_play = new boolean[3]; // bestimmt, ob die Zielpunkte im Spielfeld
								 // sind
		x_con = new boolean[10]; // bestimmt fuer x[0 bis 9],
		// ob sie nicht im rightWay drin sind

		x[0] = current.createLP(curDir);
		x[1] = x[0].createLP(altDir1);
		x[2] = x[0].createLP(curDir);
		x[3] = x[0].createLP(altDir1.revert());
		x[4] = current.createLP(altDir1);
		x[5] = x[4].createLP(altDir1);
		x[6] = x[4].createLP(curDir.revert());
		x[7] = current.createLP(altDir2);
		x[8] = x[7].createLP(altDir2);
		x[9] = x[7].createLP(oldDir.revert());

		x_play[0] = Const.inPlayingArea(x[0]);
		x_play[1] = Const.inPlayingArea(x[4]);
		x_play[2] = Const.inPlayingArea(x[7]);
		for (int i = 0; i < 10; i++) {
			x_con[i] = !x[i].isPartOf(wrongWays) && !x[i].isPartOf(rightWay);
		}

		if (x_con[1]) {
			if (x_con[0] && x_con[2] && x_con[3] && x_play[0])
				current = x[0];
			else {
				if (x_con[4] && x_con[5] && x_con[6] && x_play[1]) {
					curDir = altDir1;
					current = x[4];
				} else {
					if (x_con[7] && x_con[8] && x_con[9] && x_play[2])
						if (altDir2.equals(altDir1.revert())) {
							if (x_con[3]) {
								curDir = altDir2;
								current = x[7];
							} else
								walkable = false;
						} else {
							if (x_con[6]) {
								curDir = altDir2;
								current = x[7];
							} else
								walkable = false;
						}
					else
						walkable = false;
				}
			}
		} else {
			if (x_con[7] && x_con[8] && x_con[9] && x_play[2])
				if (altDir2.equals(altDir1.revert())) {
					if (x_con[3]) {
						curDir = altDir2;
						current = x[7];
					} else
						walkable = false;
				} else {
					if (x_con[6]) {
						curDir = altDir2;
						current = x[7];
					} else
						walkable = false;
				}
			else
				walkable = false;
		}
		return walkable;
	}

	/*private boolean findWayToFinish(int current_length, double distanceToFinish) {
		double distance = distanceToFinish;
		while ((distance > 0) && (distance <= distanceToFinish + 10)) {
			if (!goRandomStep(true, distanceToFinish))
				backTracking(current_length, 2, false);
			else
				rightWay.add(current);
			distance = current.distanceTo(finish);
		}
		if (distance <= 0)
			return true;
		else
			return false;
	}*/

	private int backTracking(int current_length, int backSteps,
			boolean createWrongWays) {
		VirtualPoint temp;
		Direction backDir;
		int current_length_temp;

		backDir = curDir.revert();
		temp = current;
		current_length_temp = current_length - 1;
		if (current_length <= backSteps)
			backSteps = current_length - 1;

		for (int i = 1; i <= backSteps; i++) {
			rightWay.subtract(temp);
			if (createWrongWays)
				wrongWays.add(temp);

			if (rightWay.contains(temp.createLP(backDir.left()))) {
				backDir = backDir.left();
			}
			if (rightWay.contains(temp.createLP(backDir.right()))) {
				backDir = backDir.right();
			}
			temp = temp.createLP(backDir);
			current_length_temp--;
		}
		current = temp;
		if (rightWay.contains(temp.createLP(backDir.left()))) {
			backDir = backDir.left();
		}
		if (rightWay.contains(temp.createLP(backDir.right()))) {
			backDir = backDir.right();
		}
		curDir = backDir.revert();

		return current_length_temp;
	}

	public boolean setRightPath(Class<? extends Path> clazz) {
		int current_length;
		double distanceToFinish;
		int totalSteps;

		current = new VirtualPoint(start);
		curDir = Const.startDirection;
		try {
			rightWay = clazz.newInstance();
			wrongWays = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		current_length = 0;
		distanceToFinish = current.distanceTo(finish);
		totalSteps = 0;

		rightWay.add(current);

		while ((current.distanceTo(finish) > 0) && (totalSteps < 2000)) {
			if (current_length > Const.rightPathLength) {
				if (!goRandomStep(true)) {
					current_length = backTracking(current_length, 1, false);
				}
			} else {
				if (distanceToFinish < 30) {
					if (!goRandomStep(false)) {
						current_length = backTracking(current_length, 1, false);
					}
				} else {
					if (!goRandomStep()) {
						current_length = backTracking(current_length, 1, true);
					}
				}
			}

			rightWay.add(current);

//			if (rightWay.length() >= 250)
//				findWayToFinish(current_length, distanceToFinish);

			distanceToFinish = current.distanceTo(finish);

			current_length++;
			System.out.println(distanceToFinish + "  ,  " + current_length
					+ "  ,  " + rightWay.length() + "  ,  " + totalSteps);
			totalSteps++;
		}

		return distanceToFinish == 0;
	}

	public void setWrongPaths() {
		for (int i = 0; i <= rightWay.length(); i++) {
			System.out.println("setWrongPaths : rightWay : " + i);
			if (rightWay.get(i) != null) {
				current = rightWay.get(i);
				while (goRandomStep()) {
					wrongWays.add(current);
				}
			}
		}
		for (int i = 0; i <= 0; i++) {
			for (int j = 0; j <= wrongWays.length(); j++) {
				System.out.println("setWrongPaths : wrongWays (" + i + ") : " + j);
				if (wrongWays.get(j) != null) {
					current = wrongWays.get(j);
					while (goRandomStep()) {
						wrongWays.add(current);
					}
				}	
			}
		}
	}

}