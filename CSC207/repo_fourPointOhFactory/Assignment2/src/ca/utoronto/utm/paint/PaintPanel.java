package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.events.shapes.ShapeColorSelectedEvent;
import ca.utoronto.utm.paint.events.shapes.ShapeLineWidthSelectedEvent;
import ca.utoronto.utm.paint.events.shapes.ShapeSelectedEvent;
import ca.utoronto.utm.paint.shapes.*;
import ca.utoronto.utm.paint.shapes.Point;
import ca.utoronto.utm.paint.shapes.builders.ShapeFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

/**
 * The panel which creates the drawable space in the GUI. This is the area the user
 * is able to draw different shapes and lines on.
 */
class PaintPanel extends JPanel implements MouseMotionListener, MouseListener {
	private PaintModel model;       // slight departure from MVC, because of the way painting works
	private View view;              // So we can talk to our parent or other components of the view
	private Point clickOrigin;      // Where was the first click before dragging?

	/**
	 * Create the panel and prepare for it to be added to a frame.
	 *
	 * @param model The model of the paint application
	 * @param view  The view which created this panel
	 */
	public PaintPanel(PaintModel model, View view) {
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(500, 500));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.clickOrigin = new Point(0, 0);
		
		this.model = model;
		
		this.view = view;

		// Draw shapes when they are selected.
		this.model.registerListener(event -> {
			if (event instanceof ShapeSelectedEvent)
				drawShape(event.getShape());
		});

		PaintOptions options = model.getOptions();

		// Respond to color selection.
		this.model.registerListener(event -> {
			if (event instanceof ShapeColorSelectedEvent) {
				ShapeColorSelectedEvent colorEvent = (ShapeColorSelectedEvent) event;
				if (colorEvent.getType() == ShapeColorSelectedEvent.Type.FILL) {
					options.setColorFill(colorEvent.getSelectedColor());

					// Reflect changes on the selected shape.
					if (colorEvent.getShape() instanceof FillablePaintShape) {
						FillablePaintShape shape = (FillablePaintShape) colorEvent.getShape();
						shape.setFillColor(options.getColorFill());
					}
				} else {
					options.setColorStroke(colorEvent.getSelectedColor());
					// Reflect changes on selected shape.
					event.getShape().setColor(options.getColorStroke());
				}
				this.repaint();
			}
		});

		// Respond to line width selection.
		this.model.registerListener(event -> {
			if (event instanceof ShapeLineWidthSelectedEvent) {
				ShapeLineWidthSelectedEvent widthEvent = (ShapeLineWidthSelectedEvent) event;
				options.setStrokeWidth(widthEvent.getSelectedWidth());

				// Reflect changes on selected shape.
				event.getShape().setStroke(new BasicStroke(options.getStrokeWidth()));
			}
		});

		// Repaint
		this.model.registerListener(event -> this.repaint());
	}

	/**
	 * View aspect of this
	 * Handles the painting of the requested component. Draws all of the
	 * shapes in the given model to this panel
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Use g to draw on the JPanel, lookup java.awt.Graphics in
		// the javadoc to see more of what this can do for you!!

		super.paintComponent(g); //paint background
		Graphics2D g2d = (Graphics2D) g; // lets use the advanced api

		// Origin is at the top left of the window 50 over, 75 down
		g2d.setColor(Color.black);

		this.model.getShapes().forEach(shape -> shape.draw(g2d));

		Optional<PaintShape> shapeInProgress = model.getShapeDrawing();
		if (shapeInProgress.isPresent())
			shapeInProgress.get().draw(g2d);
		
		g2d.dispose();
	}

	/**
	 * The controller aspect of this
	 *
	 * Marks which shape should be drawn. When given a paint shape, all attributes
	 * can be modified by the view regardless of what shape it is
	 * @param shapeToDraw The PaintShape object that will be drawn
	 */
	public void drawShape(PaintShape shapeToDraw) {
		model.setShapeDrawing(shapeToDraw);
		model.setShapeSelection(shapeToDraw);
	}

	/**
	 * Event triggered when the user moves the mouse over the panel
	 *
	 * @param e The mouse event that occurs
	 */
	@Override
	public void mouseMoved(MouseEvent e) {

	}

	/**
	 * Event triggered when the user clicks down a mouse key and moves
	 * across the panel
	 *
	 * @param e the mouse event that occurs
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		Optional<PaintShape> shapeInProgress = model.getShapeDrawing();
		if (shapeInProgress.isPresent()) {
			shapeInProgress.get().updateShapeFromDrag(clickOrigin, new Point(e.getX(), e.getY()));
			repaint();
		}
	}

	/**
	 * Event triggered when the user clickes in the panel
	 *
	 * @param e The mouse event that occurs
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * Event triggered when the user press and holds the mouse in
	 * the panel
	 *
	 * @param e The mouse event that occurs
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		Optional<PaintShape> shape = model.getShapeSelection();
		if (shape.isPresent()) {
			// Clone the selected Shape and set the click-origin, where is the 'start point'?
			PaintShape drawing = shape.get().clone();
			drawing.setOrigin(clickOrigin = new Point(e.getX(), e.getY()));
			model.setShapeDrawing(drawing);
		}
	}

	/**
	 * Event triggered when the user releases the mouse in the panel
	 *
	 * @param e The mouse event that occurs
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		Optional<PaintShape> shapeInProgress = model.getShapeDrawing();
		if (shapeInProgress.isPresent()) {
			model.addShape(shapeInProgress.get());
			model.setShapeDrawing(null);
		}
	}

	/**
	 * Event triggered when the user moves the mouse into the panel
	 *
	 * @param e The mouse event that occurs
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Event triggered when the user moves the mouse out of the panel
	 *
	 * @param e The mouse event that occurs
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if (model.getShapeDrawing().isPresent())
			mouseReleased(e);
	}
}
