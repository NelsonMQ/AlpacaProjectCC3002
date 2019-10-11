package controller.handlers;

import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents a handler to end the turn of a player.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class EndTurnHandler implements PropertyChangeListener {
    private GameController controller;

    /**
     * Creates a handler to observe the tactician
     * @param controller
     *      The controller of the game
     */
    public EndTurnHandler(GameController controller){
        this.controller = controller;
    }

    /**
     * Ends the turn of the tactician
     * @param evt
     *      The Property Change Event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.endTurn();
    }
}
