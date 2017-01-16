package ca.utoronto.utm.paint.events.shapes;

import ca.utoronto.utm.paint.shapes.PaintShape;

/**
 * This event is called when a new Shape has been created.
 *
 * Typically this occurs when the user has lifted the
 * mouse-button or the dragged the cursor off the window.
 *
 * @author Maxim
 * @version 1.0
 * @since 14-11-2016
 */
public class ShapeCreatedEvent extends ShapeEvent {

	public ShapeCreatedEvent(PaintShape shape) {
		super(shape);
	}
}
