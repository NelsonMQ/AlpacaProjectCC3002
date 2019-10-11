package model.factory.IEquipableItemFactory;

import model.items.Spirit;

/**
 * This class represents the Spirit factory.
 * It can create the Spirit item of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class SpiritFactory implements IEquipableItemFactory {

    /**
     * Creates a new Spirit item.
     * @return
     *      The new Spirit
     */
    @Override
    public Spirit create() {
        return new Spirit("Spirit",10,1,1);
    }
}
