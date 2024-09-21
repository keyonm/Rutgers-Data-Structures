package tides;

import java.util.*;

/**
 * This class contains methods that provide information about select terrains 
 * using 2D arrays. Uses floodfill to flood given maps and uses that 
 * information to understand the potential impacts. 
 * Instance Variables:
 *  - a double array for all the heights for each cell
 *  - a GridLocation array for the sources of water on empty terrain 
 * 
 * @author Original Creator Keith Scharz (NIFTY STANFORD) 
 * @author Vian Miranda (Rutgers University)
 */
public class RisingTides {

    // Instance variables
    private double[][] terrain;     // an array for all the heights for each cell
    private GridLocation[] sources; // an array for the sources of water on empty terrain 

    /**
     * DO NOT EDIT!
     * Constructor for RisingTides.
     * @param terrain passes in the selected terrain 
     */
    public RisingTides(Terrain terrain) {
        this.terrain = terrain.heights;
        this.sources = terrain.sources;
    }

    /**
     * Find the lowest and highest point of the terrain and output it.
     * 
     * @return double[][], with index 0 and index 1 being the lowest and 
     * highest points of the terrain, respectively
     */
    public double[] elevationExtrema() {
        double[] a = new double[2];
        a[0] = Double.MAX_VALUE;
        a[1] = Double.MIN_VALUE;

        for(int i = 0; i < terrain.length; i++) {
            for(int j = 0; j < terrain[i].length; j++) {
                if(terrain[i][j] < a[0]) {
                    a[0] = terrain[i][j];
                }

                if(terrain[i][j] > a[1]) {
                    a[1] = terrain[i][j];
                }
            }
        }

        return a;
    }

    /**
     * Implement the floodfill algorithm using the provided terrain and sources.
     * 
     * All water originates from the source GridLocation. If the height of the 
     * water is greater than that of the neighboring terrain, flood the cells. 
     * Repeat iteratively till the neighboring terrain is higher than the water 
     * height.
     * 
     * 
     * @param height of the water
     * @return boolean[][], where flooded cells are true, otherwise false
     */
    public boolean[][] floodedRegionsIn(double height) {
        boolean[][] result = new boolean[terrain.length][terrain[0].length];
        ArrayList<GridLocation> list = new ArrayList<>();

        for(int i = 0; i < sources.length; i++) {
            if (sources[i] != null) {
                list.add(sources[i]);
                result[sources[i].row][sources[i].col] = true;
            }
        }


        int row;
        int col;
        int up;
        int down;
        int left;
        int right;

        while (!list.isEmpty()) {
            GridLocation test = list.remove(0);
            row = test.row;
            col = test.col;
            up = row - 1;
            down = row + 1;
            left = col - 1;
            right = col + 1;


            if (up != -1 && terrain[up][col] <= height && !result[up][col]) {
                result[up][col] = true;
                list.add(new GridLocation(up,col));
            }

            if (down < terrain.length && terrain[down][col] <= height && !result[down][col]) {
                result[down][col] = true;
                list.add(new GridLocation(down, col));
            }

            if (left != -1 && terrain[row][left] <= height && !result[row][left]) {
                result[row][left] = true;
                list.add(new GridLocation(row, left));
            }

            if (right < terrain[0].length && terrain[row][right] <= height && !result[row][right]) {
                result[row][right] = true;
                list.add(new GridLocation(row, right));
            }
        }

        return result;
    }

    /**
     * Checks if a given cell is flooded at a certain water height.
     * 
     * @param height of the water
     * @param cell location 
     * @return boolean, true if cell is flooded, otherwise false
     */
    public boolean isFlooded(double height, GridLocation cell) {
        return (floodedRegionsIn(height))[cell.row][cell.col];
    }

    /**
     * Given the water height and a GridLocation find the difference between 
     * the chosen cells height and the water height.
     * 
     * If the return value is negative, the Driver will display "meters below"
     * If the return value is positive, the Driver will display "meters above"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param cell location
     * @return double, representing how high/deep a cell is above/below water
     */
    public double heightAboveWater(double height, GridLocation cell) {
        return terrain[cell.row][cell.col] - height;
    }

    /**
     * Total land available (not underwater) given a certain water height.
     * 
     * @param height of the water
     * @return int, representing every cell above water
     */
    public int totalVisibleLand(double height) {
        boolean[][] arr = floodedRegionsIn(height);
        int count = 0;

        for(int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (!arr[i][j]){
                    count++;
                }
            }
        }

        return count;
    } 


    /**
     * Given 2 heights, find the difference in land available at each height. 
     * 
     * If the return value is negative, the Driver will display "Will gain"
     * If the return value is positive, the Driver will display "Will lose"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param newHeight the future height of the water
     * @return int, representing the amount of land lost or gained
     */
    public int landLost(double height, double newHeight) {
        return totalVisibleLand(height) - totalVisibleLand(newHeight);
    }

    /**
     * Count the total number of islands on the flooded terrain.
     * 
     * Parts of the terrain are considered "islands" if they are completely 
     * surround by water in all 8-directions. Should there be a direction (ie. 
     * left corner) where a certain piece of land is connected to another 
     * landmass, this should be considered as one island. A better example 
     * would be if there were two landmasses connected by one cell. Although 
     * seemingly two islands, after further inspection it should be realized 
     * this is one single island. Only if this connection were to be removed 
     * (height of water increased) should these two landmasses be considered 
     * two separate islands.
     * 
     * @param height of the water
     * @return int, representing the total number of islands
     */
    public int numOfIslands(double height) {

        boolean[][] arr = floodedRegionsIn(height);
        WeightedQuickUnionUF qu = new WeightedQuickUnionUF(terrain.length, terrain[0].length);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (!arr[i][j]) {
                    if (i < arr.length - 1 && !arr[i][j]) {
                        qu.union(new GridLocation(i, j), new GridLocation(i + 1, j));
                    }

                    if (i != 0 && !arr[i - 1][j]) {
                        qu.union(new GridLocation(i, j), new GridLocation(i - 1, j));

                    }

                    if (j < arr[0].length - 1 && !arr[i][j + 1]) {
                        qu.union(new GridLocation(i, j), new GridLocation(i, j + 1));

                    }

                    if (j != 0 && !arr[i][j - 1]) {
                        qu.union(new GridLocation(i, j), new GridLocation(i, j - 1));

                    }

                    if (i != 0 && j != 0 && !arr[i - 1][j - 1]) {
                        qu.union(new GridLocation(i, j), new GridLocation(i - 1, j - 1));
                    }

                    if (i < arr.length - 1 && j != 0 && !arr[i + 1][j - 1]) {
                        qu.union(new GridLocation(i, j), new GridLocation(i + 1, j - 1));
                    }

                    if (i < arr.length - 1 && j < arr[0].length - 1 && !arr[i + 1][j + 1]) {
                        qu.union(new GridLocation(i, j), new GridLocation(i + 1, j + 1));
                    }

                    if (j < arr[0].length - 1 && i != 0 && !arr[i - 1][j + 1]) {
                        qu.union(new GridLocation(i, j), new GridLocation(i - 1, j + 1));
                    }
                }
            }
        }

        int counter = 0;
        GridLocation test;
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[0].length; j++) {
                if (!arr[i][j]){
                    test = new GridLocation(i,j);
                    if (qu.find(test).row == test.row && qu.find(test).col == test.col) {
                        counter++;
                    }
                }
            }
        }

        return counter;
    }
}




