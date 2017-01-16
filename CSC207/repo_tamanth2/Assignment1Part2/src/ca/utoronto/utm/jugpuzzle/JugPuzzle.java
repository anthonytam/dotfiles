package ca.utoronto.utm.jugpuzzle;

import java.util.Observable;
import java.util.Observer;

/**
 * An enum which describes the different states of the JugPuzzle.
 * SelectFrom: The game is currently waiting for the user to select a from Jug
 * SelectTo:   The game is currently waiting for the user to select a to Jug
 * GameWon:	   The user has won the current instance of the game
 * GameForfit: The user has given up and quit this instance of the game
 * 
 * @author csc207student
 *
 */
enum JugPuzzleState { SelectFrom, SelectTo, GameWon, GameForfit }

/**
 * A Jug Puzzle consists of three Jugs (numbered 0,1 and 2) with capacities 8,5
 * and 3 respectively. Initially, jug 0 is full, the other two are empty. The
 * player of the game spills liquid between the jugs (move) until both jugs 0
 * and 1 contain 4 units of liquid each. When a player makes a move, one Jug spills into
 * another. The JugPuzzle knows how many moves have taken place since the start
 * of the game. A spill ends as soon as one jug is empty or one jug is filled.
 * 
 * @author csc207student
 */

public class JugPuzzle extends Observable {
	private Jug[] jugs;
	private int moves;
	private int jugFrom;
	private JugPuzzleState gameState;

	/**
	 * Create a new JugPuzzle with three jugs, capacities 8,5,3
	 * and initial amounts 8,0,0. The goal is to achieve amounts
	 * 4,4,0. Initially the number of moves is 0.
	 */
	public JugPuzzle() {
		this.jugs = new Jug[3];
		this.jugs[0] = new Jug(8, 8);
		this.jugs[1] = new Jug(5);
		this.jugs[2] = new Jug(3);
		this.moves = 0;
		
		this.jugFrom = -1;
		this.gameState = JugPuzzleState.SelectFrom;
	}

	/**
	 * Checks to see if this instance of the game has been won and
	 * sets the game state accordingly.
	 */
	private void checkWin() {
		if (this.getIsPuzzleSolved()) {
			this.gameState = JugPuzzleState.GameWon;
			this.setChanged();
			this.notifyObservers(this.gameState);
		}
	}
	
	/**
	 * Resets the current instance of the game back to the game defaults.
	 * Any observers of the game are notified of this reset.
	 */
	public void reset() {
		this.jugs[0].reset();
		this.jugs[1].reset();
		this.jugs[2].reset();
		this.moves = 0;
		
		this.jugFrom = -1;
		this.gameState = JugPuzzleState.SelectFrom;
		
		this.setChanged();
		this.notifyObservers(this.gameState);
	}
	
	/**
	 * 
	 * @return the number of moves since the start of the game
	 */
	public int getMoves() {
		return moves;
	}

	/**
	 * 
	 * @return whether this is solved, that is 4 units in jugs 0 and 1 each.
	 */
	public boolean getIsPuzzleSolved() {
		return jugs[0].getAmount() == 4 && jugs[1].getAmount() == 4;
	}
	
	/**
	 * 
	 * @return The current state of this JugPuzzle instance.
	 */
	public JugPuzzleState getGameState() {
		return this.gameState;
	}
	
	/**
	 * Make a single move of the JugPuzzle, that is spill Jug 'from' into Jug 'to'.
	 * This counts as a single move.
	 * 
	 * @param from an integer identifying a jug
	 * @param to an integer identifying a jug
	 */
	public void move(int from, int to) {
		if(0<=from && from<jugs.length && 0<=to && to<jugs.length){
			jugs[from].spillInto(jugs[to]);
			moves++;
			if (this.jugFrom != -1) {
				jugFrom = -1;
				this.gameState = JugPuzzleState.SelectFrom;
				this.setChanged();
				this.notifyObservers(this.gameState);
			}
			this.checkWin();
		}
	}
	
	/**
	 * A step in making a single move of the JugPuzzle, this method needs to be
	 * called twice before a move is made. First call will set the from jug. Second
	 * call will cause a spill between the first call the the second.
	 * 
	 * @param jugID an integer identifying a jug
	 */
	public void move(int jugID) {
		if(this.gameState != JugPuzzleState.GameWon) {
			if(this.jugFrom == -1) {
				this.jugFrom = jugID;
				this.gameState = JugPuzzleState.SelectTo;
				this.setChanged();
				this.notifyObservers(this.gameState);
			} else
				this.move(this.jugFrom, jugID);
		}
	}
	
	/**
	 * If a move (1 param) is called, but another move has yet to be completed,
	 * this will cause that call to be ignored and another from move will be
	 * expected.
	 */
	public void undoSelection() {
		if (this.jugFrom != -1) {
			this.jugFrom = -1;
			this.gameState = JugPuzzleState.SelectFrom;
			this.setChanged();
			this.notifyObservers(this.gameState);
		}
	}
	
	/**
	 * Modifies the current state of the game to a different state
	 * 
	 * @param newState the game state to switch to
	 */
	public void modifyGameState(JugPuzzleState newState) {
		this.gameState = newState;
		this.setChanged();
		this.notifyObservers(this.gameState);
	}
	
	/**
	 * A string representation of the requested Jug
	 * 
	 * @param jugIndex an integer identifying a jug
	 * @return a string representation of the requested jug
	 */
	public String getJugInfo(int jugIndex) {
		return jugs[jugIndex].toString();
	}
	
	/**
	 * Adds a view to observe the requested jug
	 * 
	 * @param jug an integer identifying a jug
	 * @param view an observer requesting to monitor a jug
	 */
	public void addJugObserver(int jug, Observer view) {
		this.jugs[jug].addObserver(view);
	}
	
	/**
	 * @return a string representation of this
	 */
	public String toString() {
		return moves + " " + " 0:" + jugs[0] + " 1:" + jugs[1] + " 2:" + jugs[2];
	}
}