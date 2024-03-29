package org.practice.domo.herarchy;

import java.util.*;

public class RoutePlanner {

    public static boolean routeExists(int fromRow, int fromColumn, int toRow, int toColumn,
                                      boolean[][] mapMatrix) {
        if (!mapMatrix[0][0]){
            return false;
        }
        for (int i = 1; i < mapMatrix.length; i++)
            if (mapMatrix[0][i]){
                mapMatrix[0][i] = mapMatrix[0][i-1];
            }

            for (int j = 1; j < mapMatrix.length; j++) {
                if (mapMatrix[j][0]) {
                    mapMatrix[j][0] = mapMatrix[j-1][0];
                }
            }

            for (int k = 1; k < mapMatrix.length; k++) {
                for (int z = 1; z < mapMatrix.length; z++) {
                    if (mapMatrix[k][z]){
                        mapMatrix[k][z] = mapMatrix[k][z-1] ? mapMatrix[k][z-1] : mapMatrix[k-1][z];
                    }
                }
            }


        return mapMatrix[2][2];
    }

    public static boolean routeExistsIA(int fromRow, int fromColumn, int toRow, int toColumn, boolean[][] mapMatrix) {
        if (mapMatrix == null || mapMatrix.length == 0 || mapMatrix[0].length == 0) {
            throw new IllegalArgumentException("Map matrix is empty or null.");
        }
        if (fromRow < 0 || fromRow >= mapMatrix.length || fromColumn < 0 || fromColumn >= mapMatrix[0].length ||
                toRow < 0 || toRow >= mapMatrix.length || toColumn < 0 || toColumn >= mapMatrix[0].length) {
            throw new IllegalArgumentException("Start or end position is out of bounds.");
        }

        // Initialize a visited matrix or use another method to track visited cells
        boolean[][] visited = new boolean[mapMatrix.length][mapMatrix[0].length];

        // Implement a pathfinding algorithm (e.g., DFS or BFS) to check if a path exists
        // This is a placeholder for the actual pathfinding logic
        return findPath(fromRow, fromColumn, toRow, toColumn, mapMatrix, visited);
    }

    // Placeholder for a pathfinding method (e.g., DFS or BFS)
    private static boolean findPath(int fromRow, int fromColumn, int toRow, int toColumn, boolean[][] mapMatrix, boolean[][] visited) {
        // Implement pathfinding logic here
        return false; // Return true if a path is found
    }


    public static void main(String[] args) {
        boolean[][] mapMatrix = {
                {true,  false, false},
                {true,  false,  false},
                {false, true,  true}
        };

        System.out.println(routeExists(0, 0, 2, 2, mapMatrix));
    }
}