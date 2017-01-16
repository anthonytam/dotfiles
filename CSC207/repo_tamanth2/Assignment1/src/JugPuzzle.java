/**
 * 
 * @author csc207student 
 * This class represents an instance of the puzzle. Counts the number of moves
 * the player has used, if the puzzle has been solved, and contains methods to
 * make moves within the puzzle.
 */
public class JugPuzzle {
	private boolean isSolved;
	private int numberOfMoves;
	private Jug[] gameJugs;
	
	/**
	 * Creates a new instance of a jug puzzle. The puzzle has a fixed 
	 * configuration of 8/5/3 as its arrangement. 
	 */
	public JugPuzzle () {
		//Create jugs
		gameJugs = new Jug[3];
		gameJugs[0] = new Jug(8, 8);
		gameJugs[1] = new Jug(5);
		gameJugs[2] = new Jug(3);
		
		this.isSolved = false;
		this.numberOfMoves = 0;
	}
	
	/**
	 * Moves the object within the puzzle. Already assumes the move contains
	 * valid jug references as it is already checked elsewhere. There are 3 move
	 * cases which are checked:
	 * Moving to and from the same jug
	 * Moving to a larger jug
	 * Moving to a smaller jug
	 * After processing the move, this method will check to see if the win condition
	 * is met, if so the object will be marked as solved.
	 * @param from An integer specifying where the object is coming from.
	 * @param to An integer specifying where the object is moving to.
	 */
	public void move(int from, int to) {
		this.numberOfMoves += 1;
		// Ensure the user is not pouring into the same jug
		if (to != from) {
			// Spilling from a larger jug to smaller
			if(gameJugs[from].getCurrentVolume() >= gameJugs[to].getCapacity() - gameJugs[to].getCurrentVolume()) {
				if (!gameJugs[to].isFull()) { 
					gameJugs[from].changeVolume(-1 * (gameJugs[to].getCapacity() - gameJugs[to].getCurrentVolume()));
					gameJugs[to].changeVolume(gameJugs[to].getCapacity() - gameJugs[to].getCurrentVolume());
				}
			// Spilling from smaller jug to larger
			} else if (gameJugs[to].getCurrentVolume() + gameJugs[from].getCurrentVolume() <= gameJugs[to].getCapacity()) {
				gameJugs[to].changeVolume(gameJugs[from].getCurrentVolume());
				gameJugs[from].changeVolume(-1 * gameJugs[from].getCurrentVolume());
			}
		}
		
		// Check if solved
		if (gameJugs[0].getCurrentVolume() == 4 && gameJugs[1].getCurrentVolume() == 4 && gameJugs[2].getCurrentVolume() == 0)
			this.isSolved = true;
	}
	
	/**
	 * Returns if this instance of the puzzle has been solved.
	 * @return A boolean value which is true is solved, false otherwise
	 */
	public boolean getIsPuzzleSolved() {
		return this.isSolved;
	}
	
	/**
	 * Returns the number of moves that have occurred in the puzzle.
	 * @return An integer value representing the number of moves completed. 
	 */
	public int getMoves() {
		return this.numberOfMoves;
	}
	
	/**
	 * Returns a string representation of the current Jug Puzzle.
	 * Displays the capacity of the Jugs
	 */
	@Override
	public String toString() {
		return this.numberOfMoves + "\t 0:(" + gameJugs[0] + ") 1:(" + gameJugs[1] + ") 2:(" + gameJugs[2] + ")";
	}
}
