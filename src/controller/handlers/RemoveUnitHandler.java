package controller.handlers;

import controller.GameController;
import model.units.IUnit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents a handler to remove a unit.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class RemoveUnitHandler implements PropertyChangeListener {
    private GameController controller;

    /**
     * Creates a handler to observe the tactician
     * @param controller
     *      The controller of the game
     */
    public RemoveUnitHandler(GameController controller) {
        this.controller =  controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.removeUnit((IUnit) evt.getNewValue());
    }
}
