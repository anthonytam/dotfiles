package ca.utoronto.utm.paint.shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * The Rectangle has a variable width in addition to length as a square.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public class Rectangle extends Square {
	int width;  // The width of the Rectangle in terms of pixels on the x-axis.

	public Rectangle(Point origin, Color color, Stroke stroke, Color fill, int length, int width) {
		super(origin, color, stroke, fill, length);
		this.width = width;
	}

	@Override
	public void draw(Graphics2D graphics) {
		// Set the stroke.
		graphics.setStroke(stroke);

		// Graphics uses this to draw the shape.
		Rectangle2D toDraw = new Rectangle2D.Float(origin.getX(), origin.getY(), width, length);

		// Fill the shape.
		graphics.setColor(fillColor);
		graphics.fill(toDraw);

		// Then draw outline.
		graphics.setColor(color);
		graphics.draw(toDraw);
	}

	/**
	 * The Rectangle will fill its Bound exactly between the origin
	 * and the destination point.
	 *
	 * @param origin The point at which the click first originated.
	 * @param to The point to which (from the origin point) the Shape will extend to in size.
	 */
	@Override
	public void updateShapeFromDrag(Point origin, Point to) {
		Bound clickBound = new Bound(origin, to);

		this.setOrigin(clickBound.getTopLeft());

		this.length = clickBound.getYLength();
		this.width = clickBound.getXLength();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
