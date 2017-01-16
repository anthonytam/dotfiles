package ca.utoronto.utm.paint.serialization.strategies;

/**
 * The strategy to convert Integer values to a given text representation. Also converts a
 * text representation to an Integer value.
 */
public class IntegerSerializationStrategy extends SerializationStrategy<Integer> {
	/**
	 * Create a new instance of the strategy
	 */
	public IntegerSerializationStrategy() {
		super(Integer.class);
	}

	/**
	 * Convert string value to an Integer
	 * @param val The string to convert
	 * @return The Integer representation
	 */
	@Override
	public Integer fromString(String val) {
		return Integer.valueOf(val);
	}

	/**
	 * Convert an Integer to a String
	 * @param val the integer to convert
	 * @return The string representation
	 */
	@Override
	public String toString(Integer val) {
		return val.toString();
	}
}
