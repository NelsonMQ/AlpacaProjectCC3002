package model.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * @author Ignacio Slater Muñoz
 */
public class HeroTest extends AbstractTestUnit {

  private Hero hero;

  /**
   * Set up the main unit that's going to be tested in the test set
   */
  @Override
  public void setTestUnit() {
    hero = new Hero(50, 2, field.getCell(0, 0));
  }

  /**
   * @return the current unit being tested
   */
  @Override
  public IUnit getTestUnit() {
    return hero;
  }

  @Override
  @Test
  public void equipSpearTest() {
    assertNull(hero.getEquippedItem());
    hero.equipItem(spear);
    assertEquals(spear, hero.getEquippedItem());
  }

  @Override
  @Test
  public void combatTest() {
    Fighter fighter = new Fighter(50,2,field.getCell(1, 0));
    hero.equipItem(spear);
    hero.combat(fighter);
    assertEquals(40,fighter.getCurrentHitPoints());

    fighter.equipItem(axe);
    hero.combat(fighter);
    assertEquals(40,fighter.getCurrentHitPoints());
    assertEquals(35,hero.getCurrentHitPoints());
  }
}