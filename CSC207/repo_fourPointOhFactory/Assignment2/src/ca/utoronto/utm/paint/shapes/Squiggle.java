package ca.utoronto.utm.paint.shapes;

import java.awt.*;
import java.util.List;

/**
 * A Squiggle is a Composite shape composed strictly of lines.
 * While lines may be added arbitrarily the proper practice is
 * to add Points in a sequence using `addPoint`.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public class Squiggle extends CompositePaintShape<Line> {

	public Squiggle(Point origin, Stroke stroke, Color color) {
		super(origin);
		setStroke(stroke);
		setColor(color);
	}

	/**
	 * Returns the last connection within the composite.
	 * @return If there are no existing connections this will return the origin.
	 */
	public Point getEnding() {
		if (parts.isEmpty())
			return origin;
		List<Line> parts = getParts();
		return parts.get(parts.size() - 1).getDestination();
	}

	/**
	 * Adds a new connection to the Squiggle. This point will be connected
	 * to the previous ending in the composite.
	 * @param point New point to connect within the composite.
	 */
	public void addPoint(Point point) {
		addPart(new Line(getEnding(), point, getColor(), getStroke()), 0);
	}

	/**
	 * This method must be called multiple times consecutively in order to create
	 * a true Squiggle. A drag is interpreted as a new connection which is joined
	 * to the previous point. If there is no 'previous' point the origin of the
	 * shape is substituted instead.
	 *
	 * @param origin The point at which the click first originated. Squiggle ignores this value.
	 * @param to Creates a new connection from the last point.
	 */
	@Override
	public void updateShapeFromDrag(Point origin, Point to) {
		addPoint(to);
	}
}
