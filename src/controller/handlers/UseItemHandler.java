package controller.handlers;

import controller.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents a handler to use the equipped item of the selected unit on other unit.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class UseItemHandler implements PropertyChangeListener {
    private GameController controller;

    /**
     * Creates a handler to observe the tactician
     * @param controller
     *      The controller of the game
     */
    public UseItemHandler(GameController controller) {
        this.controller =  controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if((int)evt.getOldValue()==-1)
            controller.useItemOn((int)evt.getNewValue(),(int)evt.getNewValue());
        else
            controller.useItemOn((int)evt.getOldValue(),(int)evt.getNewValue());
    }
}
