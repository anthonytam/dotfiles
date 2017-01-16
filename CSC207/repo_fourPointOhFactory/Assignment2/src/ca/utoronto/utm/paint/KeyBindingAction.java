package ca.utoronto.utm.paint;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This class is used as an Action object which is able to handle calls from an Action Map.
 * The action map is called from the view when a specific key binding is pressed. The design
 * allows for additional key beings to be added easily.
 */
public class KeyBindingAction extends AbstractAction {
    private View view;
    private PaintModel model;
    private KeyBindingActionType index;

    /**
     * Creates a new instance of the KeyBindingAction class. This class requires
     * the model and view which is it handling the key combinations for,.
     * @param v The view object for the program
     * @param m The PaintModel object for the program
     * @param index The type of key binding the object will handle
     */
    public KeyBindingAction(View v, PaintModel m, KeyBindingActionType index) {
        this.index = index;
        this.model = m;
        this.view = v;
    }

    /**
     * Handles the event when the key binding is pressed. Will switch through different
     * combinations depending on the type of action that is requested.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        switch (index) {
            case UNDO:
                model.undo();
                break;
            case REDO:
                model.redo();
                break;
            case NEW:
                model.clearAll();
        }
        view.getPaintPanel().repaint();
    }
}
