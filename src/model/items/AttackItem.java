package model.items;

import model.units.IUnit;

public class AttackItem extends AbstractItem {

    /**
     *
     */
    public AttackItem(final String name, final int power, final int minRange, final int maxRange) {
        super(name, power, minRange, maxRange);
    }

    /**
     * @param unit
     *     the unit to attack
     */
    public void attack(IUnit unit) {
        if(unit.getCurrentHitPoints()>0){
            unit.setCurrentHitPoints(unit.getCurrentHitPoints() - this.getPower());
        }
    }
}
