package ca.utoronto.utm.paint;

import javax.swing.JFrame;

/**
 * The fundamental class to the program. Links all required classes together to create
 * a functioning program.
 */
public class Paint {
	/**
	 * The first method run. Creates a new instance of the Paint class to start
	 * the program
	 * @param args Not used for this application.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(Paint::new);
	}

	PaintModel model; // Model
	View view; // View+Controller

	public Paint() {
		// Create MVC components and hook them together

		// Model
		this.model = new PaintModel();

		// View+Controller
		this.view = new View(model);
		
	}
}
