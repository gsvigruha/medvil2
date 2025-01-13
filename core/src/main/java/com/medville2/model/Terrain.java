package com.medville2.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.badlogic.gdx.utils.Logger;
import com.medville2.model.Field.Type;
import com.medville2.model.terrain.Hill;
import com.medville2.model.terrain.Mountain;
import com.medville2.model.terrain.Tree;

public class Terrain {

	public static final int DX = 256;
	public static final int DY = 192;

	private final int size;
	private final Field[][] fields;

    static final Logger LOGGER = new Logger(Terrain.class.getName(), Logger.INFO);

	public Terrain(int size, int res) {
		this.size = size;
		this.fields = new Field[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				fields[i][j] = new Field(i, j);
			}	
		}

		LOGGER.info("Map of size " + size + " created.");

		int nWater = 0;
		int nGrass = 0;
		int nRock = 0;

		double roughness = 0.5;
        double[][] heightmap = DiamondSquare.generateTerrain(size + 1, res, roughness);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				fields[i][j].setHeight(heightmap[i][j]);
			}
		}

		// Set field types
        for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Field field = getField(i, j);
				if (field.getHeight() < -2.0) {
					field.setType(Type.WATER);
					nWater++;
				} else if (field.getHeight() > 10.0) {
					field.setType(Type.ROCK);
					nRock++;
				} else {
					field.setType(Type.GRASS);
					nGrass++;
				}
			}	
		}

        // Generate rivers
        for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Field field = getField(i, j);
				if (field.getType() == Field.Type.GRASS && field.getObject() == null && field.getHeight() > 8.0 && Math.random() < 0.02) {
					makeRiver(field);
				}
        	}
        }

        // Generate objects
        for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Field field = getField(i, j);
				field.setCornerType(getCornerType(i, j));
				if (field.getType() == Field.Type.GRASS) {
					if (Math.random() < 0.2) {
						Tree.Type type = Tree.Type.GREEN;
						if (Math.random() < 0.2) {
							type = Tree.Type.BLOOMING;
						} else if (Math.random() < 0.2) {
							type = Tree.Type.SMALL;
						}
						field.setObject(new Tree(type, i, j));
					}
				} else if (field.getType() == Field.Type.ROCK) {
					if (!isFlat(i, j)) {
						if (isLargeMountain(i, j) && isLargeMountain(i + 1, j) && isLargeMountain(i, j + 1) && isLargeMountain(i + 1, j + 1)) {
							Mountain hill = new Mountain(i, j);
							getField(i, j).setObject(hill);
							getField(i + 1, j).setObject(hill);
							getField(i, j + 1).setObject(hill);
							getField(i + 1, j + 1).setObject(hill);
						} else if (field.getObject() == null) {
							field.setObject(new Hill(i, j));
						}
					}
				}
        	}
        }

		LOGGER.info("Map of size " + size + " created");
		LOGGER.info("Grass: " + nGrass + ", water: " + nWater + ", rock: " + nRock);
	}

	private boolean isLargeMountain(int i, int j) {
		Field field = getField(i, j);
		if (field == null) {
			return false;
		}
		if (field.getHeight() < 12.0) {
			return false;
		}
		if (field.getObject() != null) {
			return false;
		}
		if (isFlat(i, j)) {
			return false;
		}
		return true;
	}

	private boolean hasNeighbor(int i, int j, Type type) {
		Field f1 = getField(i - 1, j);
		if (f1 != null && f1.getType() == type) {
			return true;
		}
		Field f2 = getField(i + 1, j);
		if (f2 != null && f2.getType() == type) {
			return true;
		}
		Field f3 = getField(i, j - 1);
		if (f3 != null && f3.getType() == type) {
			return true;
		}
		Field f4 = getField(i, j + 1);
		if (f4 != null && f4.getType() == type) {
			return true;
		}
		return false;
	}

	private void makeRiver(Field field) {
		Set<Field> fields = new HashSet<>();
		while (true) {
			int i = field.getI();
			int j = field.getJ();
			field.setType(Type.RIVER);
			fields.add(field);
			double h = Double.MAX_VALUE;
			
			Field f1 = getField(i - 1, j);
			if (f1 != null && !fields.contains(f1) && f1.getHeight() < h) {
				field = f1;
				h = field.getHeight();
			}
			Field f2 = getField(i + 1, j);
			if (f2 != null && !fields.contains(f2) && f2.getHeight() < h) {
				field = f2;
				h = field.getHeight();
			}
			Field f3 = getField(i, j - 1);
			if (f3 != null && !fields.contains(f3) && f3.getHeight() < h) {
				field = f3;
				h = field.getHeight();
			}
			Field f4 = getField(i, j + 1);
			if (f4 != null && !fields.contains(f4) && f4.getHeight() < h) {
				field = f4;
				h = field.getHeight();
			}
			if (field.getType() != Field.Type.WATER && field.getType() != Field.Type.RIVER) {
				continue;
			} else {
				return;
			}
		}
	}

	public Field[][] getFields() {
		return this.fields;
	}

	public int getSize() {
		return this.size;
	}

	public Field getField(int i, int j) {
		if (i < 0 || j < 0 || i >= size || j >= size) {
			return null;
		}
		return this.fields[i][j];
	}

	private boolean isSameLevel(Field f1, Field f2) {
		if (f1 == null || f2 == null) {
			return true;
		}
		//System.out.println(f1.getHeight() + " " + f2.getHeight());
		return Math.abs(f1.getHeight() - f2.getHeight()) <= 0.2;
	}

	public boolean isFlat(int i, int j) {
		return isSameLevel(getField(i - 1, j), getField(i + 1, j)) || isSameLevel(getField(i, j - 1), getField(i, j + 1));
	}

	public Field.Type getCornerType(int i, int j) {
		List<Field> fields = Stream.of(
				getField(i, j), getField(i + 1, j), getField(i, j + 1), getField(i + 1, j + 1)
				).filter(f -> f != null).toList();
		long cnt = fields.size();
		if (cnt < 4) {
			return null;
		}
		Map<Field.Type, Long> counts = fields.stream().map(f -> f.getType()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		Long grass = counts.get(Field.Type.GRASS);
		if (grass != null && grass == 3) {
			return Field.Type.GRASS;
		}
		Long water = counts.get(Field.Type.WATER);
		if (water != null && water == 3) {
			return Field.Type.WATER;
		}
		Long river = counts.get(Field.Type.RIVER);
		if (river != null && river == 3) {
			return Field.Type.RIVER;
		}
		Long rock = counts.get(Field.Type.ROCK);
		if (rock != null && rock == 3) {
			return Field.Type.ROCK;
		}
		return null;
	}
}
