package model.items;

import model.units.IUnit;

/**
 * This class represents a <i>spirit</i>.
 * <p>
 * Spirits are strong against lights and non-magic weapons, weak against darkness
 *
 * @author Nelson Marambio
 * @since 1.1
 */

public class Spirit extends AttackItem {

    /**
     * Creates a new Spirit item
     *
     * @param name
     *     the name of the Spirit
     * @param power
     *     the damage of the spirit
     * @param minRange
     *     the minimum range of the spirit
     * @param maxRange
     *     the maximum range of the spirit
     */
    public Spirit(final String name, final int power, final int minRange, final int maxRange) {
        super(name, power, minRange, maxRange);
    }

    @Override
    public void attack(IUnit unit) {
        if(unit.getCurrentHitPoints()>0) {
            if(unit.getEquippedItem() instanceof Light ||
                    unit.getEquippedItem() instanceof Sword ||
                    unit.getEquippedItem() instanceof Spear ||
                    unit.getEquippedItem() instanceof Axe ||
                    unit.getEquippedItem() instanceof Bow) {
                unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (int)(1.5*this.getPower()));
            }
            else if (unit.getEquippedItem() instanceof Darkness){
                if( this.getPower()-20>0)
                    unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (this.getPower() - 20));
            }
            else{
                unit.setCurrentHitPoints(unit.getCurrentHitPoints() - this.getPower());
            }
        }
    }
}
