package model;


import controller.handlers.*;
import model.items.IEquipableItem;
import model.map.Field;
import model.units.IUnit;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Tactician
 *
 * A Tactician is a user that plays the game, also it is called player.
 * With this class, the player can control its turn in the game.
 *
 * @author Nelson Marambio
 * @since 2.1
 */

public class Tactician {
    private String name;
    private List<IUnit> units;
    private Field map;
    private IUnit selectedUnit;
    private IEquipableItem selectedItem;
    private PropertyChangeSupport selectUnit;
    private PropertyChangeSupport equipItem;
    private PropertyChangeSupport useItem;
    private PropertyChangeSupport selectItem;
    private PropertyChangeSupport giveItem;
    private PropertyChangeSupport moveUnit;
    private PropertyChangeSupport endTurn;

    /**
     * Create a new Tactician
     * @param name
     *      The name of the player
     * @param map
     *      The map of the game
     */
    public Tactician(String name, Field map) {
        this.name = name;
        this.units = new ArrayList<>();
        this.map = map;
        this.selectedUnit = null;
        this.selectedItem = null;
        this.selectUnit = new PropertyChangeSupport(this);
        this.equipItem = new PropertyChangeSupport(this);
        this.useItem = new PropertyChangeSupport(this);
        this.selectItem = new PropertyChangeSupport(this);
        this.giveItem = new PropertyChangeSupport(this);
        this.moveUnit = new PropertyChangeSupport(this);
        this.endTurn = new PropertyChangeSupport(this);
    }

    /**
     * Add an observer to the tactician
     * @param selectUnitHandler
     *      The Handler that gonna be called
     */
    public void addSelectUnitObserver(SelectUnitHandler selectUnitHandler) {
        selectUnit.addPropertyChangeListener(selectUnitHandler);
    }

    /**
     * Add an observer to the tactician
     * @param equipItemHandler
     *      The Handler that gonna be called
     */
    public void addEquipItemObserver(EquipItemHandler equipItemHandler) {
        equipItem.addPropertyChangeListener(equipItemHandler);
    }

    /**
     * Add an observer to the tactician
     * @param useItemHandler
     *      The Handler that gonna be called
     */
    public void addUseItemObserver(UseItemHandler useItemHandler) {
        useItem.addPropertyChangeListener(useItemHandler);
    }

    /**
     * Add an observer to the tactician
     * @param selectItemHandler
     *      The Handler that gonna be called
     */
    public void addSelectItemObserver(SelectItemHandler selectItemHandler) {
        selectItem.addPropertyChangeListener(selectItemHandler);
    }

    /**
     * Add an observer to the tactician
     * @param giveItemHandler
     *      The Handler that gonna be called
     */
    public void addGiveItemObserver(GiveItemHandler giveItemHandler) {
        giveItem.addPropertyChangeListener(giveItemHandler);
    }

    /**
     * Add an observer to the tactician
     * @param moveUnitHandler
     *      The Handler that gonna be called
     */
    public void addMoveUnitObserver(MoveUnitHandler moveUnitHandler) {
        moveUnit.addPropertyChangeListener(moveUnitHandler);
    }

    /**
     * Add an observer to the tactician
     * @param endTurnHandler
     *      The Handler that gonna be called
     */
    public void addEndTurnObserver(EndTurnHandler endTurnHandler){
        endTurn.addPropertyChangeListener(endTurnHandler);
    }

    /**
     * Returns the name of the player
     * @return
     *      The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the units of the player
     * @return
     *      The units of the player
     */
    public List<IUnit> getUnits() {
        return units;
    }

    /**
     * Returns the selected unit
     * @return
     *      The selected unit
     */
    public IUnit getSelectedUnit() {
        return selectedUnit;
    }

    /**
     * Returns the map of the game
     * @return
     *      The map of the game
     */
    public Field getMap() {
        return map;
    }

    /**
     * Returns the selected item
     * @return
     *      The selected item
     */
    public IEquipableItem getSelectedItem() {
        return selectedItem;
    }

    /**
     * Set the selected unit
     * @param unit
     *      The unit to be selected
     */
    public void setSelectedUnit(IUnit unit) {
        selectedUnit = unit;
    }

    /**
     * Set the selected item
     * @param item
     *      The item to be selected
     */
    public void setSelectedItem(IEquipableItem item) {
        if(selectedUnit.getItems().contains(item))
            selectedItem = item;
    }

    /**
     * Returns the number of cells the unit can move
     * @return
     *      The number of cells the unit can move
     */
    public int getSelectedUnitMovement() {
        return getSelectedUnit().getMovement();
    }

    /**
     * Returns the current hit points of the unit
     * @return
     *      The current hit points of the unit
     */
    public int getSelectedUnitCurrentHitPoints() {
        return getSelectedUnit().getCurrentHitPoints();
    }

    /**
     * Returns the max HP of the unit
     * @return
     *      The max HP of the unit
     */
    public int getSelectedUnitMaxHP() {
        return getSelectedUnit().getMaxHP();
    }

    /**
     * Returns the power of the selected item
     * @return
     *      The power of the selected item
     */
    public int getSelectedItemPower() {
        return getSelectedItem().getPower();
    }

    /**
     * Returns the min range of the selected item
     * @return
     *      The min range of the selected item
     */
    public int getSelectedItemMinRange() {
        return getSelectedItem().getMinRange();
    }

    /**
     * Returns the max range of the selected item
     * @return
     *      The max range of the selected item
     */
    public int getSelectedItemMaxRange() {
        return getSelectedItem().getMaxRange();
    }

    /**
     * Returns the name of the selected item
     * @return
     *      The name of the selected item
     */
    public String getSelectedItemName() {
        return getSelectedItem().getName();
    }

    /**
     * Selects the unit in (x,y) location of the map
     * @param x
     *      Row
     * @param y
     *      Column
     */
    public void selectUnitIn (int x, int y) {
        if(x==y)
            x = -1;
        selectUnit.firePropertyChange("selectUnitIn",x,y);
    }

    /**
     * Equips the item to the selected unit
     * @param index
     *      the index of the items array
     */
    public void equipItemToSelectedUnit(int index) {
        equipItem.firePropertyChange("equipItemToSelectedUnit",null,index);
    }

    /**
     * The selected unit will use the item on the unit in (x,y)
     * @param x
     *      Row
     * @param y
     *      Column
     */
    public void useItemOn(int x, int y) {
        if(x==y)
            x = -1;
        useItem.firePropertyChange("useItemOn",x,y);
    }

    /**
     * Selects the item of the array of items
     * @param index
     *      The index of the items array
     */
    public void selectItem(int index) {
        selectItem.firePropertyChange("selectItem",null,index);
    }

    /**
     * Gives the selected item to the (x,y) unit
     * @param x
     *      Row
     * @param y
     *      Column
     */
    public void giveItemTo(int x, int y) {
        if(x==y)
            x = -1;
        giveItem.firePropertyChange("giveItemTo",x,y);
    }

    /**
     * Moves the selected unit to the (x,y) cell
     * @param x
     *      Row
     * @param y
     *      Column
     */
    public void moveSelectedUnitTo(int x, int y) {
        if(x==y)
            x = -1;
        moveUnit.firePropertyChange("moveUnitTo",x,y);
    }

    /**
     * Ends the turn of the player
     */
    public void endTurn() {
        endTurn.firePropertyChange("endTurn",null,0);
    }


}
