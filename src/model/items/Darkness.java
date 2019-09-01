package model.items;

import model.units.*;

/**
 * This class represents a <i>darkness</i>.
 * <p>
 * Darkness are strong against spirits and non-magic weapons ,and weak against lights
 *
 * @author Nelson Marambio
 * @since 1.1
 */

public class Darkness extends AbstractAttackItem {

    /**
     * Creates a new Darkness item
     *
     * @param name     the name of the Darkness
     * @param power    the damage of the darkness
     * @param minRange the minimum range of the darkness
     * @param maxRange the maximum range of the darkness
     */
    public Darkness(final String name, final int power, final int minRange, final int maxRange) {
        super(name, power, minRange, maxRange);
    }

    public void attack(IUnit unit) {
        if(unit.getEquippedItem()==null){
            normalAttack(unit);
        }
        else {
            unit.getEquippedItem().receiveDarknessDamage(this);
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
        strongAttack(this.getOwner());
    }

    @Override
    public void receiveDarknessDamage(Darkness darkness) {
        normalAttack(this.getOwner());
    }

    @Override
    public void receiveSpiritDamage(Spirit spirit) {
        weakAttack(this.getOwner());
    }

    @Override
    public void equipToSorcerer(Sorcerer sorcerer) {
        this.equipTo(sorcerer);
    }
}
