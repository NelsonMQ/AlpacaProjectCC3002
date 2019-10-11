package model.factory.IEquipableItemFactory;

import model.items.Axe;

/**
 * This class represents the Axe factory.
 * It can create the Axe item of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class AxeFactory implements IEquipableItemFactory {

    /**
     * Creates a new Axe item.
     * @return
     *      The new Axe
     */
    @Override
    public Axe create() {
        return new Axe("Axe",10,1,1);
    }
}
