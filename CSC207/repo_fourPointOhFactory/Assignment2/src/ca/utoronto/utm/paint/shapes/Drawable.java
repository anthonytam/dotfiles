package ca.utoronto.utm.paint.shapes;

import java.awt.*;

/**
 * Indicates that a class may be drawn to the PaintPanel.
 *
 * @author Maxim
 * @since 08-11-2016
 */
public interface Drawable {
	/**
	 * Displays the shape to the given panel.
	 * @param graphics API used to draw graphics.
	 */
	void draw(Graphics2D graphics);
}
