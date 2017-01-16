package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.serialization.Serial;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationInvalidValueException;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationMissingAttributeException;
import ca.utoronto.utm.paint.serialization.exceptions.DeserializationOutOfRangeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author 
 *
 */
public class PaintSaveFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage = ""; // error encountered during parse
	private ArrayList<PaintCommand> commands; // created as a result of the parse
	
	/**
	 * Below are Patterns used in parsing
	 */
	private Pattern pFileStart = Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd = Pattern.compile("^EndPaintSaveFile$");
	
	/**
	 * Store an appropriate error message in this, including
	 * lineNumber where the error occurred.
	 *
	 * @param mesg
	 */
	private void error(String mesg) {
		this.errorMessage = "Error in line " + lineNumber + " " + mesg;
	}

	/**
	 * @return the PaintCommands resulting from the parse
	 */
	public ArrayList<PaintCommand> getCommands() {
		return this.commands;
	}

	/**
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 *
	 * @param inputStream the open file to parse
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream) {
		this.commands = new ArrayList<>();
		this.errorMessage = "";
		
		// During the parse, we will be building one of the 
		// following shapes. As we parse the file, we modify 
		// the appropriate shape.

		try {
			int state = 0;
			Matcher matcher;
			String line;

			ParsableConstructs matchedConstruct = null;
			StringBuilder serialBuilder = null;
			Validator validator = null;

			this.lineNumber = 0;
			while ((line = inputStream.readLine()) != null) {
				line = line.replaceAll("\\s*", "");
				this.lineNumber++;
				if (line.isEmpty())
					continue;
				System.out.println(lineNumber + " " + line + " " + state);

				logic: switch (state) {
					case 0:
						matcher = pFileStart.matcher(line);
						if (matcher.matches()) {
							state = 1;
							break;
						}
						error("Expected Start of Paint Save File");
						return false;
					case 1: // Looking for the start of a new object or end of the save file
						for (ParsableConstructs construct : ParsableConstructs.values()) {
							matcher = construct.getStart().matcher(line);
							if (matcher.matches()) {
								serialBuilder = new StringBuilder(line);
								validator = construct.getValidator();
								matchedConstruct = construct;

								state = 2;
								break logic;
							}
						}
						matcher = pFileEnd.matcher(line);
						if (matcher.matches())
							state = 3;
						break;
					case 2:
						if (validator != null && !validator.validateNext(line)) {
							error("Mismatched attribute construction.");
							return false;
						}
						serialBuilder.append('\n').append(line);
						matcher = matchedConstruct.getEnd().matcher(line);
						if (matcher.matches()) {
							this.commands.add(matchedConstruct.shapeFromSerial(new Serial(serialBuilder)));
							state = 1;
						}
						break;
					case 3:
						error("End marker violated, expected end of file.");
						return false;
				}
			}
			if (state != 3) {
				error("Abrupt end of file detected, no end marker found.");
				return false;
			}
		} catch (DeserializationInvalidValueException exc) {
			error("Invalid value encountered while deserializing.");
			return false;
		} catch (DeserializationOutOfRangeException exc) {
			error("Value out-of-range.");
			return false;
		} catch (DeserializationMissingAttributeException exc) {
			error("Shape serial missing a required attribute.");
			return false;
		} catch (IOException exc) {
			error("Failed to read file.");
			return false;
		}
		return true;
	}

	private enum ParsableConstructs {
		CIRCLE("^Circle$", "^EndCircle$"),
		RECTANGLE("^Rectangle$", "^EndRectangle$"),
		SQUIGGLE("^Squiggle$", "^EndSquiggle$");

		Pattern start;
		Pattern end;

		ParsableConstructs(String start, String end) {
			this.start = Pattern.compile(start);
			this.end = Pattern.compile(end);
		}

		Pattern getStart() {
			return start;
		}

		Pattern getEnd() {
			return end;
		}

		PaintCommand shapeFromSerial(Serial savedData) throws
				DeserializationOutOfRangeException,
				DeserializationMissingAttributeException,
				DeserializationInvalidValueException {
			switch (this) {
				case CIRCLE:
					return new CircleCommand(new Circle(savedData));
				case RECTANGLE:
					return new RectangleCommand(new Rectangle(savedData));
				case SQUIGGLE:
					return new SquiggleCommand(new Squiggle(savedData));
			}
			return null;
		}

		Validator getValidator() {
			switch(this) {
				case CIRCLE:
					return new CircleValidator();
				case RECTANGLE:
					return new RectangleValidator();
				case SQUIGGLE:
					return new SquiggleValidator();
			}
			return null;
		}
	}

	/**
	 * Validates data is uncompromised and follows format.
	 */
	private static abstract class Validator {
		Deque<BooleanSupplier> expectedOrder;
		String line;

		Validator() {
			expectedOrder = new LinkedList<>();
		}

		boolean validateNext(String line) {
			this.line = line;
			return !expectedOrder.isEmpty() && expectedOrder.remove().getAsBoolean();
		}

		void add(BooleanSupplier expectation) {
			expectedOrder.add(expectation);
		}

		String getKey() {
			return line.split(":")[0];
		}

		String getValue() {
			return line.split(":")[1];
		}

		boolean verifyColor() {
			if (getKey().equals("color")) {
				String[] nums = getValue().split(",");
				for (String s : nums) {
					int val = Integer.parseInt(s);
					if (val < 0 || val > 255)
						return false;
				}
				return true;
			}
			return false;
		}

		boolean verifyFilled() {
			if (getKey().equals("filled")) {
				String val = getValue();
				if (val.equals("true") || val.equals("false"))
					return true;
			}
			return false;
		}

		boolean verifyPoint(String key) {
			if (getKey().equals(key)) {
				String val = getValue();
				return val.matches("\\(\\d+,\\d+\\)");
			}
			return false;
		}

		boolean verifyRadius() {
			if (getKey().equals("radius"))
				return getValue().matches("\\d+");
			return false;
		}

		boolean verifyEnding(String name) {
			return line.matches("End"+name);
		}
	}

	private static class CircleValidator extends Validator {
		CircleValidator() {
			add(this::verifyColor);
			add(this::verifyFilled);
			add(() -> verifyPoint("center"));
			add(this::verifyRadius);
			add(() -> verifyEnding("Circle"));
		}
	}

	static class RectangleValidator extends Validator {
		RectangleValidator() {
			add(this::verifyColor);
			add(this::verifyFilled);
			add(() -> verifyPoint("p1"));
			add(() -> verifyPoint("p2"));
			add(() -> verifyEnding("Rectangle"));
		}
	}

	private static class SquiggleValidator extends Validator {
		SquiggleValidator() {
			add(this::verifyColor);
			add(() -> getKey().equals("filled") && ("false".equals(getValue()) || "true".equals(getValue())));
			add(() -> line.equals("points"));
			add(() -> verifyPoint("point"));
			add(this::isLastLine);
			add(() -> verifyEnding("Squiggle"));
		}

		boolean isLastLine() {
			if (line.equals("endpoints"))
				return true;
			else if (verifyPoint("point")) {
				expectedOrder.addFirst(this::isLastLine);
				return true;
			}
			return false;
		}
	}
}
