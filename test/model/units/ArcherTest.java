package model.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import model.items.Light;
import org.junit.jupiter.api.Test;

/**
 * Test set for the Archer unit.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class ArcherTest extends AbstractTestUnit {

  private Archer archer;

  /**
   * Set up the main unit that's going to be tested in the test set
   */
  @Override
  public void setTestUnit() {
    archer = new Archer(50, 2, field.getCell(0, 0));
  }

  /**
   * @return the current unit being tested
   */
  @Override
  public IUnit getTestUnit() {
    return archer;
  }

  /**
   * Checks if the bow is equipped correctly to the unit
   */
  @Test
  @Override
  public void equipBowTest() {
    assertNull(archer.getEquippedItem());
    archer.equipItem(bow);
    assertEquals(bow, archer.getEquippedItem());
  }

  @Override
  @Test
  public void combatTest() {
    Sorcerer sorcerer = new Sorcerer(50,2,field.getCell(1, 0));
    archer.equipItem(bow);
    archer.combat(sorcerer);
    assertEquals(50,sorcerer.getCurrentHitPoints());

    sorcerer.setLocation(field.getCell(2, 0));
    archer.combat(sorcerer);
    assertEquals(40,sorcerer.getCurrentHitPoints());

    sorcerer.equipItem(light);
    archer.combat(sorcerer);
    assertEquals(25,sorcerer.getCurrentHitPoints());
    assertEquals(35,archer.getCurrentHitPoints());
  }
}