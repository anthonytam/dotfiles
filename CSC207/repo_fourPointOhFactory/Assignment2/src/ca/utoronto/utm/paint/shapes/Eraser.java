package ca.utoronto.utm.paint.shapes;

import java.awt.*;
import java.util.List;

public class Eraser extends CompositePaintShape<Line> {
	

	public Eraser(Point origin, Stroke stroke, Color color) {
		super(origin);
		setStroke(stroke);
		setColor(color);
	}

	public Point getEnding() {
		if (parts.isEmpty())
			return origin;
		List<Line> parts = getParts();
		return parts.get(parts.size() - 1).getDestination();
	}

	public void addPoint(Point point) {
		addPart(new Line(getEnding(), point, getColor(), getStroke()), 0);
	}

	@Override
	public void updateShapeFromDrag(Point origin, Point to) {
		addPoint(to);
	}
}
