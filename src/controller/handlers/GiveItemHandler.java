package controller.handlers;

import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents a handler to give an item to the selected unit of the player.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class GiveItemHandler implements PropertyChangeListener {
    private GameController controller;

    /**
     * Creates a handler to observe the tactician
     * @param controller
     *      The controller of the game
     */
    public GiveItemHandler(GameController controller) {
        this.controller = controller;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if((int)evt.getOldValue()==-1)
            controller.giveItemTo((int)evt.getNewValue(),(int)evt.getNewValue());
        else
            controller.giveItemTo((int)evt.getOldValue(),(int)evt.getNewValue());
    }
}
