package ca.utoronto.utm.paint.serialization;

import ca.utoronto.utm.paint.Point;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationStrategyMissingException;
import ca.utoronto.utm.paint.serialization.strategies.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
/**
 * Represents the data relevant to constructing a specific object.
 * Serial doubles as a saving & loading mechanism unifying different
 * objects under a similar save-format.
 *
 * @version 1.0
 * @author Maxim
 * @since 28-11-2016
 */
public class Serial {

	/// Collection of default serialization strategies recognized by the Serial.
	public final static SerializationStrategy<?>[] DEFAULT_STRATEGIES = new SerializationStrategy[]{
			new PointSerializationStrategy(),
			new IntegerSerializationStrategy(),
			new BooleanSerializationStrategy(),
			new ColorSerializationStrategy()
	};

	private String signature;                   // Name of the serial. i.e. Circle
	private List<Pair<String, Object>> data;    // All data held in Serial.
	private String raw;                         // Any raw data received from a stream.

	/**
	 * Create a new empty Serial with a signature.
	 * This can then be populated with data and exported.
	 * @param signature Signature of this data clump.
	 */
	public Serial(String signature) {
		this.signature = signature;
		data = new ArrayList<>();
		raw = null;
	}

	/**
	 * Create a new Serial with values extracted from a StringBuilder.
	 * @param dataBlock Block of data consisting of multiple lines representing the Serial.
	 */
	public Serial(StringBuilder dataBlock) {
		this.signature = dataBlock.substring(0, dataBlock.indexOf("\n"));
		data = new ArrayList<>();
		raw = dataBlock.toString();
	}

	/**
	 * Returns if there is a value pertaining to the specified key.
	 * @param key Key of the value.
	 * @return Returns true if there is at least one instance of this key in the Serial.
	 */
	public boolean hasValue(String key) {
		if (hasDataValue(key))
			return true;
		String[] allLines = raw.split("\\n");
		for (String line : allLines) {
			if (line.contains(":") && line.substring(0, line.indexOf(':')).equals(key))
				return true;
			line = line.replaceAll("\\s*", "");
		}
		return false;
	}

	private boolean hasDataValue(String key) {
		return data.stream()
				.filter(pair -> pair.getLeft().equals(key))
				.findAny().isPresent();
	}

	/**
	 * Returns the first occurring value with the specified key.
	 * @param key Key associated to the designated value.
	 * @param strategy Strategy to use to retrieve the value.
	 * @return May throw an error if the SerializationStrategy fails, otherwise retrieves value.
	 */
	public <T> T getValue(String key, SerializationStrategy<T> strategy) {
		if (hasDataValue(key))
			return (T) data.stream().filter(pair -> pair.getLeft().equals(key))
				.findFirst().get();
		List<T> values = getAllValues(key, strategy);
		return values.isEmpty() ? null : values.get(0);
	}

	/**
	 * Returns all values associated with the specified key.
	 * @param key Key associated to the requested values.
	 * @param strategy Strategy to use to retrieve the values.
	 * @return May throw an error if the SerializationStrategy fails for any of the values, otherwise retrieves all.
	 */
	public <T> List<T> getAllValues(String key, SerializationStrategy<T> strategy) {
		if (hasDataValue(key))
			return (List<T>) data.stream().filter(pair -> pair.getLeft().equals(key))
					.map(Pair::getRight)
					.collect(Collectors.toList());
		List<T> values = new ArrayList<>();
		String[] allLines = raw.split("\\n");

		for (String line : allLines) {
			line = line.replaceAll("\\s*", "");
			if (line.contains(":") && line.substring(0, line.indexOf(':')).equals(key))
				values.add(strategy.fromString(line.substring(line.indexOf(':') + 1, line.length())));

		}
		return values;
	}

	/**
	 * Adds a new value to the specified key for the Serial.
	 * @param key Key used to retrieve the value.
	 * @param value Value associated with the key.
	 */
	public void addValue(String key, Object value) {
		data.add(new Pair<>(key, value));
	}

	/**
	 * Nests a Serial within.
	 * @param nestedSerial Serial to store.
	 */
	public void addValue(Serial nestedSerial) {
		data.add(new Pair<>(nestedSerial.signature, nestedSerial));
	}

	/**
	 * Writes out the contents of the Serial in a format that it can reconstruct
	 * a Serial object from at a later date. The String returned will contain
	 * the necessary newlines with added tabbing for increased readability.
	 *
	 * @return Full string
	 * @throws DeserializationStrategyMissingException If during writing there is no strategy available for a class
	 * contained within the data of the Serial, the write will fail.
	 */
	public String write() throws DeserializationStrategyMissingException {
		return write(DEFAULT_STRATEGIES);
	}

	/**
	 * Writes out the contents of the Serial in a format that it can reconstruct
	 * a Serial object from at a later date. The String returned will contain
	 * the necessary newlines with added tabbing for increased readability.
	 *
	 * @param strategies Strategies used in writing out relevant classes in this Serial.
	 * @return Full string
	 * @throws DeserializationStrategyMissingException If during writing there is no strategy available for a class
	 * contained within the data of the Serial, the write will fail.
	 */
	public String write(SerializationStrategy<?>...strategies) throws DeserializationStrategyMissingException {
		return write(0, strategies);
	}

	/**
	 * Writes out the contents of the Serial in a format that it can reconstruct
	 * a Serial object from at a later date. The String returned will contain
	 * the necessary newlines with added tabbing for increased readability.
	 *
	 * @param indentDepth Depth of indentation, 0 indicates a top-level call on this method.
	 * @param strategies Strategies used in writing out relevant classes in this Serial.
	 * @return Full string
	 * @throws DeserializationStrategyMissingException If during writing there is no strategy available for a class
	 * contained within the data of the Serial, the write will fail.
	 */
	public String write(int indentDepth, SerializationStrategy<?>...strategies) throws DeserializationStrategyMissingException {
		class SerialBuilder {
			StringBuilder content = new StringBuilder(signature);
			int indent = indentDepth;

			SerialBuilder() throws DeserializationStrategyMissingException {
				indent++;
				for (int index = 0; index < data.size(); index++) {
					Object obj = data.get(index).getRight();
					if (obj instanceof Serial)
						write(((Serial) obj).write(indent, strategies));
					else
						write(data.get(index).getLeft() + ":" + getStrategy(obj).toString(obj));
				}
				indent--;

				// Odd format behaviour demands nested writes with lowercase end.
				// Here we compensate for this factor.
				write((indentDepth == 0 ? "End " : "end ") + signature);
				if (indentDepth == 0)
					content.append("\n");
			}

			SerializationStrategy getStrategy(Object obj) throws DeserializationStrategyMissingException {
				for (SerializationStrategy strategy : strategies)
					if (strategy.getSerializedClass().equals(obj.getClass()))
						return strategy;
				throw new DeserializationStrategyMissingException();
			}

			void write(String toWrite) {
				StringBuilder indentBuilder = new StringBuilder();
				for (int i = 0; i < indent; i++)
					indentBuilder.append("\t");
				String ind = indentBuilder.toString();

				if (content.length() > 0)
					content.append("\n");
				content.append(ind);
				content.append(toWrite);
			}

			@Override
			public String toString() {
				return content.toString();
			}
		}
		return new SerialBuilder().toString();
	}

	/**
	 * Returns the signature of the Serial.
	 * @return Always non-null value.
	 */
	public String getSignature() {
		return signature;
	}

	private class Pair<L, R> {
		L left;
		R right;

		Pair(L left, R right) {
			this.left = left;
			this.right = right;
		}

		public L getLeft() {
			return left;
		}

		public R getRight() {
			return right;
		}

		public void setLeft(L left) {
			this.left = left;
		}

		public void setRight(R right) {
			this.right = right;
		}
	}
}
