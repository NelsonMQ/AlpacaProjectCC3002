package model.factory.IEquipableItemFactory;

import model.items.Spear;

public class SpearFactory implements IEquipableItemFactory {

    @Override
    public Spear create() {
        return new Spear("Spear",10,1,1);
    }
}
