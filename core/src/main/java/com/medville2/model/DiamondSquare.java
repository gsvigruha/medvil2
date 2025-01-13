package com.medville2.model;

import java.util.Random;

public class DiamondSquare {


    public static double[][] generateTerrain(int gridSize, int stepSize, double roughness) {
        if ((gridSize - 1 & (gridSize - 2)) != 0) {
            throw new IllegalArgumentException("Grid size must be 2^n + 1.");
        }

        double[][] grid = new double[gridSize][gridSize];
        Random random = new Random();

        // Seed the four corners
        for (int i = 0; i <= gridSize; i += stepSize) {
            for (int j = 0; j <= gridSize; j += stepSize) {
            	grid[i][j] = random.nextDouble();
            }
        }

        //int stepSize = gridSize - 1;

        while (stepSize > 1) {
            int halfStep = stepSize / 2;

            // Diamond step
            for (int x = halfStep; x < gridSize; x += stepSize) {
                for (int y = halfStep; y < gridSize; y += stepSize) {
                    double avg = (grid[x - halfStep][y - halfStep]
                                + grid[x - halfStep][y + halfStep]
                                + grid[x + halfStep][y - halfStep]
                                + grid[x + halfStep][y + halfStep]) / 4.0;
                    grid[x][y] = avg + randomOffset(random, roughness, stepSize);
                }
            }

            // Square step
            for (int x = 0; x < gridSize; x += halfStep) {
                for (int y = (x + halfStep) % stepSize; y < gridSize; y += stepSize) {
                    double avg = averageOfSurrounding(grid, x, y, halfStep, gridSize);
                    grid[x][y] = avg + randomOffset(random, roughness, stepSize);
                }
            }

            // Reduce step size and roughness
            stepSize /= 2;
            roughness /= 2.0;
        }

        return grid;
    }

    private static double averageOfSurrounding(double[][] grid, int x, int y, int step, int gridSize) {
        double sum = 0;
        int count = 0;

        if (x - step >= 0) {
            sum += grid[x - step][y];
            count++;
        }
        if (x + step < gridSize) {
            sum += grid[x + step][y];
            count++;
        }
        if (y - step >= 0) {
            sum += grid[x][y - step];
            count++;
        }
        if (y + step < gridSize) {
            sum += grid[x][y + step];
            count++;
        }

        return sum / count;
    }

    private static double randomOffset(Random random, double roughness, int stepSize) {
        return (random.nextDouble() * 2 - 1) * roughness * stepSize;
    }
}