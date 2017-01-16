package ca.utoronto.utm.paint.serialization.strategies;

import java.awt.*;

/**
 * The strategy to convert color values to a given text representation. Also converts a
 * text representation to a color value.
 */
public class ColorSerializationStrategy extends SerializationStrategy<Color> {
	/**
	 * Create a new instance of the strategy
	 */
	public ColorSerializationStrategy() {
		super(Color.class);
	}

	/**
	 * Coverts a string value to its respective colour value.
	 * @param val the string to be converted
	 * @return the color object
	 */
	@Override
	public Color fromString(String val) {
		String[] vals = val.split(",");
		return new Color(Integer.parseInt(vals[0]),
						Integer.parseInt(vals[1]),
						Integer.parseInt(vals[2]));
	}

	/**
	 * Convers a color to a string representation
	 * @param val a color to be converted to string
	 * @return a string representation of the color
	 */
	@Override
	public String toString(Color val) {
		return String.format("%d,%d,%d", val.getRed(), val.getGreen(), val.getBlue());
	}
}
