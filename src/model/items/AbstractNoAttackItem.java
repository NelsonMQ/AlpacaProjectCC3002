package model.items;

/**
 * Class that defines some common information and behaviour between some items.
 *
 * @author Nelson Marambio
 * @since 1.1
 */
public abstract class AbstractNoAttackItem extends AbstractItem implements INoAttackItem{

    /**
     * Constructor for a default no attack item without any special behaviour.
     *
     * @param name
     *     the name of the item
     * @param power
     *     the power of the item
     * @param minRange
     *     the minimum range of the item
     * @param maxRange
     *     the maximum range of the item
     */
    public AbstractNoAttackItem(final String name, final int power, final int minRange, final int maxRange) {
        super(name, power, minRange, maxRange);
    }

    public boolean canAttack() {
        return false;
    }
}


