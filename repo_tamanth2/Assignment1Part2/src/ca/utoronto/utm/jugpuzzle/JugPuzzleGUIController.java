package ca.utoronto.utm.jugpuzzle;

import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 * This class allows a user to play an instance of JugPuzzle with a GUI. It
 * repeatedly gives the user buttons to click to cause spills between the jugs.
 * A status bar on the top reminds the user what their current action is as well
 * as the number of moves the user has taken. Becide this bar is an undo button
 * allowing the user to undo a selection they may make. Buttons at the bottom give
 * the user the option to forfeit their game or exit the game.
 * 
 * @author csc207 student
 */
public class JugPuzzleGUIController {

	/**
	 * Create an initial instance of the controller to start the application
	 * @param args Not used for this program
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/**
	 * Create the controller for the game. First the model (JugPuzzle) is created along with
	 * the GUI elements. The 2 are then connected together using observables to monitor changes. 
	 * The GUI elements are then places onto a frame and displyed to the user.
	 */
	public static void createAndShowGUI() {
		//Start the Jug Puzzle
		JugPuzzle JP = new JugPuzzle();
		
		// Create GUI Elements
		JugPuzzleStatusBar JPStatus = new JugPuzzleStatusBar(JP);
		JugPuzzleJugLabels JPLabels = new JugPuzzleJugLabels(JP);
		JugPuzzleSpillButtons JPButtons = new JugPuzzleSpillButtons(JP);
		JugPuzzleOptionButtons JPOButtons = new JugPuzzleOptionButtons(JP);

		// Hook the model to the view.
		JP.addObserver(JPStatus);
		JP.addObserver(JPOButtons);
		for(int i = 0; i < 3; i++)
			JP.addJugObserver(i, JPLabels);
				
		// Create the JFrame for the GUI
		JFrame frame = new JFrame("Jug Puzzle"); // Frame with title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Apply a Grid Layout to the GUI
		frame.getContentPane().setLayout(new GridLayout(4, 1));
		
		// Add GUI elements to the JFrame
		frame.getContentPane().add(JPStatus);
		frame.getContentPane().add(JPLabels);
		frame.getContentPane().add(JPButtons);
		frame.getContentPane().add(JPOButtons);
		frame.pack();
		frame.setVisible(true);
	}
	
}
