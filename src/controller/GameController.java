package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Tactician;
import model.items.IEquipableItem;
import model.map.Field;
import model.map.Location;
import model.units.Cleric;
import model.units.IUnit;

/**
 * Controller of the game.
 * The controller manages all the input received from de game's GUI.
 *
 * @author Ignacio Slater Muñoz
 * @version 2.0
 * @since 2.0
 */
public class GameController {
  private List<Tactician> tacticians;
  private Field map;
  private Tactician actualPlayer;
  private int roundNumber;
  private int maxRounds;

  /**
   * Creates the controller for a new game.
   *
   * @param numberOfPlayers
   *     the number of players for this game
   * @param mapSize
   *     the dimensions of the map, for simplicity, all maps are squares
   */
  public GameController(int numberOfPlayers, int mapSize) {
    this.map=newMap(mapSize);
  }

  /**
   * @return the list of all the tacticians participating in the game.
   */
  public List<Tactician> getTacticians() {
    return tacticians;
  }

  /**
   * @return the map of the current game
   */
  public Field getGameMap() {
    return map;
  }

  /**
   * @return the tactician that's currently playing
   */
  public Tactician getTurnOwner() {
    return actualPlayer;
  }

  /**
   * @return the number of rounds since the start of the game.
   */
  public int getRoundNumber() {
    return roundNumber;
  }

  /**
   * @return the maximum number of rounds a match can last
   */
  public int getMaxRounds() {
    return maxRounds;
  }

  /**
   * Finishes the current player's turn.
   */
  public void endTurn() {

  }

  /**
   * Removes a tactician and all of it's units from the game.
   *
   * @param tactician
   *     the player to be removed
   */
  public void removeTactician(String tactician) {
    for(int i = 0; i<tacticians.size();i++){
      if(tacticians.get(i).getName()==tactician) {
        List<IUnit> units = tacticians.get(i).getUnits();
        for(int j = 0; j<units.size();j++){
          units.get(j).getLocation().setUnit(null);
        }
        tacticians.remove(i);
      }
    }
  }

  /**
   * Starts the game.
   * @param maxTurns
   *  the maximum number of turns the game can last
   */
  public void initGame(final int maxTurns) {

  }

  /**
   * Starts a game without a limit of turns.
   */
  public void initEndlessGame() {
    initGame(-1);
  }

  /**
   * @return the winner of this game, if the match ends in a draw returns a list of all the winners
   */
  public List<String> getWinners() {
    return null;
  }

  /**
   * @return the current player's selected unit
   */
  public IUnit getSelectedUnit() {
    return actualPlayer.getSelectedUnit();
  }

  /**
   * Selects a unit in the game map
   *
   * @param x
   *     horizontal position of the unit
   * @param y
   *     vertical position of the unit
   */
  public void selectUnitIn(int x, int y) {
    actualPlayer.setSelectedUnit(map.getCell(x,y).getUnit());
  }

  /**
   * @return the inventory of the currently selected unit.
   */
  public List<IEquipableItem> getItems() {
    return getSelectedUnit().getItems();
  }

  /**
   * Equips an item from the inventory to the currently selected unit.
   *
   * @param index
   *     the location of the item in the inventory.
   */
  public void equipItem(int index) {
    getSelectedUnit().equipItem(getItems().get(index));
  }

  /**
   * Uses the equipped item on a target
   *
   * @param x
   *     horizontal position of the target
   * @param y
   *     vertical position of the target
   */
  public void useItemOn(int x, int y) {
    IUnit unit = map.getCell(x,y).getUnit();
    getSelectedUnit().useItemOn(unit);
  }

  /**
   * Selects an item from the selected unit's inventory.
   *
   * @param index
   *     the location of the item in the inventory.
   */
  public void selectItem(int index) {
    IEquipableItem item = actualPlayer.getSelectedUnit().getItems().get(index);
    actualPlayer.setSelectedItem(item);
  }

  /**
   * Gives the selected item to a target unit.
   *
   * @param x
   *     horizontal position of the target
   * @param y
   *     vertical position of the target
   */
  public void giveItemTo(int x, int y) {
    IUnit unit = map.getCell(x,y).getUnit();
    IEquipableItem item = actualPlayer.getSelectedItem();
    actualPlayer.getSelectedUnit().giveItemTo(unit,item);
  }

  public int[][] newMatrixMap(int size) {
    Random random = new Random();
    int[][] matrix = new int[size][size];
    int n = 0;
    for(int i=0;i<size;i++){
      for(int j=0;j<size;j++) {
        int k = random.nextInt(10);
        if (k < 6) {
          matrix[i][j] = 1;
          n += 1;
        }
        else
          matrix[i][j]=0;
      }
    }
    return matrix;
  }

  public int locationQuantity(int[][] matrix){
    int n = 0;
    for(int i=0;i<matrix[0].length;i++){
      for(int j=0;j<matrix[0].length;j++){
        if(matrix[i][j]==1){
          n+=1;
        }
      }
    }
    return n;
  }

  public Field matrixToMap(int[][] matrix) {
    int n = locationQuantity(matrix);
    Location[] locs = new Location[n];
    int k = 0;
    for(int i=0;i<matrix[0].length;i++){
      for(int j=0;j<matrix[0].length;j++){
        if(matrix[i][j]==1){
          locs[k] = new Location(i,j);
          k++;
        }
      }
    }
    Field map = new Field();
    map.addCells(true,locs);
    return map;
  }

  public Field newMap(int size) {
    Field map = matrixToMap(newMatrixMap(size));
    while(!map.isConnected()){
      map = matrixToMap(newMatrixMap(size));
    }
    return map;
  }
}
