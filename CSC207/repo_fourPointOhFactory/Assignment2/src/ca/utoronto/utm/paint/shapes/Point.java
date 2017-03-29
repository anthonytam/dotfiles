package ca.utoronto.utm.paint.shapes;

/**
 * Creates a new point object. Points represent a coordinate on the screen
 */
public class Point {
	// This point represents the top-left corner of the screen.
	public final static Point ZERO = new Point(0, 0);

	private int x, y;

	/**
	 * Create a new point object
	 *
	 * @param x The x coordinate of the point
	 * @param y The y coordinate of the point
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x value of the point
	 *
	 * @return An integer representing the x coordinate of the point
	 */
	public int getX() {
		return x;
	}

	/**
	 * Changes the x coordinate of the point
	 *
	 * @param x An integer representing a new x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y values of the point
	 *
	 * @return An integer representing the y coordinate of the point
	 */
	public int getY() {
		return y;
	}

	/**
	 * Changes the y coordinate of the point
	 *
	 * @param y AN integer representing the y coordinate of the point
	 */
	public void setY(int y) {
		this.y = y;
	}
}