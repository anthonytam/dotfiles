package ca.utoronto.utm.paint.events.shapes;

import ca.utoronto.utm.paint.PaintOptions;
import ca.utoronto.utm.paint.events.CustomizableEvent;
import ca.utoronto.utm.paint.shapes.PaintShape;

/**
 * This event is called when the active PaintOptions have been edited directly.
 *
 * @author Maxim
 * @version 1.0
 * @since 14-11-2016
 */
public abstract class ShapeOptionsEditedEvent extends ShapeEvent implements CustomizableEvent {
	PaintOptions options;

	public ShapeOptionsEditedEvent(PaintShape shape, PaintOptions options) {
		super(shape);
		this.options = options;
	}

	@Override
	public PaintOptions getOptions() {
		return options;
	}
}
