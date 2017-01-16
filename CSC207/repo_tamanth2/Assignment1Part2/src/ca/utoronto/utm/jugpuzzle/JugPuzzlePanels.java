package ca.utoronto.utm.jugpuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * A class representing the status bar elements of the game. Create a JPanel with a status
 * bar and a button. Also acts as an observer to monitor the state of the game and update
 * the status bar. ActionListener is used for the undo button at the top to allow the user
 * to void a previous move.
 * 
 * @author csc207student
 *
 */
@SuppressWarnings("serial")
class JugPuzzleStatusBar extends JPanel implements Observer, ActionListener {
	
	JugPuzzle JP;
	
    JTextField tStatus;
    JButton bUndo;

    /**
     * Create a new instance of the StatusBar panel for the GUI. Creates the
     * text box and button assigning default values to the text fields. Also 
     * prepares the ActionListener for the button
     * @param jp An instance of the JugPuzzle being played.
     */
    public JugPuzzleStatusBar(JugPuzzle jp) {    	
        this.JP = jp;
    	
    	tStatus = new JTextField("Game: Select From Jug\tMove: 0", 22);
        tStatus.setEditable(false);
        add(tStatus);
        
        bUndo = new JButton("Undo");
        bUndo.setPreferredSize(new Dimension(100, 20));
        bUndo.setEnabled(false);
        add(bUndo);
        bUndo.addActionListener(this);
    }

    /**
     * Called when the state of the game has been changed. Checks to see the
     * new statis and update the status bar accordingly. If the status allows
     * for an undo, the button is enabled, otherwise it is disabled.
     */
	@Override
	public void update(Observable o, Object arg) {
		JugPuzzleState gameState = (JugPuzzleState)arg;
		JugPuzzle jp = (JugPuzzle)o;
		if (gameState == JugPuzzleState.SelectTo) {
			this.tStatus.setText("Game: Select To Jug\tMove: " + jp.getMoves());
			this.bUndo.setEnabled(true);
		} else if (gameState == JugPuzzleState.SelectFrom) {
			this.tStatus.setText("Game: Select From Jug\tMove: " + jp.getMoves());
			this.bUndo.setEnabled(false);
		} else if (gameState == JugPuzzleState.GameWon) {
			this.tStatus.setText("Game: Won in " + jp.getMoves() + " moves!");
			this.bUndo.setEnabled(false);
		} else {
			this.tStatus.setText("Game: Forfeited in " + jp.getMoves() + " moves!");
			this.bUndo.setEnabled(false);
		}
	}

	/**
	 * When the undo button is available to be activated, calls a function within
	 * the JugPuzzle to undo the previous selection and set the game state back.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.JP.undoSelection();
	}
}

/**
 * A class representing the labels of the Jugs. Each text field shows a representation
 * of its respective Jug for the user to see the volume of each. The Labels panels is 
 * also an implementation of an Observer monitoring each individual jug for changes and
 * replicating those changes onto the text fields.
 * 
 * @author csc207student
 *
 */
@SuppressWarnings("serial")
class JugPuzzleJugLabels extends JPanel implements Observer {

    JTextField[] jugLabels;

    /**
     * Creates a new instance of the Jug labels panels. Creates 3 un-editable text fields
     * to be populated with jug information from the given JugPuzzle instance.
     * @param jp an instance of the current JugPuzzle being played. 
     */
    public JugPuzzleJugLabels(JugPuzzle jp) {

        jugLabels = new JTextField[3];
        for(int i = 0; i < 3; i++) {
            jugLabels[i] = new JTextField(10);
            jugLabels[i].setEditable(false);
            jugLabels[i].setText(jp.getJugInfo(i));
            add(jugLabels[i]);
        }
    }

    /**
     * The monitor method of the observer. Monitor for any changes in the volume
     * of any jugs in the game. If alerted to a change, the text field for the 
     * corrosponding Jug will be updated accordingly. 
     */
	@Override
	public void update(Observable o, Object arg) {
		Jug modifiedJug = (Jug)o;
		if (modifiedJug.getCapacity() == 8)
			jugLabels[0].setText(modifiedJug.toString());
		else if (modifiedJug.getCapacity() == 5)
			jugLabels[1].setText(modifiedJug.toString());
		else
			jugLabels[2].setText(modifiedJug.toString());
	}
}

/**
 * A call representing a panels of buttons the user can press to spill between other jugs.
 * Strictly implements only ActionListener to allow move commands. This panel is not a 
 * part of the view model and is not monitoring any changes in the model. 
 * 
 * @author csc207student
 *
 */
@SuppressWarnings("serial")
class JugPuzzleSpillButtons extends JPanel implements ActionListener {

    JButton[] spillButtons;
    JugPuzzle JP;

    /**
     * Creates a new instance of the panel by creating 3 buttons and labeling them for their
     * Corresponding Jug. 
     * @param jp an instance of the current JugPuzzle played by the user.
     */
    public JugPuzzleSpillButtons(JugPuzzle jp) {
    	this.JP = jp;

        spillButtons = new JButton[3];
        for(int i = 0; i < 3; i++) {
            spillButtons[i] = new JButton("Jug: " + i);
            spillButtons[i].setPreferredSize(new Dimension(115, 25));
            add(spillButtons[i]);
            spillButtons[i].addActionListener(this);
        }
    }

    /**
     * Call the move method for the JugPuzzle given the user clicked a button. The method
     * is able to find which move this is intended to be given the game state.
     */
    public void actionPerformed (ActionEvent e) {
    	this.JP.move(Integer.valueOf(String.valueOf(((JButton)e.getSource()).getText().charAt(((JButton)e.getSource()).getText().length() - 1))));
    }
}

/**
 * A class representing a panel with multiple misc options for the user. These options
 * include forfeiting a game, starting a new game, and exiting the game. The ActionListener
 * Carries out these requests while the Observer ensures the button text remains up to date
 * with the state of the game.
 * 
 * @author csc207student
 *
 */
@SuppressWarnings("serial")
class JugPuzzleOptionButtons extends JPanel implements Observer, ActionListener {

	JugPuzzle JP;
	
	JButton bForfit;
	JButton bQuit;
	
	/**
	 * Create a new instance of the OptionButtons. Creates two buttons and assigns each
	 * of them to the action listener.
	 * @param jp
	 */
	public JugPuzzleOptionButtons (JugPuzzle jp) {
		this.JP = jp;
		
		bForfit = new JButton("Forfit");
		bForfit.setPreferredSize(new Dimension(115, 25));
		add(bForfit);
		bForfit.addActionListener(this);
		
		bQuit = new JButton("Exit Game");
		bQuit.setPreferredSize(new Dimension(115, 25));
		add(bQuit);
		bQuit.addActionListener(this);
	}
	
	/**
	 * Carries out the requirements of the button. Checks the button text to
	 * determine which action is required:
	 * Change game state to forfited
	 * Reset the game and start over
	 * Exit the game
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bForfit) {
			if(bForfit.getText() == "Forfit") {
				this.JP.modifyGameState(JugPuzzleState.GameForfit);
			} else {
				this.JP.reset();
			}
		} else {
			System.exit(0);
		}
		
	}

	/**
	 * Observer to monitor the game state. As the game state changes, checks if the 
	 * game is over, if so changes the button text to offer the place to start a new game. If
	 * a game is currently ongoing, allows the user an option to stop playing.
	 */
	@Override
	public void update(Observable o, Object arg) {
		JugPuzzleState gameState = (JugPuzzleState)arg;
		if (gameState == JugPuzzleState.GameWon || gameState == JugPuzzleState.GameForfit)
			this.bForfit.setText("New Game");
		else
			this.bForfit.setText("Forfit");
	}
	
}

