package model.factory.IUnitFactory;

import model.units.IUnit;

/**
 * This interface represents the units factory.
 * It define a common method for all the units factory.
 * @author Nelson Marambio
 * @since 2.1
 */
public interface IUnitFactory {

    /**
     * Creates a new unit
     * @return
     *      The created unit
     */
    IUnit create();
}
