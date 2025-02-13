package com.medville2.model.terrain;

import java.util.ArrayList;

import com.medville2.model.Field;
import com.medville2.model.Terrain;

public class DistanceFromWater {

	public static int[][] computeDistanceFromWater(Terrain terrain) {
		int size = terrain.getSize();
		int[][] result = new int[size][size];
		@SuppressWarnings("unchecked")
		ArrayList<Field>[] distances = new ArrayList[size * 2];
		for (int i = 0; i < size * 2; i++) {
			distances[i] = new ArrayList<>();
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Field field = terrain.getField(i, j);
				if (field.getType() == Field.Type.WATER || field.getType() == Field.Type.RIVER) {
					result[i][j] = 0;
					distances[0].add(field);
				} else {
					result[i][j] = Integer.MAX_VALUE;
				}
			}
		}

		for (int i = 0; i < size * 2; i++) {
			for (int j = 0; j < distances[i].size(); j++) {
				Field field = distances[i].get(j);
				processField(terrain, field.getI() + 1, field.getJ(), i + 1, result, distances);
				processField(terrain, field.getI() - 1, field.getJ(), i + 1, result, distances);
				processField(terrain, field.getI(), field.getJ() + 1, i + 1, result, distances);
				processField(terrain, field.getI(), field.getJ() - 1, i + 1, result, distances);
			}
		}

		return result;
	}

	private static void processField(Terrain terrain, int i, int j, int distance, int[][] result, ArrayList<Field>[] distances) {
		Field f = terrain.getField(i, j);
		if (f != null && f.getType() != Field.Type.WATER && f.getType() != Field.Type.RIVER && distance < result[f.getI()][f.getJ()]) {
			result[f.getI()][f.getJ()] = distance;
			distances[distance].add(f);
		}
	}
}
