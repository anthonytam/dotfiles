package ca.utoronto.utm.paint.shapes;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Draws a simple line from one point to a destination.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public class Line extends PaintShape {
	private Point destination;  // Where the Line will head to.

	public Line(Point origin, Point destination, Color color, Stroke stroke) {
		super(origin, color, stroke);
		this.destination = destination;
	}

	/**
	 * Draws a line based on the stroke from the origin to the destination.
	 */
	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);
		graphics.draw(new Line2D.Float(origin.getX(), origin.getY(), destination.getX(), destination.getY()));
	}

	@Override
	public void move(int deltaX, int deltaY) {
		super.move(deltaX, deltaY);
		destination.setX(destination.getX() + deltaX);
		destination.setY(destination.getY() + deltaY);
	}

	@Override
	public void updateShapeFromDrag(Point origin, Point to) {
		this.destination = to;
	}

	public Point getDestination() {
		return destination;
	}

	public void setDestination(Point destination) {
		this.destination = destination;
	}
}
