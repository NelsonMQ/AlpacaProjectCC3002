package model.items;

import model.units.*;

/**
 * This class represents a <i>spirit</i>.
 * <p>
 * Spirits are strong against lights and non-magic weapons, weak against darkness
 *
 * @author Nelson Marambio
 * @since 1.1
 */

public class Spirit extends AbstractAttackItem {

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

    public void attack(IUnit unit) {
        if(unit.getEquippedItem()==null){
            normalAttack(unit);
        }
        else {
            unit.getEquippedItem().receiveSpiritDamage(this);
        }
    }

    public void receiveAxeDamage(Axe axe) {
        strongAttack(this.getOwner());
    }

    public void receiveBowDamage(Bow bow) {
        strongAttack(this.getOwner());
    }

    public void receiveSpearDamage(Spear spear) {
        strongAttack(this.getOwner());
    }

    public void receiveSwordDamage(Sword sword) {
        strongAttack(this.getOwner());
    }

    public void receiveLightDamage(Light light) {
        weakAttack(this.getOwner());
    }

    public void receiveDarknessDamage(Darkness darkness) {
        strongAttack(this.getOwner());
    }

    public void receiveSpiritDamage(Spirit spirit) {
        normalAttack(this.getOwner());
    }

    public void equipToSorcerer(Sorcerer sorcerer) {
        this.equipTo(sorcerer);
    }

    public void equipToArcher(Archer archer){}

    public void equipToFighter(Fighter fighter){}

    public void equipToHero(Hero hero){}

    public void equipToCleric(Cleric cleric){}

    public void equipToSwordMaster(SwordMaster swordMaster){}
}
