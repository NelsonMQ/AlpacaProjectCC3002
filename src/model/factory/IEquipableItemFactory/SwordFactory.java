package model.factory.IEquipableItemFactory;

import model.items.Sword;

/**
 * This class represents the Sword factory.
 * It can create the Sword item of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class SwordFactory implements IEquipableItemFactory {

    /**
     * Creates a new Sword item.
     * @return
     *      The new Sword
     */
    @Override
    public Sword create() {
        return new Sword("Sword",10,1,1);
    }
}
