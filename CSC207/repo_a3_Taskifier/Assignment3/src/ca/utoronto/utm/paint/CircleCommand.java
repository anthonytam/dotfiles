package ca.utoronto.utm.paint;
import ca.utoronto.utm.paint.serialization.Serial;

import java.awt.Graphics2D;

public class CircleCommand implements PaintCommand {
	private Circle circle;
	public CircleCommand(Circle circle){
		this.circle=circle;
	}
	public void execute(Graphics2D g2d){
		g2d.setColor(circle.getColor());
		int x = this.circle.getCentre().x;
		int y = this.circle.getCentre().y;
		int radius = this.circle.getRadius();
		if(circle.isFill()){
			g2d.fillOval(x-radius, y-radius, 2*radius, 2*radius);
		} else {
			g2d.drawOval(x-radius, y-radius, 2*radius, 2*radius);
		}
	}

	/**
	 * Create a new Serial for the given Circle. Will group together all attributes and prepare
	 * them for saving to a file
	 * @return A Serial for the Circle.
	 */
	@Override
	public Serial serialize() {
		Serial serial = new Serial("Circle");
		serial.addValue("color", circle.getColor());
		serial.addValue("filled", circle.isFill());
		serial.addValue("center", circle.getCentre());
		serial.addValue("radius", circle.getRadius());
		return serial;
	}
}
