package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Sorcerer;

/**
 * This class represents the Sorcerer factory.
 * It can create the Sorcerer unit of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class SorcererFactory implements IUnitFactory {

    /**
     * Creates a new Sorcerer unit.
     * @return
     *      The new Sorcerer
     */
    @Override
    public Sorcerer create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Sorcerer(50,1,null,items);
    }
}
