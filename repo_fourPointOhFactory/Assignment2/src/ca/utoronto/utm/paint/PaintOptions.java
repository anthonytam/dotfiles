package ca.utoronto.utm.paint;

import java.awt.*;

/**
 * A container for all user-accessible options for the creation of a Shape.
 *
 * @author Maxim
 * @version 1.0
 * @since 14-11-2016
 */
public class PaintOptions {
	private int strokeWidth;        // Thickness of shape outline.
	private Color colorStroke;      // Color used for outlines.
	private Color colorFill;        // Color used for fill.

	public PaintOptions() {
		strokeWidth = 2;
		colorStroke = Color.black;
		colorFill = new Color(0, 0, 0, 0);
	}

	public int getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public Color getColorStroke() {
		return colorStroke;
	}

	public void setColorStroke(Color colorStroke) {
		this.colorStroke = colorStroke;
	}

	public Color getColorFill() {
		return colorFill;
	}

	public void setColorFill(Color colorFill) {
		this.colorFill = colorFill;
	}
}
