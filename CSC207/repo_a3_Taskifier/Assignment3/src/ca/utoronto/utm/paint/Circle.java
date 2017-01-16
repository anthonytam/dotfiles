package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.serialization.Serial;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationMissingAttributeException;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationOutOfRangeException;
import ca.utoronto.utm.paint.serialization.strategies.BooleanSerializationStrategy;
import ca.utoronto.utm.paint.serialization.strategies.ColorSerializationStrategy;
import ca.utoronto.utm.paint.serialization.strategies.IntegerSerializationStrategy;
import ca.utoronto.utm.paint.serialization.strategies.PointSerializationStrategy;

public class Circle extends Shape {
	private Point centre;
	private int radius;
	
	public Circle(){
		this(new Point(0,0), 0);
	}
	public Circle(Point centre, int radius){
		this.centre = centre;
		this.radius = radius;
	}

	/**
	 * Creates a new circle from a stored serial. This constructor will parse through the serial ensuring
	 * that all values are accepted and will throw their relative exceptions with any issues. These exceptions
	 * will then be returned to the parser as an error.
	 * @param serial The serial to read from for the new shape
	 * @throws DeserializationMissingAttributeException
	 * @throws DeserializationOutOfRangeException
	 */
	public Circle(Serial serial) throws DeserializationMissingAttributeException,
										DeserializationOutOfRangeException {
		if (serial.hasValue("center")) {
			centre = serial.getValue("center", new PointSerializationStrategy());
			if (centre.getX() < 0 || centre.getY() < 0) throw new DeserializationOutOfRangeException();
		}
		else throw new DeserializationMissingAttributeException();

		if (serial.hasValue("radius")) {
			radius = serial.getValue("radius", new IntegerSerializationStrategy());
			if (radius < 0) throw new DeserializationOutOfRangeException();
		}
		else throw new DeserializationMissingAttributeException();

		if (serial.hasValue("color")) this.setColor(serial.getValue("color", new ColorSerializationStrategy()));
		else throw new DeserializationMissingAttributeException();

		if (serial.hasValue("filled")) this.setFill(serial.getValue("filled", new BooleanSerializationStrategy()));
		else throw new DeserializationMissingAttributeException();

	}

	public Point getCentre() { return centre; }
	public void setCentre(Point centre) { this.centre = centre; }
	public int getRadius() { return radius; }
	public void setRadius(int radius) { this.radius = radius; }
	
}