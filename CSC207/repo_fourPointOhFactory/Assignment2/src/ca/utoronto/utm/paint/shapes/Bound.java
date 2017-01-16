package ca.utoronto.utm.paint.shapes;

/**
 * The Bound class will sort any two points and then construct a Bound rectangle
 * based on the min's and max's of the points.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public class Bound {
	Point topLeft;
	Point bottomRight;

	/**
	 * Specify the limits on the x and y axis, the first and second points
	 * represent the extremities of the Bound.
	 * @param first The first point within the bound.
	 * @param second The second point within the bound.
	 */
	public Bound(Point first, Point second) {
		topLeft = new Point(Math.min(first.getX(), second.getX()), Math.min(first.getY(), second.getY()));
		bottomRight = new Point(Math.max(first.getX(), second.getX()), Math.max(first.getY(), second.getY()));
	}

	private Bound() {
		bottomRight = topLeft = Point.ZERO;
	}

	/**
	 * Returns the lowest two X and Y values as a point.
	 * @return Point representing the two values closest to the top-left of the screen.
	 */
	public Point getTopLeft() {
		return topLeft;
	}

	/**
	 * Returns the highest two X and Y values as a point.
	 * @return Point representing the two values closest to the bottom-right of the screen.
	 */
	public Point getBottomRight() {
		return bottomRight;
	}

	/**
	 * Returns the length along the x-axis of this bound.
	 * @return A positive value.
	 */
	public int getXLength() {
		return Math.abs(topLeft.getX() - bottomRight.getX());
	}

	/**
	 * Returns the length along the x-axis of this bound.
	 * @return A positive value.
	 */
	public int getYLength() {
		return Math.abs(topLeft.getY() - bottomRight.getY());
	}

	/**
	 * Returns the length of the longest side in this bound.
	 * @return Length as an int, always 0 or greater.
	 */
	public int getMaxSideLength() {
		return Math.max(Math.abs(topLeft.getX() - bottomRight.getX()),
				Math.abs(topLeft.getY() - bottomRight.getY()));
	}

	/**
	 * Returns approximate distance (precision lost in conversion to int)
	 * between the top-left and bottom-right corners of the Bound.
	 * @return Distance between corners as an int.
	 */
	public double getDiagonalLength() {
		//System.out.println(getXLength()+","+getYLength()+","+(Math.sqrt((getXLength()^2) + (getYLength()^2))));
		return Math.sqrt((getXLength()^2) + (getYLength()^2));
	}

	/**
	 * A Square bound conforms to its specific quadrant expanding from
	 * the origin point. The corner closest to the first point is
	 * defined as the 'origin' from which the Square expands.
	 *
	 * Each side will assume the length of the longest side of the Bound.
	 */
	public static class Square extends Bound {

		/**
		 * Creates a Bound with the limitation that it should
		 * always assume a square shape. It is the understanding that
		 * the most radical x and y values taken from the first and
		 * second points represent the real limits of the Bound.
		 *
		 * The first and second points passed into the constructor
		 * do not necessarily (though may) represent the 'corners'
		 * of the Bound.
		 *
		 * @param first The first point within the bound.
		 * @param second The second point within the bound.
		 */
		public Square(Point first, Point second) {
			float xLength = second.getX() - first.getX();
			float yLength = second.getY() - first.getY();

			if (Math.abs(xLength) < Math.abs(yLength))
				// Don't want to lose the quadrant, but we want them to be equal.
				xLength *= Math.abs(yLength / xLength);
			else
				yLength *= Math.abs(xLength / yLength);

			int xPos = (int) (xLength > 0 ? first.getX() : first.getX() - Math.abs(xLength));
			int yPos = (int) (yLength > 0 ? first.getY() : first.getY() - Math.abs(yLength));

			topLeft = new Point(xPos, yPos);
			bottomRight = new Point(topLeft.getX() + (int) xLength, topLeft.getY() + (int) yLength);
		}
	}
}
