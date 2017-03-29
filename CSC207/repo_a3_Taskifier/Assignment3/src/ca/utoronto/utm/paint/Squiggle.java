package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.serialization.Serial;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationInvalidValueException;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationMissingAttributeException;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationOutOfRangeException;
import ca.utoronto.utm.paint.serialization.strategies.BooleanSerializationStrategy;
import ca.utoronto.utm.paint.serialization.strategies.ColorSerializationStrategy;
import ca.utoronto.utm.paint.serialization.strategies.PointSerializationStrategy;

import java.util.ArrayList;
import java.util.List;

public class Squiggle extends Shape {
	private List<Point> points=new ArrayList<>();
	
	public Squiggle() {}

	/**
	 * Creates a new Squiggle from a stored serial. This constructor will parse through the serial ensuring
	 * that all values are accepted and will throw their relative exceptions with any issues. These exceptions
	 * will then be returned to the parser as an error.
	 * @param serial The serial to read from for the new shape
	 * @throws DeserializationMissingAttributeException
	 * @throws DeserializationInvalidValueException
	 * @throws DeserializationOutOfRangeException
	 */
	public Squiggle(Serial serial) throws DeserializationMissingAttributeException,
										  DeserializationInvalidValueException,
										  DeserializationOutOfRangeException {
		if (serial.hasValue("color")) this.setColor(serial.getValue("color", new ColorSerializationStrategy()));
		else throw new DeserializationMissingAttributeException();

		if (serial.hasValue("filled")) {
			this.setFill(serial.getValue("filled", new BooleanSerializationStrategy()));
		}
		else throw new DeserializationMissingAttributeException();


		if (serial.hasValue("point")) {
			points = serial.getAllValues("point", new PointSerializationStrategy());
		} else throw new DeserializationMissingAttributeException();
		for (Point p : points)
			if (p.getX() < 0 || p.getY() < 0) throw new DeserializationOutOfRangeException();
	}
	public void add(Point p){ this.points.add(p); }
	public List<Point> getPoints(){ return this.points; }
}