package model.factory.IEquipableItemFactory;

import model.items.Light;

public class LightFactory implements IEquipableItemFactory {

    @Override
    public Light create() {
        return new Light("Light",10,1,1);
    }
}
