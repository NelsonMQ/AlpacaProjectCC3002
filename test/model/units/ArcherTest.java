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
  public void attackToTest() {
    Sorcerer sorcerer = new Sorcerer(50,2,field.getCell(1, 0));
    archer.setEquippedItem(bow);
    archer.attackTo(sorcerer);
    assertEquals(sorcerer.getCurrentHitPoints(),50);

    sorcerer.setLocation(field.getCell(2, 0));
    archer.attackTo(sorcerer);
    assertEquals(sorcerer.getCurrentHitPoints(),40);

    sorcerer.setEquippedItem(light);
    archer.attackTo(sorcerer);
    assertEquals(sorcerer.getCurrentHitPoints(),25);
  }
}