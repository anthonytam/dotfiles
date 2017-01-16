package ca.utoronto.utm.paint.serialization.strategies;

import ca.utoronto.utm.paint.Point;

/**
 * The strategy to convert Point values to a given text representation. Also converts a
 * text representation to a Point value.
 */
public class PointSerializationStrategy extends SerializationStrategy<Point> {
	/**
	 * Create a new instance of the strategy
	 */
	public PointSerializationStrategy() {
		super(Point.class);
	}

	/**
	 * Convert a string to a point value
	 * @param val The string to convert
	 * @return The Point from the string
	 */
	@Override
	public Point fromString(String val) {
		String[] split = val
				// Slice the brackets
				.substring(1, val.length() - 1)
				// Split at comma.
				.split(",");
		return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
	}

	/**
	 * Convert a point to a string
	 * @param val The point to convert
	 * @return The string representation of the point
	 */
	@Override
	public String toString(Point val) {
		return String.format("(%d,%d)", val.getX(), val.getY());
	}
}
