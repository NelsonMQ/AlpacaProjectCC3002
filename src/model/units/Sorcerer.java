package model.units;

import model.items.Light;
import model.items.Darkness;
import model.items.Spirit;
import model.items.IEquipableItem;
import model.map.Location;

/**
 * This class represents a sorcerer type unit.
 * A sorcerer is a unit that can only use magic weapons (Light, Darkness and Spirit).
 *
 * @author Nelson Marambio
 * @since 1.1
 */
public class Sorcerer extends AbstractUnit {

    public Sorcerer(final int hitPoints, final int movement, final Location location,
                   IEquipableItem... items) {
        super(hitPoints, movement, location, 3, items);
    }

    /**
     * Sets the currently equipped item of this unit.
     *
     * @param item
     *     the item to equip
     */
    @Override
    public void equipItem(final IEquipableItem item) {
        if(this.getItems().contains(item)) {
            item.equipToSorcerer(this);
        }
    }
}