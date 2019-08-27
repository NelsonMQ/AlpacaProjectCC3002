package model.items;

import model.units.IUnit;

/**
 * This interface represents the <i>attackItems</i> that the units of the game can use to attack.
 * <p>
 * The signature for all the common methods of the attack items are defined here.
 *
 * @author Nelson Marambio
 * @since 1.1
 */
public interface IAttackItem {

    /**
     * @param unit
     *     the unit to attack
     */
    void attack(IUnit unit);

    /**
     * This attack has normal damage
     * @param unit
     *     the unit to attack
     */
    void normalAttack(IUnit unit);

    /**
     * This attack has a strong damage (1.5*damage is approximate to int)
     * @param unit
     *     the unit to attack
     */
    void strongAttack(IUnit unit);

    /**
     * This attack has a weak damage (damage-20)
     * @param unit
     *     the unit to attack
     */
    void weakAttack(IUnit unit);
}
