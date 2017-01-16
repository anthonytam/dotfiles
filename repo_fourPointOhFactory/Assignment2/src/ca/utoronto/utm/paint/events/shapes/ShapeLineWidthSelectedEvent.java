package ca.utoronto.utm.paint.events.shapes;

import ca.utoronto.utm.paint.PaintOptions;
import ca.utoronto.utm.paint.shapes.PaintShape;

/**
 * This event is called when the user selects a new stroke width.
 *
 * @author Maxim
 * @version 1.0
 * @since 14-11-2016
 */
public class ShapeLineWidthSelectedEvent extends ShapeOptionsEditedEvent {
	private int width;

	public ShapeLineWidthSelectedEvent(PaintShape shape, PaintOptions options, int width) {
		super(shape, options);
		this.width = width;
	}

	public int getSelectedWidth() {
		return width;
	}

	public void setSelectedWidth(int width) {
		this.width = width;
	}
}
