package model.items;

import model.map.Location;
import model.units.IUnit;
import model.units.SwordMaster;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test set for swords
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class SwordTest extends AbstractTestItem {

  private Sword sword;
  private Sword wrongSword;
  private SwordMaster swordMaster;

  /**
   * Sets which item is going to be tested
   */
  @Override
  public void setTestItem() {
    expectedName = "Common sword";
    expectedPower = 10;
    expectedMinRange = 1;
    expectedMaxRange = 1;
    sword = new Sword(expectedName, expectedPower, expectedMinRange, expectedMaxRange);
  }

  /**
   * Sets up an item with wrong ranges setted.
   */
  @Override
  public void setWrongRangeItem() {
    wrongSword = new Sword("Wrong sword", 0, -1, -2);
  }

  /**
   * Sets the unit that will be equipped with the test item
   */
  @Override
  public void setTestUnit() {
    swordMaster = new SwordMaster(50, 5, new Location(0, 0));
  }

  @Override
  public IEquipableItem getWrongTestItem() {
    return wrongSword;
  }

  /**
   * @return the item being tested
   */
  @Override
  public IEquipableItem getTestItem() {
    return sword;
  }

  /**
   * @return a unit that can equip the item being tested
   */
  @Override
  public IUnit getTestUnit() {
    return swordMaster;
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
    assertEquals(50,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveBowDamage(bow);
    assertEquals(40,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveDarknessDamage(darkness);
    assertEquals(25,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveLightDamage(light);
    assertEquals(10,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSpearDamage(spear);
    assertEquals(-5,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSpiritDamage(spirit);
    assertEquals(-20,getTestUnit().getCurrentHitPoints());

    getTestItem().receiveSwordDamage(sword);
    assertEquals(-30,getTestUnit().getCurrentHitPoints());
  }
}
