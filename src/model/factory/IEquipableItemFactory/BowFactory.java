package model.factory.IEquipableItemFactory;

import model.items.Bow;

public class BowFactory implements IEquipableItemFactory {

    @Override
    public Bow create() {
        return new Bow("Bow",10,2,2);
    }
}
