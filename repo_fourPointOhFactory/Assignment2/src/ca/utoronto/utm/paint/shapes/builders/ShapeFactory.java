package ca.utoronto.utm.paint.shapes.builders;

import ca.utoronto.utm.paint.PaintOptions;
import ca.utoronto.utm.paint.shapes.*;
import ca.utoronto.utm.paint.shapes.Point;
import ca.utoronto.utm.paint.shapes.Rectangle;

import java.awt.*;

/**
 * ShapeFactory provides convenience in the form of code-readability
 * as well as some simple defaults and streamlines the Shape creation
 * process.
 *
 * By default Shapes are drawn with solid 1px sized strokes
 * with a black border and light-gray fill.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public class ShapeFactory {
	// The default line stroke is applied for all Shapes until otherwise specified.
	final private static Stroke defaultStroke = new BasicStroke();

	private Point origin;
	private Stroke stroke;
	private Color colorMain;

	private ShapeFactory(Point origin) {
		this.origin = origin;
		this.stroke = defaultStroke;
		this.colorMain = Color.black;
	}

	/**
	 * Begins a new ShapeFactory with the desired position.
	 * @param atPoint Resultant Shape will assume this position.
	 * @return Returns the factory.
	 */
	public static ShapeFactory build(Point atPoint) {
		return new ShapeFactory(atPoint);
	}

	/**
	 * Begins a new ShapeFactory with the desired position.
	 * @param x x position to use.
	 * @param y y position to use.
	 * @return Returns the factory.
	 */
	public static ShapeFactory build(int x, int y) {
		return build(new Point(x, y));
	}

	/**
	 * Begins a new ShapeFactory with a zero'd origin. (x, y) as (0, 0)
	 * @return Returns the factory.
	 */
	public static ShapeFactory build() {
		return build(Point.ZERO);
	}

	/**
	 * Applies all applicable values from the provided Options to the factory.
	 * @param options Values to apply.
	 * @return Returns the factory.
	 */
	public ShapeFactory withOptions(PaintOptions options) {
		return setStroke(options.getStrokeWidth()).setColor(options.getColorStroke());
	}

	/**
	 * Set the outline of the Shape.
	 * @param stroke Used to draw any 'lines' in the resultant shape.
	 * @return Returns the factory.
	 */
	public ShapeFactory setStroke(Stroke stroke) {
		this.stroke = stroke;
		return this;
	}

	/**
	 * Set a basic solid stroke with the specified width.
	 * @param width Value no less than 0.
	 * @return Returns the factory.
	 */
	public ShapeFactory setStroke(float width) {
		return setStroke(new BasicStroke(width));
	}

	/**
	 * Set the color of any lines within the Shape.
	 * @param color Color to draw.
	 * @return Returns the factory.
	 */
	public ShapeFactory setColor(Color color) {
		this.colorMain = color;
		return this;
	}

	/**
	 * Returns the more specific Fillable factory used
	 * to create Shapes with a distinct 'inside' that
	 * may be filled with another color.
	 *
	 * @param withFill Color to fill shape with.
	 * @return Returns the factory.
	 */
	public ShapeFactory.Fillable asFillable(Color withFill) {
		return new Fillable(withFill);
	}

	/**
	 * Returns the more specific Fillable factory.
	 *
	 * @param withOptions Applies the fill color found in the options.
	 * @return Returns the factory.
	 */
	public ShapeFactory.Fillable asFillable(PaintOptions withOptions) {
		return asFillable(withOptions.getColorFill());
	}

	/**
	 * Returns the more specific Fillable factory, provides
	 * light-gray fill by default.
	 * @return Returns the factory.
	 */
	public ShapeFactory.Fillable asFillable() {
		return asFillable(Color.lightGray);
	}

	/**
	 * Builds a line.
	 * @param toPoint Point to draw line to.
	 * @return Returns a Line with factory values.
	 */
	public Line toLine(Point toPoint) {
		return new Line(origin, toPoint, colorMain, stroke);
	}

	/**
	 * Builds a Squiggle.
	 * @param withPoints Points to connect.
	 * @return Returns a Squiggle with factory values.
	 */
	public Squiggle toSquiggle(Point...withPoints) {
		Squiggle squiggle = new Squiggle(origin, stroke, colorMain);
		for (Point point : withPoints)
			squiggle.addPoint(point);
		return squiggle;
	}

	/**
	 * Builds a Eraser.
	 * @param withPoints Points to connect.
	 * @return Returns a Eraser with factory values.
	 */
	public Eraser toEraser(Point...withPoints) {
		Eraser eraser = new Eraser(origin, stroke, colorMain);
		for (Point point : withPoints)
			eraser.addPoint(point);
		return eraser;
	}


	/**
	 * ShapeFactory.Fillable provides the methods necessary to construct
	 * shapes with properties that allow them to be filled between outlines.
	 */
	public class Fillable {
		Color colorSecondary;

		private Fillable(Color withFill) {
			this.colorSecondary = withFill;
		}

		/**
		 * Builds a Circle.
		 * @param withRadius Radius of the circle.
		 * @return Returns a Circle with factory values.
		 */
		public Circle toCircle(int withRadius) {
			return new Circle(origin, colorMain, stroke, colorSecondary, withRadius);
		}

		/**
		 * Builds a Square.
		 * @param withLength Length of any given side of the Square.
		 * @return Returns a Square with factory values.
		 */
		public Square toSquare(int withLength) {
			return new Square(origin, colorMain, stroke, colorSecondary, withLength);
		}

		/**
		 * Builds a Rectangle.
		 * @param withLength Length of the shape on the y-axis.
		 * @param withWidth Length of the shape on the x-axis.
		 * @return Returns a Rectangle with factory values.
		 */
		public Rectangle toRectangle(int withLength, int withWidth) {
			return new Rectangle(origin, colorMain, stroke, colorSecondary, withLength, withWidth);
		}
		
		public Triangle toTriangle(int base, int height){
			return new Triangle(origin, colorMain, stroke, colorSecondary, base, height);
		}

		
	}
}
