package model.items;

/**
 * This class represents a <i>Staff</i> type item.
 * <p>
 * A staff is an item that can heal other units nut cannot counter any attack
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class Staff extends AbstractNoAttackItem {

  /**
   * Creates a new Staff item.
   *
   * @param name
   *     the name of the staff
   * @param power
   *     the healing power of the staff
   * @param minRange
   *     the minimum range of the staff
   * @param maxRange
   *     the maximum range of the staff
   */
  public Staff(final String name, final int power, final int minRange, final int maxRange) {
    super(name, power, minRange, maxRange);
  }

  public void receiveAxeDamage(Axe axe) {
    axe.normalAttack(this.getOwner());
  }

  public void receiveBowDamage(Bow bow) {
    bow.normalAttack(this.getOwner());
  }

  public void receiveSpearDamage(Spear spear) { spear.normalAttack(this.getOwner()); }

  public void receiveSwordDamage(Sword sword) {
    sword.normalAttack(this.getOwner());
  }

  public void receiveLightDamage(Light light) {
    light.strongAttack(this.getOwner());
  }

  public void receiveDarknessDamage(Darkness darkness) {
    darkness.strongAttack(this.getOwner());
  }

  public void receiveSpiritDamage(Spirit spirit) {
    spirit.strongAttack(this.getOwner());
  }
}
