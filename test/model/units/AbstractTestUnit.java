package model.units;

import model.items.*;
import model.map.Field;
import model.map.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public abstract class AbstractTestUnit implements ITestUnit {

  protected Alpaca targetAlpaca;
  protected Bow bow;
  protected Field field;
  protected Axe axe;
  protected Sword sword;
  protected Staff staff;
  protected Spear spear;
  protected Light light;
  protected Darkness darkness;
  protected Spirit spirit;

  @Override
  public void setTargetAlpaca() {
    targetAlpaca = new Alpaca(50, 2, field.getCell(1, 0));
  }

  /**
   * Sets up the units and weapons to be tested
   */
  @BeforeEach
  public void setUp() {
    setField();
    setTestUnit();
    setTargetAlpaca();
    setWeapons();
  }

  /**
   * Set up the game field
   */
  @Override
  public void setField() {
    this.field = new Field();
    this.field.addCells(true, new Location(0, 0), new Location(0, 1), new Location(0, 2),
        new Location(1, 0), new Location(1, 1), new Location(1, 2), new Location(2, 0),
        new Location(2, 1), new Location(2, 2));
  }

  /**
   * Set up the main unit that's going to be tested in the test set
   */
  @Override
  public abstract void setTestUnit();

  /**
   * Creates a set of testing weapons
   */
  @Override
  public void setWeapons() {
    this.axe = new Axe("Axe", 10, 1, 2);
    this.sword = new Sword("Sword", 10, 1, 2);
    this.spear = new Spear("Spear", 10, 1, 2);
    this.staff = new Staff("Staff", 10, 1, 2);
    this.bow = new Bow("Bow", 10, 2, 3);
    this.light = new Light("Light",10,1,2);
    this.darkness = new Darkness("Darkness",10,1,2);
    this.spirit = new Spirit("Spirit",10,1,2);
  }

  /**
   * Checks that the constructor works properly.
   */
  @Override
  @Test
  public void constructorTest() {
    assertEquals(50, getTestUnit().getCurrentHitPoints());
    assertEquals(2, getTestUnit().getMovement());
    assertEquals(new Location(0, 0), getTestUnit().getLocation());
    assertTrue(getTestUnit().getItems().isEmpty());
  }

  /**
   * @return the current unit being tested
   */
  @Override
  public abstract IUnit getTestUnit();

  /**
   * Checks if the axe is equipped correctly to the unit
   */
  @Override
  @Test
  public void equipAxeTest() {
    assertNull(getTestUnit().getEquippedItem());
    checkEquippedItem(getAxe());
  }

  /**
   * Tries to equip a weapon to the alpaca and verifies that it was not equipped
   *
   * @param item
   *     to be equipped
   */
  @Override
  public void checkEquippedItem(IEquipableItem item) {
    assertNull(getTestUnit().getEquippedItem());
    getTestUnit().equipItem(item);
    assertNull(getTestUnit().getEquippedItem());
  }

  /**
   * @return the test axe
   */
  @Override
  public Axe getAxe() {
    return axe;
  }

  @Override
  @Test
  public void equipSwordTest() {
    checkEquippedItem(getSword());
  }

  /**
   * @return the test sword
   */
  @Override
  public Sword getSword() {
    return sword;
  }

  @Override
  @Test
  public void equipSpearTest() {
    checkEquippedItem(getSpear());
  }

  /**
   * @return the test spear
   */
  @Override
  public Spear getSpear() {
    return spear;
  }

  @Override
  @Test
  public void equipStaffTest() {
    checkEquippedItem(getStaff());
  }

  /**
   * @return the test staff
   */
  @Override
  public Staff getStaff() {
    return staff;
  }

  @Override
  @Test
  public void equipBowTest() {
    checkEquippedItem(getBow());
  }

  /**
   * @return the test bow
   */
  @Override
  public Bow getBow() {
    return bow;
  }

  @Override
  @Test
  public void equipLightTest() { checkEquippedItem(getLight());}

  /**
   * @return the test light
   */
  public Light getLight() { return light; }

  @Override
  @Test
  public void equipDarknessTest() { checkEquippedItem(getDarkness());}

  /**
   * @return the test darkness
   */
  public Darkness getDarkness() { return darkness; }

  @Override
  @Test
  public void equipSpiritTest() { checkEquippedItem(getSpirit());}

  /**
   * @return the test spirit
   */
  public Spirit getSpirit() { return spirit; }

  /**
   * Checks if the unit moves correctly
   */
  @Override
  @Test
  public void testMovement() {
    getTestUnit().moveTo(getField().getCell(2, 2));
    assertEquals(new Location(0, 0), getTestUnit().getLocation());

    getTestUnit().moveTo(getField().getCell(0, 2));
    assertEquals(new Location(0, 2), getTestUnit().getLocation());

    getField().getCell(0, 1).setUnit(getTargetAlpaca());
    getTestUnit().moveTo(getField().getCell(0, 1));
    assertEquals(new Location(0, 2), getTestUnit().getLocation());
  }

  /**
   * @return the test field
   */
  @Override
  public Field getField() {
    return field;
  }

  /**
   * @return the target Alpaca
   */
  @Override
  public Alpaca getTargetAlpaca() {
    return targetAlpaca;
  }

  @Override
  @Test
  public void setItemTest() {
    assertEquals(getTestUnit().getItems().size(),0);
    getTestUnit().addItem(axe);
    assertTrue(getTestUnit().getItems().contains(axe));

    getTestUnit().addItem(bow);
    getTestUnit().addItem(sword);
    assertEquals(getTestUnit().getItems().size(),3);

    getTestUnit().addItem(staff);
    assertFalse(getTestUnit().getItems().contains(staff));
  }

  @Override
  @Test
  public void removeItemTest() {
    getTestUnit().removeItem(staff);
    assertEquals(getTestUnit().getItems().size(),0);

    getTestUnit().addItem(axe);
    getTestUnit().addItem(sword);
    getTestUnit().removeItem(axe);
    assertEquals(getTestUnit().getItems().size(),1);
    assertFalse(getTestUnit().getItems().contains(axe));
  }

  @Override
  @Test
  public void giveItemToTest() {
    getTestUnit().addItem(axe);
    getTestUnit().giveItemTo(targetAlpaca,axe);
    assertEquals(targetAlpaca.getItems().size(),1);
    assertTrue(targetAlpaca.getItems().contains(axe));

    targetAlpaca.setLocation(field.getCell(2,2));
    targetAlpaca.giveItemTo(getTestUnit(),axe);
    assertFalse(getTestUnit().getItems().contains(axe));
  }

  @Override
  @Test
  public void canAttackTest() {
    assertFalse(getTestUnit().canAttack(targetAlpaca));
    SwordMaster swordMaster = new SwordMaster(50,2,field.getCell(2, 0));
    swordMaster.setEquippedItem(sword);
    assertTrue(swordMaster.canAttack(targetAlpaca));

    Cleric cleric = new Cleric(50,2,field.getCell(2, 0));
    cleric.setEquippedItem(staff);
    assertFalse(cleric.canAttack(targetAlpaca));
  }

  @Override
  @Test
  public void combatTest() {
    targetAlpaca.combat(getTestUnit());
    assertEquals(50,targetAlpaca.getCurrentHitPoints());
    assertEquals(50,getTestUnit().getCurrentHitPoints());
  }

  @Override
  @Test
  public void setCurrentHitPointsTest() {
    assertEquals(getTestUnit().getCurrentHitPoints(),50);
    getTestUnit().setCurrentHitPoints(30);
    assertEquals(getTestUnit().getCurrentHitPoints(),30);
  }
}
