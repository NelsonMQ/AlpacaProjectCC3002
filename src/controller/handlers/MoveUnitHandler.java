package controller.handlers;

import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents a handler to move a unit of the player.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class MoveUnitHandler implements PropertyChangeListener {
    private GameController controller;

    /**
     * Creates a handler to observe the tactician
     * @param controller
     *      The controller of the game
     */
    public MoveUnitHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if((int)evt.getOldValue()==-1)
            controller.moveSelectedUnitTo((int)evt.getNewValue(),(int)evt.getNewValue());
        else
            controller.moveSelectedUnitTo((int)evt.getOldValue(),(int)evt.getNewValue());
    }
}
