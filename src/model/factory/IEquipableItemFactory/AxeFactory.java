package model.factory.IEquipableItemFactory;

import model.items.Axe;

public class AxeFactory implements IEquipableItemFactory {

    @Override
    public Axe create() {
        return new Axe("Axe",10,1,1);
    }
}
