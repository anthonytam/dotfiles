package ca.utoronto.utm.paint.events;

import ca.utoronto.utm.paint.events.shapes.ShapeEvent;

/**
 * The EventDispatcher is responsible for coordinating the flow
 * of Events including notifying any listeners when a change
 * is detected.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public interface EventDispatcher {
	/**
	 * This listener will be notified on every event dispatch.
	 * @param listener Listener to register.
	 */
	void registerListener(EventListener listener);

	/**
	 * This listener will no longer be notified for event dispatches.
	 * @param listener Listener to remove.
	 */
	void unregisterListener(EventListener listener);

	/**
	 * Notifies all listeners of the event.
	 * @param event Event to dispatch.
	 */
	void dispatchEvent(ShapeEvent event);
}
