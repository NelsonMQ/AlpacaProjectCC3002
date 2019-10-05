package model.factory.GameMapFactory;

import model.map.Field;
import model.map.Location;

import java.util.Random;

public class FieldFactory {
    private Random randomSeed = null;

    public Field create(int size) {
        return newMap(size);
    }

    public Field newMap(int size) {
        Field map = matrixToMap(newMatrixMap(size));
        while(!map.isConnected()){
            map = matrixToMap(newMatrixMap(size));
        }
        return map;
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
        map.setSize(matrix[0].length);
        return map;
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

    public int[][] newMatrixMap(int size) {
        Random random;
        if(randomSeed != null) {
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

    public void setRandomSeed(Random randomSeed) {
        this.randomSeed = randomSeed;
    }

}
