package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Archer;

/**
 * This class represents the Archer factory.
 * It can create the Archer unit of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class ArcherFactory implements IUnitFactory {

    /**
     * Creates a new Archer unit.
     * @return
     *      The new Archer
     */
    @Override
    public Archer create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Archer(50,1,null,items);
    }
}
