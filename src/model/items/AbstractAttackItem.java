package model.items;

import model.units.IUnit;

/**
 * Class that defines some common information and behaviour between some items.
 *
 * @author Nelson Marambio
 * @since 1.1
 */
public abstract class AbstractAttackItem extends AbstractItem implements IAttackItem {

    /**
     * Constructor for a default attack item without any special behaviour.
     *
     * @param name
     *     the name of the item
     * @param power
     *     the power of the item
     * @param minRange
     *     the minimum range of the item
     * @param maxRange
     *     the maximum range of the item
     */
    public AbstractAttackItem(final String name, final int power, final int minRange, final int maxRange) {
        super(name, power, minRange, maxRange);
    }

    /**
     * Checks if the item can attack
     * @return true if the item can attack
     */
    public boolean canAttack() {
        return true;
    }

    public abstract void attack(IUnit unit);

    public void normalAttack(IUnit unit) {
        unit.setCurrentHitPoints(unit.getCurrentHitPoints()-this.getPower());
    }

    public void strongAttack(IUnit unit) {
        unit.setCurrentHitPoints(unit.getCurrentHitPoints()-(int)(1.5*this.getPower()));
    }

    public void weakAttack(IUnit unit) {
        if((this.getPower()-20)>0){
            unit.setCurrentHitPoints(unit.getCurrentHitPoints()-(this.getPower()-20));
        }
    }
}

