package ca.utoronto.utm.paint.shapes;

import java.awt.*;

/**
 * This kind of shape has a secondary colour which fills the inside.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public abstract class FillablePaintShape extends PaintShape {
	protected Color fillColor;  // The color that will fill the space between the borders.

	public FillablePaintShape(Point origin, Color outline, Stroke stroke, Color fill) {
		super(origin, outline, stroke);
		this.fillColor = fill;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
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
	public FillablePaintShape clone() {
		return (FillablePaintShape) super.clone();
	}
}
