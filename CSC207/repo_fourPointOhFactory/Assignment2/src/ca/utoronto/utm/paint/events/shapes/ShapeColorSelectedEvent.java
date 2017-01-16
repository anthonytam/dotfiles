package ca.utoronto.utm.paint.events.shapes;

import ca.utoronto.utm.paint.PaintOptions;
import ca.utoronto.utm.paint.shapes.PaintShape;

import java.awt.*;

/**
 * This event is called when the user has selected either a fill or a stroke color.
 *
 * @author Maxim
 * @version 1.0
 * @since 14-11-2016
 */
public class ShapeColorSelectedEvent extends ShapeOptionsEditedEvent {
	Type type;
	Color selected;

	public ShapeColorSelectedEvent(PaintShape shape, PaintOptions options, Color selected, Type type) {
		super(shape, options);
		this.type = type;
		this.selected = selected;
	}

	public Color getSelectedColor() {
		return selected;
	}

	public void setSelectedColor(Color selected) {
		this.selected = selected;
	}

	public Type getType() {
		return type;
	}

	/**
	 * The Type of color selection.
	 * An outline color indicates the color of the stroke, whereas the fill
	 * would be any space in-between.
	 */
	public enum Type {
		FILL, OUTLINE;
	}
}
