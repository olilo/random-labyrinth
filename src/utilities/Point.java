/**
 * 
 */
package utilities;

import java.util.List;
import java.util.ListIterator;

/**
 * @author OlilO
 * created on 26.11.2006
 * <br><br>
 * This class represents an n-dimensional point.
 *
 */
public class Point {
	
	private double[] coordinates;
	public final int dimension;
	
	/**
	 * Constructs the n-dimensional origin of the coordinate system.
	 */
	public Point(int n) {
		coordinates = new double[n];
		dimension = n;
	}
	
	public Point(double x, double y) {
		coordinates = new double[2];
		coordinates[0] = x;
		coordinates[1] = y;
		dimension = 2;
	}
	
	public Point(double x, double y, double z) {
		coordinates = new double[3];
		coordinates[0] = x;
		coordinates[1] = y;
		coordinates[2] = z;
		dimension = 3;
	}
	
	public Point(final List<Number> l) {
		final ListIterator<Number> iter = l.listIterator();
		for (int i = 0; iter.hasNext(); i++) {
			coordinates[i] = iter.next().doubleValue();
		}
		dimension = l.size();
	}

	public double[] getCoordinates() {
		return coordinates;
	}
	
	public boolean equals(Point p) {
		boolean isEqual = true;
		int i = 0;
		while (isEqual && i < coordinates.length) {
			isEqual &= coordinates[i] == p.coordinates[i];
			i++;
		}
		return isEqual;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point)
			return equals ((Point)obj);
		else return super.equals(obj);
	}
	
	@Override
	public String toString() {
		String str = "Point : (";
		str += coordinates[0];
		for (int i = 1; i < coordinates.length; i++) {
			str += ", " + coordinates[i];
		}
		str += ")";
		return str;
	}

}
