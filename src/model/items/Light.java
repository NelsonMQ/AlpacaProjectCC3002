package model.items;

import model.units.IUnit;

/**
 * This class represents a <i>light</i>.
 * <p>
 * Lights are strong against darkness and non-magic weapons, and weak against spirits
 *
 * @author Nelson Marambio
 * @since 1.1
 */

public class Light extends AttackItem {

    /**
     * Creates a new Light item
     *
     * @param name
     *     the name of the Light
     * @param power
     *     the damage of the light
     * @param minRange
     *     the minimum range of the light
     * @param maxRange
     *     the maximum range of the light
     */
    public Light(final String name, final int power, final int minRange, final int maxRange) {
        super(name, power, minRange, maxRange);
    }

    @Override
    public void attack(IUnit unit) {
        if(unit.getCurrentHitPoints()>0) {
            if(unit.getEquippedItem() instanceof Darkness ||
                    unit.getEquippedItem() instanceof Sword ||
                    unit.getEquippedItem() instanceof Spear ||
                    unit.getEquippedItem() instanceof Axe ||
                    unit.getEquippedItem() instanceof Bow) {
                unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (int)(1.5*this.getPower()));
            }
            else if (unit.getEquippedItem() instanceof Spirit){
                if( this.getPower()-20>0)
                    unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (this.getPower() - 20));
            }
            else{
                unit.setCurrentHitPoints(unit.getCurrentHitPoints() - this.getPower());
            }
        }
    }
}
