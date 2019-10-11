package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Hero;

/**
 * This class represents the Hero factory.
 * It can create the Hero unit of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class HeroFactory implements IUnitFactory {

    /**
     * Creates a new Hero unit.
     * @return
     *      The new Hero
     */
    @Override
    public Hero create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Hero(50,1,null,items);
    }
}
