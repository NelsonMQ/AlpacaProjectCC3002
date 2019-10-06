package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Cleric;

public class ClericFactory implements IUnitFactory {

    @Override
    public Cleric create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Cleric(50,1,null,items);
    }
}
