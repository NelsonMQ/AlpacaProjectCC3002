package model.factory.IUnitFactory;

import model.factory.IUnitFactory.IUnitFactory;
import model.items.IEquipableItem;
import model.units.SwordMaster;

public class SwordMasterFactory implements IUnitFactory {

    @Override
    public SwordMaster create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new SwordMaster(50,1,null,items);
    }
}
