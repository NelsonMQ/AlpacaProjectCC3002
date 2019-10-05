package model.factory.IEquipableItemFactory;

import model.items.Sword;

public class SwordFactory implements IEquipableItemFactory {

    @Override
    public Sword create() {
        return new Sword("Sword",10,1,1);
    }
}
