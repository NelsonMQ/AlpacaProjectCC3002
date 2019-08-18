package model.items;

import model.units.IUnit;

/**
 * This class represents a <i>darkness</i>.
 * <p>
 * Darkness are strong against spirits and non-magic weapons ,and weak against lights
 *
 * @author Nelson Marambio
 * @since 1.1
 */

public class Darkness extends AttackItem {

    /**
     * Creates a new Darkness item
     *
     * @param name
     *     the name of the Darkness
     * @param power
     *     the damage of the darkness
     * @param minRange
     *     the minimum range of the darkness
     * @param maxRange
     *     the maximum range of the darkness
     */
    public Darkness(final String name, final int power, final int minRange, final int maxRange) {
        super(name, power, minRange, maxRange);
    }

    @Override
    public void attack(IUnit unit) {
        if(unit.getCurrentHitPoints()>0) {
            if(unit.getEquippedItem() instanceof Spirit ||
                    unit.getEquippedItem() instanceof Sword ||
                    unit.getEquippedItem() instanceof Spear ||
                    unit.getEquippedItem() instanceof Axe ||
                    unit.getEquippedItem() instanceof Bow) {
                unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (int)(1.5*this.getPower()));
            }
            else if (unit.getEquippedItem() instanceof Light){
                if( this.getPower()-20>0)
                    unit.setCurrentHitPoints(unit.getCurrentHitPoints() - (this.getPower() - 20));
            }
            else{
                unit.setCurrentHitPoints(unit.getCurrentHitPoints() - this.getPower());
            }
        }
    }
}
