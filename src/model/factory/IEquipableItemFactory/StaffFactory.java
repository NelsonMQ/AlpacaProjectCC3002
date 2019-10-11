package model.factory.IEquipableItemFactory;

import model.items.Staff;

/**
 * This class represents the Staff factory.
 * It can create the Staff item of the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */
public class StaffFactory implements IEquipableItemFactory{

    /**
     * Creates a new Staff item.
     * @return
     *      The new Staff
     */
    @Override
    public Staff create() {
        return new Staff("Staff",10,1,1);
    }
}
