package controller.handlers;

import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents a handler to check the heroes of the tactician.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class CheckHeroesHandler implements PropertyChangeListener {
    private GameController controller;

    /**
     * Creates a handler to observe the tactician
     * @param controller
     *      The controller of the game
     */
    public CheckHeroesHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.checkPlayersHeroes();
    }
}
