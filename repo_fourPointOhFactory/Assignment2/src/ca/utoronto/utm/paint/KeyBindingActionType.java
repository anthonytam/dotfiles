package ca.utoronto.utm.paint;

/**
 * An enumeration representing the different actions the KeyBindingAction class
 * is capable of handing.
 */
public enum KeyBindingActionType {
    UNDO('Z'),
    REDO('Y'),
    NEW('N');

    private char hotkey;

    KeyBindingActionType(char hotkey) {
        this.hotkey = hotkey;
    }

	/**
     * Hotkey that is used to execute this action.
     * @return Character used to execute the hotkey.
     */
    public char getHotkey() {
        return hotkey;
    }
}