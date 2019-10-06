package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Fighter;

public class FighterFactory implements IUnitFactory {

    @Override
    public Fighter create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Fighter(50,1,null,items);
    }
}
