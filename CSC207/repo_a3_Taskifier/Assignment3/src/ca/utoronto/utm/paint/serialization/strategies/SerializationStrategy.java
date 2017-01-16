package ca.utoronto.utm.paint.serialization.strategies;

/**
 * The strategy to determine how to serialize different objects
 * @param <T> The type of object to serialize
 */
public abstract class SerializationStrategy<T> {
	private Class<T> clazz;

	/**
	 * Create a serialization strategy for the given object
	 * @param clazz
	 */
	SerializationStrategy(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Convert from a given string to the generic object assigned to the strategy
	 * @param val The string representation of an object
	 * @return The object constructed from the string
	 */
	public abstract T fromString(String val);

	/**
	 * Convert from a generic object to its string serialization
	 * @param val The object to serialize
	 * @return The string representation of the object
	 */
	public abstract String toString(T val);

	/**
	 * Gets the specific class which is used for the serialization object
	 * @return The object which is being serialized or deserialized
	 */
	public Class<T> getSerializedClass() {
		return clazz;
	}
}
