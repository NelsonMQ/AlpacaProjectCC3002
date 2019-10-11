package model.factory.IEquipableItemFactory;

import model.items.Bow;

/**
 * This class represents the Bow factory.
 * It can create the Bow item of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class BowFactory implements IEquipableItemFactory {

    /**
     * Creates a new Bow item.
     * @return
     *      The new Bow
     */
    @Override
    public Bow create() {
        return new Bow("Bow",10,2,2);
    }
}
