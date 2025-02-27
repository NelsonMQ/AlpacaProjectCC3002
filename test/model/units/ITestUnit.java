package model.units;


import model.items.*;
import model.map.Field;
import org.junit.jupiter.api.Test;

/**
 * Interface that defines the common behaviour of all the test for the units classes
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public interface ITestUnit {

  /**
   * Set up the game field
   */
  void setField();

  /**
   * Set up the main unit that's going to be tested in the test set
   */
  void setTestUnit();

  void setTargetAlpaca();

  /**
   * Creates a set of testing weapons
   */
  void setWeapons();

  /**
   * Checks that the constructor works properly.
   */
  @Test
  void constructorTest();

  /**
   * @return the current unit being tested
   */
  IUnit getTestUnit();

  /**
   * Checks if the axe is equipped correctly to the unit
   */
  @Test
  void equipAxeTest();

  /**
   * Tries to equip a weapon to the alpaca and verifies that it was not equipped
   *
   * @param item
   *     to be equipped
   */
  void checkEquippedItem(IEquipableItem item);

  /**
   * @return the test axe
   */
  Axe getAxe();

  @Test
  void equipSwordTest();

  /**
   * @return the test sword
   */
  Sword getSword();

  @Test
  void equipSpearTest();

  /**
   * @return the test spear
   */
  Spear getSpear();

  @Test
  void equipStaffTest();

  /**
   * @return the test staff
   */
  Staff getStaff();

  @Test
  void equipBowTest();

  /**
   * @return the test bow
   */
  Bow getBow();

  @Test
  void equipLightTest();

  /**
   * @return the test light
   */
  Light getLight();

  @Test
  void equipDarknessTest();

  /**
   * @return the test darkness
   */
  Darkness getDarkness();

  @Test
  void equipSpiritTest();

  /**
   * @return the test spirit
   */
  Spirit getSpirit();

  /**
   * Checks if the unit moves correctly
   */
  @Test
  void testMovement();

  /**
   * @return the test field
   */
  Field getField();

  /**
   * @return the target Alpaca
   */
  Alpaca getTargetAlpaca();

  /**
   * Checks if the item is setted correctly
   */
  @Test
  void setItemTest();

  /**
   * Checks if the item is removed correctly
   */
  @Test
  void removeItemTest();

  /**
   * Checks if the item is given correctly
   */
  @Test
  void giveItemToTest();

  /**
   * Checks if the unit can attack
   */
  @Test
  void canAttackTest();

  /**
   * Checks if the unit attacks correctly and checks the combat works correctly
   */
  @Test
  void combatTest();

  /**
   * Checks if the hit points are set correctly
   */
  @Test
  void setCurrentHitPointsTest();
}

