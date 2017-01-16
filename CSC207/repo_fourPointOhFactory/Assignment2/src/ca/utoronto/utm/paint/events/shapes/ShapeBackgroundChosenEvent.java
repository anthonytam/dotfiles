package ca.utoronto.utm.paint.events.shapes;

import ca.utoronto.utm.paint.shapes.PaintShape;

import java.awt.*;

/**
 * This event is called when the user has selected a new background color.
 *
 * @author Maxim
 * @version 1.0
 * @since 16-11-2016
 */
public class ShapeBackgroundChosenEvent extends ShapeEvent {
	private Color selectedColor;

	public ShapeBackgroundChosenEvent(PaintShape currentShape, Color selectedColor) {
		super(currentShape);
		this.selectedColor = selectedColor;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
}
