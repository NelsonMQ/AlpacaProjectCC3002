package model.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import model.items.Darkness;
import org.junit.jupiter.api.Test;

/**
 * Test set for the Sorcerer unit.
 *
 * @author Nelson Marambio
 * @since 1.1
 */
public class SorcererTest extends AbstractTestUnit {

    private Sorcerer sorcerer;

    /**
     * Set up the main unit that's going to be tested in the test set
     */
    @Override
    public void setTestUnit() {
        sorcerer = new Sorcerer(50, 2, field.getCell(0, 0));
    }

    /**
     * @return the current unit being tested
     */
    @Override
    public IUnit getTestUnit() {
        return sorcerer;
    }

    /**
     * Checks if the light is equipped correctly to the unit
     */
    @Test
    @Override
    public void equipLightTest() {
        assertNull(sorcerer.getEquippedItem());
        sorcerer.equipItem(light);
        assertEquals(light, sorcerer.getEquippedItem());
    }

    /**
     * Checks if the darkness is equipped correctly to the unit
     */
    @Test
    @Override
    public void equipDarknessTest() {
        assertNull(sorcerer.getEquippedItem());
        sorcerer.equipItem(darkness);
        assertEquals(darkness, sorcerer.getEquippedItem());
    }

    /**
     * Checks if the spirit is equipped correctly to the unit
     */
    @Test
    @Override
    public void equipSpiritTest() {
        assertNull(sorcerer.getEquippedItem());
        sorcerer.equipItem(spirit);
        assertEquals(spirit, sorcerer.getEquippedItem());
    }

    @Override
    @Test
    public void attackToTest() {
        Archer archer = new Archer(50,2,field.getCell(1, 0));
        sorcerer.setEquippedItem(darkness);
        sorcerer.attackTo(archer);
        assertEquals(archer.getCurrentHitPoints(),40);

        archer.setEquippedItem(bow);
        sorcerer.attackTo(archer);
        assertEquals(archer.getCurrentHitPoints(),25);
    }
}

