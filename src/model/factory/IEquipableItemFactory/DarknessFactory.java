package model.factory.IEquipableItemFactory;

import model.items.Darkness;

public class DarknessFactory implements IEquipableItemFactory {

    @Override
    public Darkness create() {
        return new Darkness("Darkness",10,1,1);
    }
}
