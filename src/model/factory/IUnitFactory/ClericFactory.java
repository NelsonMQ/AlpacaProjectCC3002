package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Cleric;

/**
 * This class represents the Cleric factory.
 * It can create the Cleric unit of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class ClericFactory implements IUnitFactory {

    /**
     * Creates a new Cleric unit.
     * @return
     *      The new Cleric
     */
    @Override
    public Cleric create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Cleric(50,1,null,items);
    }
}
