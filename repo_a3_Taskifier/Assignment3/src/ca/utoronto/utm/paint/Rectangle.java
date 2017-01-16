package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.serialization.Serial;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationMissingAttributeException;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationOutOfRangeException;
import ca.utoronto.utm.paint.serialization.strategies.BooleanSerializationStrategy;
import ca.utoronto.utm.paint.serialization.strategies.ColorSerializationStrategy;
import ca.utoronto.utm.paint.serialization.strategies.PointSerializationStrategy;

public class Rectangle extends Shape {
	private Point p1,p2;
	public Rectangle(Point p1, Point p2){
		this.p1 = p1; this.p2=p2;
	}
	
	public Rectangle() {
		this(new Point(0,0), new Point(0,0));
	}

	/**
	 * Creates a new Rectangle from a stored serial. This constructor will parse through the serial ensuring
	 * that all values are accepted and will throw their relative exceptions with any issues. These exceptions
	 * will then be returned to the parser as an error.
	 * @param serial The serial to read from for the new shape
	 * @throws DeserializationMissingAttributeException
	 * @throws DeserializationOutOfRangeException
	 */
	public Rectangle(Serial serial) throws DeserializationMissingAttributeException,
										   DeserializationOutOfRangeException {
		if (serial.hasValue("p1")) {
			p1 = serial.getValue("p1", new PointSerializationStrategy());
			if(p1.getX() < 0 || p1.getY() < 0) throw new DeserializationOutOfRangeException();
		}
		else throw new DeserializationMissingAttributeException();

		if (serial.hasValue("p2")) {
			p2 = serial.getValue("p2", new PointSerializationStrategy());
			if(p2.getX() < 0 || p2.getY() < 0) throw new DeserializationOutOfRangeException();
		}
		else throw new DeserializationMissingAttributeException();

		if (serial.hasValue("color")) this.setColor(serial.getValue("color", new ColorSerializationStrategy()));
		else throw new DeserializationMissingAttributeException();

		if (serial.hasValue("filled")) this.setFill(serial.getValue("filled", new BooleanSerializationStrategy()));
		else throw new DeserializationMissingAttributeException();
	}

	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public Point getP2() {
		return p2;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}

	public Point getTopLeft(){
		return new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
	}
	public Point getBottomRight(){
		return new Point(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
	}
	public Point getDimensions(){
		Point tl = this.getTopLeft();
		Point br = this.getBottomRight();
		return(new Point(br.x-tl.x, br.y-tl.y));
	}
}