package model.factory.IEquipableItemFactory;

import model.items.Spirit;

public class SpiritFactory implements IEquipableItemFactory {

    @Override
    public Spirit create() {
        return new Spirit("Spirit",10,1,1);
    }
}
