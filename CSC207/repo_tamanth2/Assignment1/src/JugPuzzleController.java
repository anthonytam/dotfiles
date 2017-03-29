import java.util.*;
/**
 * @author csc207student
 * This class allows a console user to play an instance of JugPuzzle. Acts as an interface
 * to the jug puzzle so that the plays can enter data into the game. The controller will prompt
 * the user for values to be passed into the puzzle.
 */
public class JugPuzzleController {
	private static final String INVALID_INPUT_MESSAGE="Invalid number, please enter 0,1 or 2";

	private Scanner scanner;
	private JugPuzzle jugPuzzle;

	/**
	 * Constructs a new JugPuzzleController with a new JugPuzzle, ready to play with a console user.
	 */
	public JugPuzzleController() {
		scanner=new Scanner(System.in);
		jugPuzzle=new JugPuzzle();
	}

	/**
	 * Prompt the player to enter a move. 
	 * Displays a message to the user and ensure that the move is within the upper 
	 * and lower boundaries of the jugs. If they are not, the method will continue to
	 * prompt the user until valid input is obtained.
	 * @param message  A string containing the message that will be displayed to the user
	 * @param lower    An integer representing the lowest bottle the object can spill to/from
	 * @param upper    An integer representing the topmost bottle the object can spill to/from
	 * @return         Returns an integer containing the jug the player wishes to interact with
	 */
	private int getMove(String message, int lower, int upper) {
		int move;
		while(true){
			try {
				System.out.print(message);
				String line=scanner.nextLine();
				move=Integer.parseInt(line);
				if(lower<=move && move<=upper){
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			}
			catch(NumberFormatException e){
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
	}

	/**
	 * Starts an instance of the Jug Puzzle game. Continuously prompts the user to 
	 * make moves until the puzzle is solved. Once the win condition has been met, displays
	 * a message to the user stating they have won and informs the user the number of moves
	 * that were used to solve the puzzle.
	 */
	public void play(){
		while(!jugPuzzle.getIsPuzzleSolved()) {
			System.out.println(jugPuzzle); // called the toString() method of jugPuzzle
			int from, to;
			from=getMove("spill from jug: ", 0,2);
			to=getMove("into jug: ",0,2);
			jugPuzzle.move(from,to);
		}
		System.out.println("Congrats you solved it in "+jugPuzzle.getMoves()+" moves!!");
	}
	
	/**
	 * Begins the jug puzzle game. Creates a new controller to start the game (Creating 
	 * the puzzle and the Jugs for the game) and begins the game.
	 * @param args String startup arguments for the Java program. Not used for this program.
	 */
	public static void main(String [] args) {
		JugPuzzleController jpcc=new JugPuzzleController();
		jpcc.play();
	}
}
