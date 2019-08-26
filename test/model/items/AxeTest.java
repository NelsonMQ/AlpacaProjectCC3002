package model.items;

import model.map.Location;
import model.units.Fighter;
import model.units.IUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test set for Axes
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
class AxeTest extends AbstractTestItem {

  private Axe axe;
  private Axe wrongAxe;
  private Fighter fighter;

  @Override
  public void setTestItem() {
    expectedName = "Common axe";
    expectedPower = 10;
    expectedMinRange = 1;
    expectedMaxRange = 2;
    axe = new Axe(expectedName, expectedPower, expectedMinRange, expectedMaxRange);
  }

  /**
   * Sets up an item with wrong ranges set.
   */
  @Override
  public void setWrongRangeItem() {
    wrongAxe = new Axe("Wrong axe", 0, -1, -2);
  }

  /**
   * Sets the unit that will be equipped with the test item
   */
  @Override
  public void setTestUnit() {
    fighter = new Fighter(50, 5, new Location(0, 0));
  }

  @Override
  public IEquipableItem getWrongTestItem() {
    return wrongAxe;
  }

  @Override
  public IEquipableItem getTestItem() {
    return axe;
  }

  /**
   * @return a unit that can equip the item being tested
   */
  @Override
  public IUnit getTestUnit() {
    return fighter;
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
    assertEquals(0,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSpiritDamage(spirit);
    assertEquals(-15,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSwordDamage(sword);
    assertEquals(-30,getTestUnit().getCurrentHitPoints());
  }

}