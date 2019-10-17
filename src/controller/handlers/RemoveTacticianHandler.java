package controller.handlers;

import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents a handler to remove a tactician of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class RemoveTacticianHandler implements PropertyChangeListener {
    private GameController controller;

    /**
     * Creates a handler to observe the tactician
     * @param controller
     *      The controller of the game
     */
    public RemoveTacticianHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.removeTactician((String)evt.getNewValue());
    }
}

