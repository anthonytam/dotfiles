package ca.utoronto.utm.paint.serialization.strategies;

/**
 * The strategy to convert boolean values to a given text representation. Also converts a
 * text representation to a boolean value.
 */
public class BooleanSerializationStrategy extends SerializationStrategy<Boolean> {
	/**
	 * Create a new BooleanSerializationStrategy to convert boolean values
	 */
	public BooleanSerializationStrategy() {
		super(Boolean.class);
	}

	/**
	 * Convert a boolean value to a string
	 * @param val Boolean to be converted
	 * @return The boolean converted to a string
	 */
	@Override
	public String toString(Boolean val) {
		return val.toString();
	}

	/**
	 * Convert a string to a boolean
	 * @param val the string to convert to a boolean
	 * @return The string converted to a boolean
	 */
	@Override
	public Boolean fromString(String val) {
		return Boolean.valueOf(val);
	}
}
