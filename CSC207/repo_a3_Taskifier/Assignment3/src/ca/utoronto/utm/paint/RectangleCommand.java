package ca.utoronto.utm.paint;
import ca.utoronto.utm.paint.serialization.Serial;

import java.awt.Graphics2D;

public class RectangleCommand implements PaintCommand {
	private Rectangle rectangle;
	public RectangleCommand(Rectangle rectangle){
		this.rectangle = rectangle;
	}
	public void execute(Graphics2D g2d){
		g2d.setColor(rectangle.getColor());
		Point topLeft = this.rectangle.getTopLeft();
		Point dimensions = this.rectangle.getDimensions();
		if(rectangle.isFill()){
			g2d.fillRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		} else {
			g2d.drawRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		}
	}

	/**
	 * Convert the given rectangle to a serial object to prepare the shape for saving to a file
	 * @return A serial representation of the file to be saved
	 */
	@Override
	public Serial serialize() {
		Serial serial = new Serial("Rectangle");
		serial.addValue("color", rectangle.getColor());
		serial.addValue("filled", rectangle.isFill());
		serial.addValue("p1", rectangle.getP1());
		serial.addValue("p2", rectangle.getP2());
		return serial;
	}
}
