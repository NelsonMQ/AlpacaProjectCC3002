package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Alpaca;

/**
 * This class represents the Alpaca factory.
 * It can create the Alpaca unit of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class AlpacaFactory implements IUnitFactory {

    /**
     * Creates a new Alpaca unit.
     * @return
     *      The new Alpaca
     */
    @Override
    public Alpaca create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Alpaca(50,1,null, items);
    }
}
