package ca.utoronto.utm.paint.shapes;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tracks multiple shapes and displays using the order they were added in.
 *
 * @author Maxim
 * @version 1.0
 * @since 08-11-2016
 */
public abstract class CompositePaintShape<T extends PaintShape> extends PaintShape {
	protected List<ShapeOrder> parts;   // All shapes contained within & representing the composite shape.

	/**
	 * Create a new CompositePaintShape with a certain offset.
	 * @param origin Offset to all contained shapes.
	 */
	public CompositePaintShape(Point origin) {
		super(origin, null, null);
		parts = new ArrayList<>();
	}

	/**
	 * Removes all instances of a shape from the composite.
	 * @param shape Shape to remove.
	 */
	public void removePart(PaintShape shape) {
		Iterator<ShapeOrder> iterator = parts.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().shape == shape)
				iterator.remove();
		}
	}

	/**
	 * Adds a shape to the composite.
	 * @param shape Shape to add.
	 * @param zOrder A higher order will place the shape above others.
	 */
	public void addPart(T shape, int zOrder) {
		parts.add(new ShapeOrder(shape, zOrder));
	}

	/**
	 * Returns a non-mutable collection of all parts contained in this composite shape.
	 * @return Unmodifiable collection of composing shapes.
	 */
	public List<T> getParts() {
		// Unmodifiable as the Stream no longer references the original List anyways.
		return Collections.unmodifiableList(parts.stream()
				// Outside world doesn't need to know ShapeOrder exists.
				.map(ShapeOrder::getShape)
				.collect(Collectors.toList()));
	}

	/**
	 * Draws all of the contained Shapes in their respective zOrder.
	 * @param graphics API to use and to which this shape will be drawn.
	 */
	@Override
	public final void draw(Graphics2D graphics) {
		// Draw each part separately.
		parts.stream()
				/* Sort the elements during retrieval.
				 *
				 * We don't want to sort them when they
				 * are added because for certain Composites
				 * it may be important to keep track of
				 * the order Shapes were added as well.
				 */
				.sorted((p1, p2) -> p1.zOrder - p2.zOrder)
				.forEach(part -> part.shape.draw(graphics));
	}

	/**
	 * Moves all contained PaintShapes by the specified delta.
	 *
	 * @param deltaX Added to the X position.
	 * @param deltaY Added to the Y position.
	 */
	@Override
	public final void move(int deltaX, int deltaY) {
		parts.forEach(part -> part.shape.move(deltaX, deltaY));
	}

	/**
	 * Tracks the zOrder of each shape, this allows the Composite to keep track
	 * of the order in which it should be drawing each shape.
	 */
	private final class ShapeOrder implements Cloneable {
		T shape;    // Shape in this pair.
		int zOrder; // Order of the shape.

		private ShapeOrder(T shape, int zOrder) {
			this.shape = shape;
			this.zOrder = zOrder;
		}

		public T getShape() {
			return shape;
		}

		/**
		 * Returns a deep-cloned ShapeOrder.
		 * @return ShapeOrder with no relation to the current instance.
		 */
		@SuppressWarnings(value = "unchecked")
		@Override
		protected ShapeOrder clone() {
			try {
				ShapeOrder order = (ShapeOrder) super.clone();
				order.shape = (T) shape.clone();
				return order;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Returns a deep-cloned shape, the CompositePaintShape will in turn
	 * clone all of the shapes it contains.
	 *
	 * @return CompositePaintShape with no relation to the current instance.
	 */
	@SuppressWarnings(value = "unchecked")
	@Override
	public CompositePaintShape<T> clone() {
		CompositePaintShape<T> shape = (CompositePaintShape<T>) super.clone();
		shape.parts = parts.stream().map(ShapeOrder::clone).collect(Collectors.toList());
		return shape;
	}
}
