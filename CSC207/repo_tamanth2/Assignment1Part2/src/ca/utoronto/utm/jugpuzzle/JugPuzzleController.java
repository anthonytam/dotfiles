package ca.utoronto.utm.jugpuzzle;

import java.util.*;

/**
 * This class allows a console user to play an instance of JugPuzzle. It
 * repeatedly asks the user for moves and then prints the status of the puzzle
 * until the user solves the JugPuzzle.
 * 
 * @author csc207 student
 */
public class JugPuzzleController {
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 0,1 or 2";

	private Scanner scanner; // for user console input
	private JugPuzzle jugPuzzle; // The puzzle the console user will be playing

	/**
	 * Constructs a new JugPuzzleController with a new JugPuzzle, ready to play
	 * with a console user.
	 */
	public JugPuzzleController() {
		scanner = new Scanner(System.in);
		jugPuzzle = new JugPuzzle();
	}

	/**
	 * Request an integer (move) from the user with lower<=move<=upper. Repeat
	 * until a valid user response is received.
	 * 
	 * @param message
	 *            the prompt for input
	 * @param lower
	 *            integer lower bound for user input
	 * @param upper
	 *            integer upper bound for user input
	 * @return integer move with lower<=move<=upper
	 */
	private int getMove(String message, int lower, int upper) {
		int move;
		while (true) {
			try {
				System.out.print(message);
				String line = scanner.nextLine();
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
	}

	/**
	 * Play the JugPuzzle with the user at the console, until the user solves
	 * the puzzle.
	 */
	public void play() {
		while (!jugPuzzle.getIsPuzzleSolved()) {
			System.out.println(jugPuzzle); // called the toString() method of
											// jugPuzzle
			int from, to;
			from = getMove("spill from jug: ", 0, 2);
			to = getMove("into jug: ", 0, 2);
			jugPuzzle.move(from, to);
		}
		System.out.println("Congrats you solved it in " + jugPuzzle.getMoves() + " moves!!");
	}
}