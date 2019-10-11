package controller.handlers;

import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents a handler to select a unit of the player.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class SelectUnitHandler implements PropertyChangeListener {
    private GameController controller;

    /**
     * Creates a handler to observe the tactician
     * @param controller
     *      The controller of the game
     */
    public SelectUnitHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if((int)evt.getOldValue()==-1)
            controller.selectUnitIn((int)evt.getNewValue(),(int)evt.getNewValue());
        else
            controller.selectUnitIn((int)evt.getOldValue(),(int)evt.getNewValue());
    }
}
