package ca.utoronto.utm.paint.events;

import ca.utoronto.utm.paint.PaintOptions;

/**
 * A CustomizableEvent is expected to provide the PaintOptions used by the model.
 *
 * @author Maxim
 * @version 1.0
 * @since 14-11-2016
 */
public interface CustomizableEvent {
	/**
	 * Returns the PaintOptions queried by the panel in the creation of a new PaintShape.
	 * @return
	 */
	PaintOptions getOptions();
}
