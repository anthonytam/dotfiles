package ca.utoronto.utm.paint.shapes;
import java.awt.*;
import java.util.Arrays;

/**
 * Creates a triangle with a base and a height
 *
 * @author Swetha
 * @version 1.0
 * @since 15-11-2016
 */
public class Triangle extends FillablePaintShape {
	int base; 
	int height; // The width of the Rectangle in terms of pixels on the x-axis.
	int [ ] x = new int [3];
	int [ ] y = new int [3];
	
	public Triangle(Point origin, Color color, Stroke stroke, Color fill, int base, int height) {
		super(origin, color, stroke, fill);
		this.height = height;
		this.base = base;
	}

	@Override
	public void draw(Graphics2D graphics) {
		Polygon toDraw = new Polygon(x, y, 3);

		graphics.setStroke(stroke);
		graphics.setColor(fillColor);

		graphics.fill(toDraw);

		graphics.setColor(color);
		
		graphics.drawPolygon(toDraw);
	}

	@Override
	public void updateShapeFromDrag(Point origin, Point to) {
		Bound clickBound = new Bound(origin, to);

		this.setOrigin(clickBound.getTopLeft());
		this.height = clickBound.getYLength();
		this.base = clickBound.getXLength();
		
		x[0] = origin.getX();
		x[1] = to.getX();
		x[2] = origin.getX();
		
		y[0] = origin.getY();
		y[1] = to.getY();
		y[2] = to.getY();
	}

	public int getBase() {
		return this.base;
	}

	public void setBase(int base) {
		this.base = base;
	}

	@Override
	public Triangle clone() {
		Triangle clone = (Triangle) super.clone();
		clone.x = Arrays.copyOf(x, 3);
		clone.y = Arrays.copyOf(y, 3);
		return clone;
	}
}
