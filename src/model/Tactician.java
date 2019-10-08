package model;


import controller.*;
import model.items.IEquipableItem;
import model.map.Field;
import model.units.IUnit;

import java.beans.PropertyChangeEvent;
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
    private PropertyChangeSupport selectUnit;
    private PropertyChangeSupport equipItem;
    private PropertyChangeSupport useItem;
    private PropertyChangeSupport selectItem;
    private PropertyChangeSupport giveItem;
    private PropertyChangeSupport moveUnit;

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
    }

    public void addSelectUnitObserver(selectUnitHandler selectUnitHandler) {
        selectUnit.addPropertyChangeListener(selectUnitHandler);
    }

    public void addEquipItemObserver(equipItemHandler equipItemHandler) {
        equipItem.addPropertyChangeListener(equipItemHandler);
    }

    public void addUseItemObserver(useItemHandler useItemHandler) {
        useItem.addPropertyChangeListener(useItemHandler);
    }

    public void addSelectItemObserver(selectItemHandler selectItemHandler) {
        selectItem.addPropertyChangeListener(selectItemHandler);
    }

    public void addGiveItemObserver(giveItemHandler giveItemHandler) {
        giveItem.addPropertyChangeListener(giveItemHandler);
    }

    public void addMoveUnitObserver(moveUnitHandler moveUnitHandler) {
        moveUnit.addPropertyChangeListener(moveUnitHandler);
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
        selectUnit.firePropertyChange("selectUnitIn",x,y);
    }
/*
    public void equipItemToSelectedUnit(int index) {
        equipItem.firePropertyChange("equipItemToSelectedUnit",null,index);
    }

    public void useItemOn(int x, int y) {
        useItem.firePropertyChange("useItemOn",x,y);
    }

    public void selectItem(int index) {
        selectItem.firePropertyChange("selectItem",null,index);
    }

    public void giveItemTo(int x, int y) {
        giveItem.firePropertyChange("giveItemTo",x,y);
    }

    public void moveSelectedUnitTo(int x, int y) {
        moveUnit.firePropertyChange("moveUnitTo",x,y);
    }

 */
}
