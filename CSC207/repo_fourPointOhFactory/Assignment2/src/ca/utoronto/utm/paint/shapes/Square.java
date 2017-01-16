package ca.utoronto.utm.paint.shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Simple implementation of the FillablePaintShape abstract class.
 * A square only has a variable length.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public class Square extends FillablePaintShape {
	int length; // Length of the Square in terms of the `y` and `x` axis.

	public Square(Point origin, Color color, Stroke stroke, Color fill, int length) {
		super(origin, color, stroke, fill);
		this.length = length;
	}

	/**
	 * Draws a typical filled square with four equal sides.
	 *
	 * @param graphics API used to draw graphics.
	 */
	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);

		// Graphics uses this to draw the shape.
		Rectangle2D toDraw = new Rectangle2D.Float(origin.getX(), origin.getY(), length, length);

		// Fill the shape.
		graphics.setColor(fillColor);
		graphics.fill(toDraw);

		// Then draw outline.
		graphics.setColor(color);
		graphics.draw(toDraw);
	}

	/**
	 * The origin represents the corner of the square that will always be
	 * the center of four quadrants from which the square can expand to.
	 *
	 * The most extreme delta in either the y or x axis is used to
	 * determine the length of any given side of the shape.
	 *
	 * @param origin The point at which the click first originated.
	 * @param to The point to which (from the origin point) the Shape will extend to in size.
	 */
	@Override
	public void updateShapeFromDrag(Point origin, Point to) {
		//this.length = new Bound(origin, to).getMaxSideLength();

		Bound sqBound = new Bound.Square(origin, to);

		this.setOrigin(sqBound.getTopLeft());
		this.length = sqBound.getMaxSideLength();

		/*Bound clickBound = new Bound(origin, to);

		this.setOrigin(clickBound.getTopLeft());
		this.length = clickBound.getMaxSideLength();*/
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
