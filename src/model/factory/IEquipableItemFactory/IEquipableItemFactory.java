package model.factory.IEquipableItemFactory;

import model.items.IEquipableItem;

/**
 * This interface represents the items factory.
 * It define a common method for all the items factory.
 * @author Nelson Marambio
 * @since 2.1
 */
public interface IEquipableItemFactory {

    /**
     * Creates a new item
     * @return
     *      The created item
     */
    IEquipableItem create();

}
