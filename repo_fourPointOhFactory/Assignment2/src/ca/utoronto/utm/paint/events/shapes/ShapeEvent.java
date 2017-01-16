package ca.utoronto.utm.paint.events.shapes;

import ca.utoronto.utm.paint.shapes.PaintShape;

/**
 * A ShapeEvent is dispatched when any action is taking regarding the
 * PaintPanel and is Shape related. This includes Selection of a new
 * Shape, the creation of a Shape, and etc.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public abstract class ShapeEvent {
	private PaintShape shape;

	public ShapeEvent(PaintShape shape) {
		this.shape = shape;
	}

	public PaintShape getShape() {
		return shape;
	}
}
