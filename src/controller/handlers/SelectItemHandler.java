package controller.handlers;

import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents a handler to select an item of the selected unit of the player.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class SelectItemHandler implements PropertyChangeListener {
    private GameController controller;

    /**
     * Creates a handler to observe the tactician
     * @param controller
     *      The controller of the game
     */
    public SelectItemHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.selectItem((int)evt.getNewValue());
    }
}
