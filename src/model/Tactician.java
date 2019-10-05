package model;


import model.items.IEquipableItem;
import model.map.Field;
import model.units.IUnit;

import java.util.ArrayList;
import java.util.List;

public class Tactician {
    private String name;
    private List<IUnit> units;
    private Field map;
    private IUnit selectedUnit;
    private IEquipableItem selectedItem;

    public Tactician(String name, Field map) {
        this.name = name;
        this.units = new ArrayList<>();
        this.map = map;
        this.selectedUnit = null;
        this.selectedItem = null;
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
}
