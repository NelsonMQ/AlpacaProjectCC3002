package model.factory.IEquipableItemFactory;

import model.items.Staff;

public class StaffFactory implements IEquipableItemFactory{

    @Override
    public Staff create() {
        return new Staff("Staff",10,1,1);
    }
}
