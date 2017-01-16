package ca.utoronto.utm.paint.serialization.exceptions;

/**
 * Represents the exception thrown when a serial is missing a corresponding strategy
 *
 * @version 1.0
 * @author Anthony
 * @since 04-12-2016
 */
public class DeserializationStrategyMissingException extends Exception {
    public DeserializationStrategyMissingException() { super(); }
}
