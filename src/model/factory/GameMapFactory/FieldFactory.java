package model.factory.GameMapFactory;

import model.map.Field;
import model.map.Location;

import java.util.Random;

/**
 * This class allows to create a random map. Thus, the controller don't have to worry about the creation of the map.
 * @author Nelson Marambio
 * @since 2.1
 */
public class FieldFactory {
    private Random randomSeed = null;
    private Random randomSeed2 = null;

    /**
     * Creates a new map
     * @param size
     *      The size of the map
     * @return
     *      The map
     */
    public Field create(int size) {
        return newMap(size,randomSeed);
    }

    /**
     * Creates a new map
     * @param size
     *      The size of the map
     * @return
     *      The map
     */
    public Field newMap(int size, Random seed) {
        Field map = matrixToMap(newMatrixMap(size,seed));
        while(!map.isConnected()){
            map = matrixToMap(newMatrixMap(size,seed));
        }
        return map;
    }

    /**
     * Converts a matrix (ones and zeros) in a map
     * @param matrix
     *      The matrix to be converted
     * @return
     *      The map
     */
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
        map.setRandom(randomSeed2);
        map.addCells(false,locs);
        map.setSize(matrix[0].length);
        return map;
    }

    /**
     * Returns the quantity of 'ones' in the matrix
     * @param matrix
     *      The matrix
     * @return
     *      The quantity of 'ones' in the matrix
     */
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

    /**
     * Creates a random matrix (ones and zeros)
     * @param size
     *      The size of the matrix (size x size)
     * @return
     *      The matrix
     */
    public int[][] newMatrixMap(int size,Random seed) {
        Random random;
        if(seed != null) {
            random = randomSeed;
        }
        else
            random = new Random();
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

    /**
     * Set a random seed to create the matrix
     * @param randomSeed
     *      The random seed
     */
    public void setRandomSeed(Random randomSeed) {
        this.randomSeed = randomSeed;
    }

    /**
     * Set a random seed to create the connections of the map
     * @param randomSeed2
     *      The random seed
     */
    public void setRandomSeed2(Random randomSeed2) {
        this.randomSeed2 = randomSeed2;
    }

}
