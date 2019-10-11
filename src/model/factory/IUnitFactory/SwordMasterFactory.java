package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.SwordMaster;

/**
 * This class represents the Sword Master factory.
 * It can create the Sword Master unit of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class SwordMasterFactory implements IUnitFactory {

    /**
     * Creates a new Sword Master unit.
     * @return
     *      The new Sword Master
     */
    @Override
    public SwordMaster create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new SwordMaster(50,1,null,items);
    }
}
