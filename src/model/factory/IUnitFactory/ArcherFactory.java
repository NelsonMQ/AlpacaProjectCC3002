package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Archer;

public class ArcherFactory implements IUnitFactory {

    @Override
    public Archer create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Archer(50,1,null,items);
    }
}
