package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.events.EventDispatcher;
import ca.utoronto.utm.paint.events.EventListener;
import ca.utoronto.utm.paint.events.shapes.ShapeEvent;
import ca.utoronto.utm.paint.shapes.PaintShape;

import java.util.*;

/**
 * The model of the program. An observable object which contains all the shapes currently drawn on the
 * screen. Additional shapes can be drawn onto the screen, all of the shapes can be returned if requested.
 */
public class PaintModel implements EventDispatcher {
	private Set<EventListener> listeners;
	private List<PaintShape> shapes, undoShapes;
	private PaintShape shapeSelection, shapeDrawing;
	private PaintOptions options;

	PaintModel() {
		shapes = new ArrayList<>();
		undoShapes = new ArrayList<>();
		listeners = new HashSet<>();
		options = new PaintOptions();
		shapeSelection = shapeDrawing = null;
	}

	@Override
	public void dispatchEvent(ShapeEvent event) {
		System.out.println(String.format("Dispatched event: `%s`", event.getClass().getSimpleName()));
		listeners.forEach(listener -> listener.onEventCalled(event));
	}

	@Override
	public void registerListener(EventListener listener) {
		listeners.add(listener);
	}

	@Override
	public void unregisterListener(EventListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Adds an additional point to the current model
	 * @param p A Point object to be added to the model
	 */
	public void addShape(PaintShape p){
		this.shapes.add(p);
	}

	/**
	 * Clears all of the shapes in the current workspace
	 */
	public void clearAll() {
		this.shapes.clear();
	}

	/**
	 * Undo the last shapes that was added to the workspace
	 */
	public void undo() {
		if (shapes.size() != 0) {
			PaintShape removedShape = shapes.get(shapes.size() - 1);
			shapes.remove(shapes.size() - 1);
			undoShapes.add(removedShape);
		}
	}

	/**
	 * Re add the last undone shape to the workspace
	 */
	public void redo() {
		if (undoShapes.size() != 0) {
			PaintShape addBack = undoShapes.get(undoShapes.size() - 1);
			undoShapes.remove(undoShapes.size() - 1);
			shapes.add(addBack);
		}
	}

	/**
	 * Returns all shapes in the model.
	 * @return List of all Shapes currently in the model.
	 */
	public List<PaintShape> getShapes() {
		return shapes;
	}

	public PaintOptions getOptions() {
		return options;
	}

	public void setOptions(PaintOptions options) {
		this.options = options;
	}

	/**
	 * Returns the Shape the user is currently drawing, if such exists.
	 * @return Optional value, may contain shape.
	 */
	public Optional<PaintShape> getShapeDrawing() {
		return Optional.ofNullable(shapeDrawing);
	}

	/**
	 * Returns the Shape the user has selected to draw, if such exists.
	 * @return Optional value, may contain shape.
	 */
	public Optional<PaintShape> getShapeSelection() {
		return Optional.ofNullable(shapeSelection);
	}

	public void setShapeDrawing(PaintShape shapeDrawing) {
		this.shapeDrawing = shapeDrawing;
	}

	public void setShapeSelection(PaintShape shapeSelection) {
		this.shapeSelection = shapeSelection;
	}
}
