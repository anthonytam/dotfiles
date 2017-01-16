package ca.utoronto.utm.paint.shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * The Circle has a variable radius, where the origin does not represent
 * the center of the Circle, but rather the top-left corner.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public class Circle extends FillablePaintShape {
	private double radius;

	public Circle(Point origin, Color outline, Stroke stroke, Color fill, int radius) {
		super(origin, outline, stroke, fill);
		this.radius = radius;
	}

	/**
	 * A Circle is drawn based on the field values passed to the class.
	 * The origin is considered the center of the circle.
	 *
	 * @param graphics API used to draw the Circle, it will be drawn to this panel.
	 */
	@Override
	public void draw(Graphics2D graphics) {
		super.draw(graphics);

		// Set the stroke.
		graphics.setStroke(stroke);

		// Graphics uses this to draw the shape.
		Ellipse2D toDraw = new Ellipse2D.Float(origin.getX(), origin.getY(),
				(float)Math.pow(radius, 2), (float)(Math.pow(radius, 2)));

		// Fill the shape.
		graphics.setColor(fillColor);
		graphics.fill(toDraw);

		// Then draw outline.
		graphics.setColor(color);
		graphics.draw(toDraw);
	}

	/**
	 * The values applied to the Circle through this method will guarantee
	 * that only a quarter of the circle is contained within the Bound between
	 * the 'origin' and the 'to' point. The shape will position itself in
	 * such a manner that the center will match the origin.
	 *
	 * @param origin The point at which the click first originated, also the center of the circle.
	 * @param to The diagonal distance from this to the origin is considered the radius of the circle.
	 */
	@Override
	public void updateShapeFromDrag(Point origin, Point to) {
		Bound sqBound = new Bound.Square(origin, to);
		this.radius = sqBound.getDiagonalLength();

		this.origin = new Point(origin.getX() - (int)(this.radius * this.radius) / 2, origin.getY() - (int)(this.radius * this.radius) / 2);
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}
}
