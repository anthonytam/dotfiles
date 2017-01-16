package ca.utoronto.utm.paint.events;

import ca.utoronto.utm.paint.events.shapes.ShapeEvent;

/**
 * An EventListener executes some action when it detects a ShapeEvent
 * that matches certain criteria it is listening for.
 *
 * @author Maxim
 * @version 1.0
 * @since 14-11-2016
 */
@FunctionalInterface
public interface EventListener {
	/**
	 * Execute any relevant instructions for the specified event.
	 * @param event Contains information regarding the event.
	 */
	void onEventCalled(ShapeEvent event);
}
