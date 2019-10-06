package model;


import controller.GameController;
import model.items.IEquipableItem;
import model.map.Field;
import model.units.IUnit;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Tactician {
    private String name;
    private List<IUnit> units;
    private Field map;
    private IUnit selectedUnit;
    private IEquipableItem selectedItem;
    private PropertyChangeSupport changes;

    public Tactician(String name, Field map) {
        this.name = name;
        this.units = new ArrayList<>();
        this.map = map;
        this.selectedUnit = null;
        this.selectedItem = null;
        this.changes = new PropertyChangeSupport(this);
    }

    public void addObserver(GameController controller) {
        changes.addPropertyChangeListener(controller);
    }

    public String getName() {
        return name;
    }

    public List<IUnit> getUnits() {
        return units;
    }

    public IUnit getSelectedUnit() {
        return selectedUnit;
    }

    public Field getMap() {
        return map;
    }

    public IEquipableItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedUnit(IUnit unit) {
        selectedUnit = unit;
    }

    public void setSelectedItem(IEquipableItem item) {
        if(selectedUnit.getItems().contains(item))
            selectedItem = item;
    }

    public int getSelectedUnitMovement() {
        return getSelectedUnit().getMovement();
    }

    public int getSelectedUnitCurrentHitPoints() {
        return getSelectedUnit().getCurrentHitPoints();
    }

    public int getSelectedUnitMaxHP() {
        return getSelectedUnit().getMaxHP();
    }

    public int getSelectedItemPower() {
        return getSelectedItem().getPower();
    }

    public int getSelectedItemMinRange() {
        return getSelectedItem().getMinRange();
    }

    public int getSelectedItemMaxRange() {
        return getSelectedItem().getMaxRange();
    }

    public String getSelectedItemName() {
        return getSelectedItem().getName();
    }

    public void selectUnitIn (int x, int y) {
        changes.firePropertyChange("selectUnitIn",x,y);
    }
    /*
    public List<IEquipableItem> getSelectedUnitItems () {
    }
    */
    public void equipItemToSelectedUnit(int index) {
        changes.firePropertyChange("equipItemToSelectedUnit",null,index);
    }

    public void useItemOn(int x, int y) {
        changes.firePropertyChange("useItemOn",x,y);
    }

    public void selectItem(int index) {
        changes.firePropertyChange("selectItem",null,index);
    }

    public void giveItemTo(int x, int y) {
        changes.firePropertyChange("giveItemTo",x,y);
    }

    public void moveSelectedUnitTo(int x, int y) {
        changes.firePropertyChange("moveUnitTo",x,y);
    }
}
