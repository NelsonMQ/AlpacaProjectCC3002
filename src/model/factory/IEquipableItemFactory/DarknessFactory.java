package model.factory.IEquipableItemFactory;

import model.items.Darkness;

/**
 * This class represents the Darkness factory.
 * It can create the Darkness item of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class DarknessFactory implements IEquipableItemFactory {

    /**
     * Creates a new Darkness item.
     * @return
     *      The new Darkness
     */
    @Override
    public Darkness create() {
        return new Darkness("Darkness",10,1,1);
    }
}
