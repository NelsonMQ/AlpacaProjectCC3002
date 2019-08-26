package model.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.map.Location;
import model.units.Archer;
import model.units.IUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test set for bows
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class BowTest extends AbstractTestItem {

  private Bow bow;
  private Bow wrongBow;
  private Archer archer;

  /**
   * Sets which item is going to be tested
   */
  @Override
  public void setTestItem() {
    expectedName = "Common bow";
    expectedPower = 10;
    expectedMinRange = 2;
    expectedMaxRange = 4;
    bow = new Bow(expectedName, expectedPower, expectedMinRange, expectedMaxRange);
  }

  /**
   * Sets up an item with wrong ranges setted.
   */
  @Override
  public void setWrongRangeItem() {
    wrongBow = new Bow("Wrong bow", 10, 1, 1);
  }

  /**
   * Sets the unit that will be equipped with the test item
   */
  @BeforeEach
  @Override
  public void setTestUnit() {
    archer = new Archer(50, 5, new Location(0, 0));
  }

  /**
   * Checks that the minimum range for a bow is greater than 1
   */
  @Override
  @Test
  public void incorrectRangeTest() {
    assertTrue(getWrongTestItem().getMinRange() > 1);
    assertTrue(getWrongTestItem().getMaxRange() >= getWrongTestItem().getMinRange());
  }

  @Override
  public IEquipableItem getWrongTestItem() {
    return wrongBow;
  }

  /**
   * @return the item being tested
   */
  @Override
  public IEquipableItem getTestItem() {
    return bow;
  }

  /**
   * @return a unit that can equip the item being tested
   */
  @Override
  public IUnit getTestUnit() {
    return archer;
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
    assertEquals(40,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveBowDamage(bow);
    assertEquals(30,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveDarknessDamage(darkness);
    assertEquals(15,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveLightDamage(light);
    assertEquals(0,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSpearDamage(spear);
    assertEquals(-10,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSpiritDamage(spirit);
    assertEquals(-25,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSwordDamage(sword);
    assertEquals(-35,getTestUnit().getCurrentHitPoints());
  }
}
