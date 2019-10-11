package model.factory.IEquipableItemFactory;

import model.items.Light;

/**
 * This class represents the Light factory.
 * It can create the Light item of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class LightFactory implements IEquipableItemFactory {

    /**
     * Creates a new Light item.
     * @return
     *      The new Light
     */
    @Override
    public Light create() {
        return new Light("Light",10,1,1);
    }
}
