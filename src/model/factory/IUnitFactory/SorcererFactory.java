package model.factory.IUnitFactory;

import model.factory.IUnitFactory.IUnitFactory;
import model.items.IEquipableItem;
import model.units.Sorcerer;

public class SorcererFactory implements IUnitFactory {

    @Override
    public Sorcerer create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Sorcerer(50,1,null,items);
    }
}
