package ca.utoronto.utm.paint;
import ca.utoronto.utm.paint.serialization.Serial;

import java.awt.Graphics2D;
import java.util.List;

public class SquiggleCommand implements PaintCommand {
	private Squiggle squiggle;
	public SquiggleCommand(Squiggle squiggle){
		this.squiggle = squiggle;
	}
	public void execute(Graphics2D g2d){
		List<Point> points = this.squiggle.getPoints();
		g2d.setColor(squiggle.getColor());
		for(int i=0;i<points.size()-1;i++){
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}

	/**
	 * Convert the given Squiggle to a serial to prepare the shape for saving to a file. The
	 * serial will be returned and ready for saving.
	 * @return A Serial object representing the squiggle.
	 */
	@Override
	public Serial serialize() {
		Serial serial = new Serial("Squiggle");
		serial.addValue("color", squiggle.getColor());
		serial.addValue("filled", squiggle.isFill());

		Serial points = new Serial("points");
		for (Point point : squiggle.getPoints())
			points.addValue("point", point);
		serial.addValue(points);

		return serial;
	}
}
