package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.events.shapes.ShapeSelectedEvent;
import ca.utoronto.utm.paint.shapes.builders.ShapeFactory;
import ca.utoronto.utm.paint.shapes.*;
import ca.utoronto.utm.paint.shapes.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

/**
 * The panel which displays to the user the different shapes that can be created
 */
class ShapeChooserPanel extends JPanel implements ActionListener {
	private View view; // So we can talk to our parent or other components of the view
	private JLabel selectedMode;

	/**
	 * Creates a new shape choose panel for the user to select different shapes with
	 *
	 * @param view The view which requested the panel
	 */
	public ShapeChooserPanel(View view) {
		this.view = view;
		
		String[] buttonLabels = {"Circle", "Rectangle", "Square", "Triangle", "Squiggle", "Line", "Dotted Line", "Eraser"};
		this.setLayout(new GridLayout((int) Math.ceil(buttonLabels.length + 1), 1));
		this.setPreferredSize(new Dimension(100, 0));

		for (String label : buttonLabels) {
			ImageIcon icon = new ImageIcon(getClass().getResource("img/" + label + ".png"));
			JButton button = new JButton(icon);

			button.setPreferredSize(new Dimension(50, 50));

			icon.setImage(icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

			button.setName(label);
			this.add(button);
			button.addActionListener(this);
		}
		this.selectedMode = new JLabel();
		this.selectedMode.setHorizontalAlignment(JLabel.CENTER);
		this.add(selectedMode, BorderLayout.SOUTH);
	}
	
	/**
	 * Controller aspect of this
	 * Handles the action for choosing a button on the shape chooser
	 *
	 * @param e The event which triggered the function
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		String selection = source.getName();
		PaintOptions options = view.getOptions();

		// Ignore coords, this Shape simply represents the selection and is not visible.
		ShapeFactory factory = ShapeFactory.build(Point.ZERO).withOptions(options);
		PaintShape shape = null;

		switch (selection) {
			case "Circle":
				shape = factory.asFillable(options).toCircle(0);
				break;
			case "Rectangle":
				shape = factory.asFillable(options).toRectangle(0, 0);
				break;
			case "Square":
				shape = factory.asFillable(options).toSquare(0);
				break;
			case "Squiggle":
				shape = factory.toSquiggle();
				break;
			case "Line":
				shape = factory.toLine(Point.ZERO);
				break;
			case "Eraser":
				shape = factory
						.setColor(view.getPaintPanel().getBackground())
						.setStroke(options.getStrokeWidth() * 4)        // Eraser width is a multiple of 4 of stroke (for convenience).
						.toEraser();
				break;
			case "Dotted Line":
				shape = factory.setStroke(new BasicStroke(options.getStrokeWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1f, new float[] {10f}, 5f)).toLine(Point.ZERO);
				break;
			case "Triangle":
				shape = factory.asFillable(options).toTriangle(0,0);
				break;
		}

		if (shape != null)
			view.getEventDispatcher().dispatchEvent(new ShapeSelectedEvent(shape, options));
		this.selectedMode.setText(String.format("<html>%s<br>Tool</html>", selection));
	}
}
