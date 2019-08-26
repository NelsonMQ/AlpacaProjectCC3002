package model.items;

import model.map.Location;
import model.units.IUnit;
import model.units.Sorcerer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test set for Light
 *
 * @author Nelson Marambio
 * @since 1.1
 */
public class LightTest extends AbstractTestItem {
    private Light light;
    private Light wrongLight;
    private Sorcerer sorcerer;

    @Override
    public void setTestItem() {
        expectedName = "Common light";
        expectedPower = 10;
        expectedMinRange = 1;
        expectedMaxRange = 2;
        light = new Light(expectedName, expectedPower, expectedMinRange, expectedMaxRange);
    }

    /**
     * Sets up an item with wrong ranges set.
     */
    @Override
    public void setWrongRangeItem() {
        wrongLight = new Light("Wrong light", 0, -1, -2);
    }

    /**
     * Sets the unit that will be equipped with the test item
     */
    @Override
    public void setTestUnit() {
        sorcerer = new Sorcerer(50, 5, new Location(0, 0));
    }

    @Override
    public IEquipableItem getWrongTestItem() {
        return wrongLight;
    }

    @Override
    public IEquipableItem getTestItem() {
        return light;
    }

    /**
     * @return a unit that can equip the item being tested
     */
    @Override
    public IUnit getTestUnit() {
        return sorcerer;
    }

    /**
     * Checks if receive"Item"Damage works correctly
     */
    @Test
    public void receiveDamageTest() {
        Axe axe = new Axe("Axe", 10, 1, 2);
        Sword sword = new Sword("Sword", 10, 1, 2);
        Spear spear = new Spear("Spear", 10, 1, 2);
        Bow bow = new Bow("Bow", 10, 2, 3);
        Light light = new Light("Light", 10, 1, 2);
        Darkness darkness = new Darkness("Darkness", 10, 1, 2);
        Spirit spirit = new Spirit("Spirit", 10, 1, 2);

        getTestItem().equipTo(getTestUnit());
        getTestItem().receiveAxeDamage(axe);
        assertEquals(35,getTestUnit().getCurrentHitPoints());

        getTestItem().receiveBowDamage(bow);
        assertEquals(20,getTestUnit().getCurrentHitPoints());

        getTestItem().receiveDarknessDamage(darkness);
        assertEquals(20,getTestUnit().getCurrentHitPoints());

        getTestItem().receiveLightDamage(light);
        assertEquals(10,getTestUnit().getCurrentHitPoints());

        getTestItem().receiveSpearDamage(spear);
        assertEquals(-5,getTestUnit().getCurrentHitPoints());

        getTestItem().receiveSpiritDamage(spirit);
        assertEquals(-20,getTestUnit().getCurrentHitPoints());

        getTestItem().receiveSwordDamage(sword);
        assertEquals(-35,getTestUnit().getCurrentHitPoints());
    }
}
