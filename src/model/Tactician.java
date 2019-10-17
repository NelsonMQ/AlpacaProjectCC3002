package model;


import controller.handlers.*;
import model.factory.IEquipableItemFactory.*;
import model.factory.IUnitFactory.*;
import model.items.IEquipableItem;
import model.map.Field;
import model.map.Location;
import model.units.IUnit;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Tactician
 *
 * A Tactician is a user that plays the game, also it is called player.
 * With this class, the controller can controls the turn of the player in the game.
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
    private List<IUnit> unitsToMove;
    private PropertyChangeSupport refreshMap;
    private PropertyChangeSupport removeUnit;
    private PropertyChangeSupport removeTactician;
    private PropertyChangeSupport checkHeroes;
    private List<IUnit> heroes;
    private int heroesNumber;


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
        this.refreshMap = new PropertyChangeSupport(this);
        this.removeUnit = new PropertyChangeSupport(this);
        this.removeTactician = new PropertyChangeSupport(this);
        this.checkHeroes = new PropertyChangeSupport(this);
        this.unitsToMove = new ArrayList<>();
        this.heroes = new ArrayList<>();
        this.heroesNumber = 0;
    }

    /**
     * Add an observer to the tactician
     * @param removeUnitHandler
     *      The Handler that gonna be called
     */
    public void addRemoveUnitObserver(RemoveUnitHandler removeUnitHandler) {
        removeUnit.addPropertyChangeListener(removeUnitHandler);
    }

    /**
     * Add an observer to the tactician
     * @param refreshMapHandler
     *      The Handler that gonna be called
     */
    public void addRefreshMapObserver(RefreshMapHandler refreshMapHandler) {
        refreshMap.addPropertyChangeListener(refreshMapHandler);
    }

    /**
     * Add an observer to the tactician
     * @param removeTacticianHandler
     *      The Handler that gonna be called
     */
    public void addRemoveTacticianObserver(RemoveTacticianHandler removeTacticianHandler) {
        removeTactician.addPropertyChangeListener(removeTacticianHandler);
    }

    /**
     * Add an observer to the tactician
     * @param checkHeroesHandler
     *      The Handler that gonna be called
     */
    public void addCheckHeroesObserver(CheckHeroesHandler checkHeroesHandler) {
        checkHeroes.addPropertyChangeListener(checkHeroesHandler);
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
     * @return The tactician's heroes.
     */
    public List<IUnit> getHeroes() {
        return heroes;
    }

    /**
     * @return The number of heroes that the unit has.
     */
    public int getHeroesNumber() {
        return heroesNumber;
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
     * @param unitsToMove  The units to be set
     */
    public void setUnitsToMove(List<IUnit> unitsToMove) {
        this.unitsToMove = unitsToMove;
    }

    /**
     * @param map the map to be set
     */
    public void setMap(Field map) {
        this.map = map;
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
     * @return  The units that the player can move
     */
    public List<IUnit> getUnitsToMove() {
        return unitsToMove;
    }
    /**
     * Selects the unit in (x,y) location of the map
     * @param x
     *      Row
     * @param y
     *      Column
     */
    public void selectUnitIn (int x, int y) {
        if (getUnits().contains(map.getCell(x, y).getUnit())){
            setSelectedUnit(map.getCell(x, y).getUnit());
        }
    }

    /**
     * Equips the item to the selected unit
     * @param index
     *      the index of the items array
     */
    public void equipItemToSelectedUnit(int index) {
        getSelectedUnit().equipItem(getSelectedUnit().getItems().get(index));
    }

    /**
     * The selected unit will use the item on the unit in (x,y)
     * @param x
     *      Row
     * @param y
     *      Column
     */
    public void useItemOn(int x, int y) {
        IUnit unit = map.getCell(x,y).getUnit();
        getSelectedUnit().useItemOn(unit);
        if(unit.getCurrentHitPoints()<=0){
            removeUnit.firePropertyChange("removeUnit",null,unit);
        }
        if(getSelectedUnit().getCurrentHitPoints()<=0){
            removeUnit.firePropertyChange("removeUnit",null,getSelectedUnit());
        }
        checkHeroes.firePropertyChange("checkHeroes",null,1);
    }

    /**
     * Selects the item of the array of items
     * @param index
     *      The index of the items array
     */
    public void selectItem(int index) {
        setSelectedItem(selectedUnit.getItems().get(index));
    }

    /**
     * Gives the selected item to the (x,y) unit
     * @param x
     *      Row
     * @param y
     *      Column
     */
    public void giveItemTo(int x, int y) {
        IUnit unit = map.getCell(x,y).getUnit();
        IEquipableItem item = getSelectedItem();
        getSelectedUnit().giveItemTo(unit,item);
    }

    /**
     * Moves the selected unit to the (x,y) cell
     * @param x
     *      Row
     * @param y
     *      Column
     */
    public void moveSelectedUnitTo(int x, int y) {
        Location targetLocation = map.getCell(x, y);
        if(targetLocation.getUnit()==null && unitsToMove.contains(getSelectedUnit())) {
            getSelectedUnit().moveTo(targetLocation);
            unitsToMove.remove(getSelectedUnit());
            refreshMap(this.map);
        }
    }

    /**
     * Refresh the map of all players
     */
    public void refreshMap(Field map) {
        refreshMap.firePropertyChange("RefreshMap",null,map);
    }

    /**
     * Assign an Alpaca to the actual player
     * @param x
     *      horizontal position where the unit will be set
     * @param y
     *      vertical position where the unit will be set
     */
    public void assignAlpaca(int x,int y) {
        if(map.getCell(x,y).getUnit()==null) {
            IUnitFactory unitFactory = new AlpacaFactory();
            IUnit unit = unitFactory.create();
            getUnits().add(unit);
            putUnitOn(x, y, unit);
            unitsToMove.add(unit);
        }
    }

    /**
     * Assign an Archer to the actual player
     * @param x
     *      horizontal position where the unit will be set
     * @param y
     *      vertical position where the unit will be set
     */
    public void assignArcher(int x, int y) {
        if(map.getCell(x,y).getUnit()==null) {
            IUnitFactory unitFactory = new ArcherFactory();
            IUnit unit = unitFactory.create();
            getUnits().add(unit);
            putUnitOn(x, y, unit);
            unitsToMove.add(unit);
        }
    }

    /**
     * Assign a Cleric to the actual player
     * @param x
     *      horizontal position where the unit will be set
     * @param y
     *      vertical position where the unit will be set
     */
    public void assignCleric(int x, int y) {
        if(map.getCell(x,y).getUnit()==null) {
            IUnitFactory unitFactory = new ClericFactory();
            IUnit unit = unitFactory.create();
            getUnits().add(unit);
            putUnitOn(x, y, unit);
            unitsToMove.add(unit);
        }
    }

    /**
     * Assign a Fighter to the actual player
     * @param x
     *      horizontal position where the unit will be set
     * @param y
     *      vertical position where the unit will be set
     */
    public void assignFighter(int x, int y) {
        if(map.getCell(x,y).getUnit()==null) {
            IUnitFactory unitFactory = new FighterFactory();
            IUnit unit = unitFactory.create();
            getUnits().add(unit);
            putUnitOn(x, y, unit);
            unitsToMove.add(unit);
        }
    }

    /**
     * Assign a Hero to the actual player
     * @param x
     *      horizontal position where the unit will be set
     * @param y
     *      vertical position where the unit will be set
     */
    public void assignHero(int x, int y) {
        if(map.getCell(x,y).getUnit()==null) {
            IUnitFactory unitFactory = new HeroFactory();
            IUnit unit = unitFactory.create();
            getUnits().add(unit);
            putUnitOn(x, y, unit);
            unitsToMove.add(unit);
            heroes.add(unit);
            heroesNumber++;
        }
    }

    /**
     * Assign a Sorcerer to the actual player
     * @param x
     *      horizontal position where the unit will be set
     * @param y
     *      vertical position where the unit will be set
     */
    public void assignSorcerer(int x, int y) {
        if(map.getCell(x,y).getUnit()==null) {
            IUnitFactory unitFactory = new SorcererFactory();
            IUnit unit = unitFactory.create();
            getUnits().add(unit);
            putUnitOn(x, y, unit);
            unitsToMove.add(unit);
        }
    }

    /**
     * Assign a SwordMaster to the actual player
     * @param x
     *      horizontal position where the unit will be set
     * @param y
     *      vertical position where the unit will be set
     */
    public void assignSwordMaster(int x, int y) {
        if(map.getCell(x,y).getUnit()==null) {
            IUnitFactory unitFactory = new SwordMasterFactory();
            IUnit unit = unitFactory.create();
            getUnits().add(unit);
            putUnitOn(x, y, unit);
            unitsToMove.add(unit);
        }
    }

    /**
     * Adds an Axe to the selected unit
     */
    public void addAxeToSelectedUnit() {
        getSelectedUnit().addItem(new AxeFactory().create());
    }

    /**
     * Adds a Bow to the selected unit
     */
    public void addBowToSelectedUnit() {
        getSelectedUnit().addItem(new BowFactory().create());
    }

    /**
     * Adds a Darkness to the selected unit
     */
    public void addDarknessToSelectedUnit() {
        getSelectedUnit().addItem(new DarknessFactory().create());
    }

    /**
     * Adds a Light to the selected unit
     */
    public void addLightToSelectedUnit() {
        getSelectedUnit().addItem(new LightFactory().create());
    }

    /**
     * Adds a Spear to the selected unit
     */
    public void addSpearToSelectedUnit() {
        getSelectedUnit().addItem(new SpearFactory().create());
    }

    /**
     * Adds a Spirit to the selected unit
     */
    public void addSpiritToSelectedUnit() {
        getSelectedUnit().addItem(new SpiritFactory().create());
    }

    /**
     * Adds a Staff to the selected unit
     */
    public void addStaffToSelectedUnit() {
        getSelectedUnit().addItem(new StaffFactory().create());
    }

    /**
     * Adds a Sword to the selected unit
     */
    public void addSwordToSelectedUnit() {
        getSelectedUnit().addItem(new SwordFactory().create());
    }

    /**
     * Puts a unit on the (x,y) cell
     * @param x
     *      horizontal position where the unit will be set
     * @param y
     *      vertical position where the unit will be set
     * @param unit
     *      the unit
     */
    public void putUnitOn(int x, int y,IUnit unit) {
        Location cell = map.getCell(x,y);
        if(cell.getUnit()==null) {
            unit.setLocation(cell);
            cell.setUnit(unit);
            refreshMap(map);
        }
    }

    /**
     * Puts the selected unit on the (x,y) cell
     * @param x
     *      horizontal position where the unit will be set
     * @param y
     *      vertical position where the unit will be set
     */
    public void putSelectedUnitOn(int x, int y) {
        putUnitOn(x,y,getSelectedUnit());
    }
}
