package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Fighter;

/**
 * This class represents the Fighter factory.
 * It can create the Fighter unit of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class FighterFactory implements IUnitFactory {

    /**
     * Creates a new Fighter unit.
     * @return
     *      The new Fighter
     */
    @Override
    public Fighter create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Fighter(50,1,null,items);
    }
}
