package ca.utoronto.utm.paint.events.shapes;

import ca.utoronto.utm.paint.PaintOptions;
import ca.utoronto.utm.paint.events.CustomizableEvent;
import ca.utoronto.utm.paint.shapes.PaintShape;

/**
 * Called whenever a new Shape has been selected.
 *
 * @author Maxim
 * @version 1.0
 * @since 14-11-2016
 */
public class ShapeSelectedEvent extends ShapeEvent implements CustomizableEvent {
	PaintOptions options;

	public ShapeSelectedEvent(PaintShape shape, PaintOptions options) {
		super(shape);
		this.options = options;
	}

	@Override
	public PaintOptions getOptions() {
		return options;
	}
}
