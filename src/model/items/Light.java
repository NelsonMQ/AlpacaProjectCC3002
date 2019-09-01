package model.items;

import model.units.*;

/**
 * This class represents a <i>light</i>.
 * <p>
 * Lights are strong against darkness and non-magic weapons, and weak against spirits
 *
 * @author Nelson Marambio
 * @since 1.1
 */

public class Light extends AbstractAttackItem {

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

    public void attack(IUnit unit) {
        if(unit.getEquippedItem()==null){
            normalAttack(unit);
        }
        else {
            unit.getEquippedItem().receiveLightDamage(this);
        }
    }

    @Override
    public void receiveAxeDamage(Axe axe) {
        strongAttack(this.getOwner());
    }

    @Override
    public void receiveBowDamage(Bow bow) {
        strongAttack(this.getOwner());
    }

    @Override
    public void receiveSpearDamage(Spear spear) {
        strongAttack(this.getOwner());
    }

    @Override
    public void receiveSwordDamage(Sword sword) {
        strongAttack(this.getOwner());
    }

    @Override
    public void receiveLightDamage(Light light) {
        normalAttack(this.getOwner());
    }

    @Override
    public void receiveDarknessDamage(Darkness darkness) {
        weakAttack(this.getOwner());
    }

    @Override
    public void receiveSpiritDamage(Spirit spirit) {
        strongAttack(this.getOwner());
    }

    @Override
    public void equipToSorcerer(Sorcerer sorcerer) {
        this.equipTo(sorcerer);
    }
}
