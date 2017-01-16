package ca.utoronto.utm.paint;

import ca.utoronto.utm.paint.events.EventDispatcher;
import ca.utoronto.utm.paint.events.shapes.ShapeBackgroundChosenEvent;
import ca.utoronto.utm.paint.events.shapes.ShapeColorSelectedEvent;
import ca.utoronto.utm.paint.shapes.Eraser;
import ca.utoronto.utm.paint.shapes.PaintShape;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Optional;

/**
 * This is the top level View+Controller, it contains other aspects of the View+Controller.
 * @author arnold
 *
 */
public class View extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private PaintModel model;
	
	// The components that make this up
	private PaintPanel paintPanel;
	private ShapeChooserPanel shapeChooserPanel;
	
	/**
	 * Creates a new view for the program. Brings all panels together to create
	 * a completed GUI
	 *
	 * @param model the model the view will correlate to
	 */
	public View(PaintModel model) {
		super("Paint"); // set the title and do other JFrame init
		this.model = model;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());

		Container c = this.getContentPane();
		// c.add(new JButton("North"),BorderLayout.NORTH);
		// c.add(new JButton("South"),BorderLayout.SOUTH);
		// c.add(new JButton("East"),BorderLayout.EAST);
		this.shapeChooserPanel = new ShapeChooserPanel(this);
		c.add(this.shapeChooserPanel, BorderLayout.WEST);
		
		this.paintPanel = new PaintPanel(model, this);
		c.add(this.paintPanel, BorderLayout.CENTER);
		
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		// Erasers should match any new background selections.
		getEventDispatcher().registerListener(event -> {
			if (event instanceof ShapeBackgroundChosenEvent) {
				ShapeBackgroundChosenEvent backEvent = (ShapeBackgroundChosenEvent) event;
				model.getShapes().forEach(shape -> {
					if (shape instanceof Eraser) {
						Eraser eraser = (Eraser) shape;
						// Change the color of all parts of the composite.
						eraser.getParts().forEach(line -> line.setColor(backEvent.getSelectedColor()));
						eraser.setColor(backEvent.getSelectedColor());
					}
				});
				Optional<PaintShape> selection = model.getShapeSelection();
				if (selection.isPresent() && selection.get() instanceof Eraser) {
					selection.get().setColor(backEvent.getSelectedColor());
				}
			}
		});

		// Add Key Bindings
		for (KeyBindingActionType type : KeyBindingActionType.values()) {
			paintPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(type.getHotkey(), InputEvent.CTRL_DOWN_MASK), type.name());
			paintPanel.getActionMap().put(type.name(), new KeyBindingAction(this, model, type));
		}
	}

	/**
	 * Gets the painting panels of the View
	 *
	 * @return The PaintPanel the user is drawing on
	 */
	public PaintPanel getPaintPanel() {
		return paintPanel;
	}

	/**
	 * Gets the shape chooser panel of the view
	 *
	 * @return The ShapeChooserPanel the user is interacting with
	 */
	public ShapeChooserPanel getShapeChooserPanel() {
		return shapeChooserPanel;
	}

	/**
	 * Create the menuBar for the application. Adds options to each menu
	 *
	 * @return The completed menu bar to be added to the frame
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;

		String[][] menuOptions = {
				{"File",        "New (Ctrl+N)", "Open", "Save", "", "Exit"},
				{"Edit",        "Cut", "Copy", "Paste", "", "Undo (Ctrl+Z)", "Redo (Ctrl+Y)"},
				{"Features",    "Fill Color", "Stroke Color", "Line Thickness", "Background Color"}
		};

		for (String[] options : menuOptions) {
			menu = new JMenu(options[0]);
			for (int i = 1; i < options.length; i++) {
				if (options[i].isEmpty()) {
					menu.addSeparator();
					continue;
				}
				menuItem = new JMenuItem(options[i]);
				menuItem.addActionListener(this);
				menu.add(menuItem);
			}
			menuBar.add(menu);
		}

		return menuBar;
	}

	/**
	 * Handle an event when the use selects an item in the menu bar
	 *
	 * @param e The event which triggered the method
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		PaintOptions options = model.getOptions();

		switch (cmd) {
			case "Exit":
				this.dispose();
				break;
			case "Fill Color":
			case "Stroke Color":
				Optional<PaintShape> selection = model.getShapeSelection();
				if (selection.isPresent()) {
					// Only if there is a selection present should we notify the program of an options change.
					ShapeColorSelectedEvent event = new ShapeColorSelectedEvent(
							selection.get(),
							options,
							JColorChooser.showDialog(new JColorChooser(), cmd, Color.blue),
							cmd.startsWith("Fill") ? ShapeColorSelectedEvent.Type.FILL : ShapeColorSelectedEvent.Type.OUTLINE);
					// Call the event to allow listeners to make changes.
					model.dispatchEvent(event);
				}
				break;
			case "Line Thickness":
				new StrokeChooserFrame(this);
				break;
			case "Background Color":
				Color color = JColorChooser.showDialog(new JColorChooser(), "Background Color", Color.white);
				Optional<PaintShape> selShape = model.getShapeSelection();
				if (selShape.isPresent()) {
					ShapeBackgroundChosenEvent event = new ShapeBackgroundChosenEvent(selShape.get(), color);
					model.dispatchEvent(event);
					getPaintPanel().setBackground(event.getSelectedColor());
				}
				break;
			case "New":
				this.model.clearAll();
				break;
			case "Undo":
				this.model.undo();
				break;
			case "Redo":
				this.model.redo();
				break;
		}
		paintPanel.repaint();
	}

	/**
	 * Passes down the current shape the user has selected.
	 *
	 * @return If no shape is selected, passes empty optional.
	 */
	public Optional<PaintShape> getSelectedShape() {
		return model.getShapeSelection();
	}

	/**
	 * Returns the EventDispatcher.
	 *
	 * @return Returns dispatcher responsible for coordinating the View actions.
	 */
	public EventDispatcher getEventDispatcher() {
		return model;
	}

	/**
	 * All options available to the user.
	 *
	 * @return PaintOptions used in the creation of new shapes.
	 */
	public PaintOptions getOptions() {
		return model.getOptions();
	}

}
