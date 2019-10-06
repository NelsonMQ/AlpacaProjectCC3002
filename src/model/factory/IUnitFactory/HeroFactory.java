package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Hero;

public class HeroFactory implements IUnitFactory {

    @Override
    public Hero create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Hero(50,1,null,items);
    }
}
