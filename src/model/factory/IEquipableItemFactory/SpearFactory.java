package model.factory.IEquipableItemFactory;

import model.items.Spear;

/**
 * This class represents the Spear factory.
 * It can create the Spear item of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class SpearFactory implements IEquipableItemFactory {

    /**
     * Creates a new Spear item.
     * @return
     *      The new Spear
     */
    @Override
    public Spear create() {
        return new Spear("Spear",10,1,1);
    }
}
