package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.serialization.Serial;

import java.awt.Graphics2D;

public interface PaintCommand {
	void execute(Graphics2D g2d);
	Serial serialize();
}
