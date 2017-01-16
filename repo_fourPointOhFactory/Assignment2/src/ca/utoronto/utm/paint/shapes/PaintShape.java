package ca.utoronto.utm.paint.shapes;

import java.awt.*;

/**
 * A shape has an origin shape and a color in addition to
 * conforming to the Drawable interface.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public abstract class PaintShape implements Drawable, Cloneable {
	protected Point origin;     // The point from which the Shape begins, this is not necessarily the center.
	protected Color color;      // The color of the outline of the Shape.
	protected Stroke stroke;    // The 'stroke' or outline of the Shape.

	/**
	 * Each PaintShape must specify a set of basic values that would
	 * outline the essential rules the Shape will fulfill.
	 * @param origin Offset to the shape in relation to the current panel. This does not necessarily represent a certain part of the Shape, i.e. the center.
	 * @param color The base color (or outline) of the Shape.
	 * @param stroke The stroke to use when drawing out the shape.
	 */
	public PaintShape(Point origin, Color color, Stroke stroke) {
		this.origin = origin;
		this.color = color;
		this.stroke = stroke;
	}

	/**
	 * Prepares stroke and color for the child-class.
	 * @param graphics API used to draw graphics.
	 */
	@Override
	public void draw(Graphics2D graphics) {
		// Prepare the stroke & color.
		graphics.setStroke(stroke);
		graphics.setColor(color);
	}

	/**
	 * This method is called when the Shape is to be updated (or finalized)
	 * during the time a user is dragging and finally after the mouse is
	 * lifted. This method assumes that there is an origin point and that
	 * once the action is complete there will be a final `to` point.
	 *
	 * This method allows the Shape to update itself accordingly based on its
	 * own specifications. It is expected that the Shape will adapt to the `to`
	 * point regardless of its orientation in regards to the `origin`.
	 *
	 * @param origin The point at which the click first originated.
	 * @param to The point to which (from the origin point) the Shape will extend to in size.
	 */
	public abstract void updateShapeFromDrag(Point origin, Point to);

	/**
	 * Moves the Shape in an expected manner, as though
	 * the entire shape is being dragged.
	 *
	 * @param deltaX Added to the X position.
	 * @param deltaY Added to the Y position.
	 */
	public void move(int deltaX, int deltaY) {
		origin.setX(origin.getX() + deltaX);
		origin.setY(origin.getY() + deltaY);
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	/**
	 * Returns a new instance that hold equivalent values
	 * to this Shape.
	 *
	 * Strokes and colors are not deep-copied.
	 *
	 * @return Returns null in the event that the Shape cannot be cloned safely.
	 */
	@Override
	public PaintShape clone() {
		try {
			PaintShape shape = (PaintShape) super.clone();
			shape.origin = new Point(origin.getX(), origin.getY());
			return shape;
		} catch (CloneNotSupportedException exc) {
			exc.printStackTrace();
			return null;
		}
	}
}
