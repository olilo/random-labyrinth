package utilities;

/**
 * Created by Oliver on 02.01.2016.
 */
public interface Path {
	/**
	 * This method adds the rectangle, that belongs to the VirtualPoint lp, to
	 * the ImagePath. <br>
	 * If the VirtualPoint is already in the ImagePath (means : contains(lp) returns true),
	 * then nothing happens to this path.
	 *
	 * @param lp
	 *            the VirtualPoint to be added to the ImagePath
	 */
	void add(VirtualPoint lp);

	/**
	 * This method subtracts the rectangle, that belongs to the VirtualPoint
	 * lp, from the ImagePath. (== removes the rectangle from the path) <br>
	 * If the VirtualPoint is not in the ImagePath (means : contains(lp) returns false),
	 * then nothing happens to this path.
	 *
	 * @param lp
	 *            the VirtualPoint to be subtracted from the ImagePath
	 */
	void subtract(VirtualPoint lp);

	boolean contains(VirtualPoint lp);

	boolean isInBounds(VirtualPoint lp);

	/**
	 * This method returns the VirtualPoint of this ImagePath at the Position of
	 * entry. If the VirtualPoint related to this entry doesn't
	 * exist, this method returns null. <br>
	 *
	 * @param entry
	 *            the number of the VirtualPoint added to this ImagePath
	 * @return The VirtualPoint or null.
	 */
	VirtualPoint get(int entry);

	Path merge(Path path);

	int length();

	void wipe();
}
