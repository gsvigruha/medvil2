package com.medville2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.badlogic.gdx.utils.Logger;
import com.medville2.model.Field.Type;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.terrain.DistanceFromWater;
import com.medville2.model.terrain.Hill;
import com.medville2.model.terrain.Mountain;
import com.medville2.model.terrain.OpenSimplex2;
import com.medville2.model.terrain.Tree;
import com.medville2.model.time.Calendar;

public class Terrain implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int DX = 256;
	public static final int DY = 192;

	private final int size;
	private final Field[][] fields;

    static final Logger LOGGER = new Logger(Terrain.class.getName(), Logger.INFO);

    private static final long GOLD_RANDOM_SEED = 1l;
    private static final double GOLD_THRESHOLD = 0.8;
    private static final long IRON_RANDOM_SEED = 2l;
    private static final double IRON_THRESHOLD = 0.7;
    private static final long STONE_RANDOM_SEED = 3l;
    private static final double STONE_THRESHOLD = 0.6;
    private static final long CLAY_RANDOM_SEED = 4l;
    private static final double CLAY_THRESHOLD = 0.8;
    private static final int MINERAL_FREQ = 32;

	public Terrain(int size, int res, double roughness, double waterThreshold) {
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
				if (field.getHeight() < waterThreshold) {
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
					if (hasNeighbor(i, j, Type.RIVER)) {
						if (OpenSimplex2.noise2(CLAY_RANDOM_SEED, i, j, size, MINERAL_FREQ) > CLAY_THRESHOLD) {
							field.setType(Type.ROCK);
							field.setObject(new Hill(field, Artifacts.CLAY, (int) (Math.random() * 100)));
						}
					} else if (Math.random() < 0.2) {
						Tree.TreeType type = Tree.TreeType.GREEN;
						if (Math.random() < 0.2) {
							type = Tree.TreeType.BLOOMING;
						} else if (Math.random() < 0.2) {
							type = Tree.TreeType.SMALL;
						}
						field.setObject(new Tree(type, field));
					}
				} else if (field.getType() == Field.Type.ROCK) {
					if (!isFlat(i, j)) {
						if (isLargeMountain(i, j) && isLargeMountain(i + 1, j) && isLargeMountain(i, j + 1) && isLargeMountain(i + 1, j + 1)) {
							Mountain mountain = new Mountain(field);
							getField(i, j).setObject(mountain);
							getField(i + 1, j).setObject(mountain);
							getField(i, j + 1).setObject(mountain);
							getField(i + 1, j + 1).setObject(mountain);
						} else if (field.getObject() == null) {
							Map.Entry<String, Integer> e = getMiningArtifact(i, j);
							if (e != null) {
								field.setObject(new Hill(field, e.getKey(), e.getValue()));
							} else {
								field.setObject(new Hill(field, null, 0));
							}
						}
					}
				}
        	}
        }

        int[][] distanceFromWater = DistanceFromWater.computeDistanceFromWater(this);
        for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Field field = getField(i, j);
				field.setDistanceFromWater(distanceFromWater[i][j]);
			}
        }

		LOGGER.info("Map of size " + size + " created");
		LOGGER.info("Grass: " + nGrass + ", water: " + nWater + ", rock: " + nRock);
	}

	private Map.Entry<String, Integer> getMiningArtifact(int i, int j) {
		double gold = OpenSimplex2.noise2(GOLD_RANDOM_SEED, i, j, size, MINERAL_FREQ);
		double iron = OpenSimplex2.noise2(IRON_RANDOM_SEED, i, j, size, MINERAL_FREQ);
		double stone = OpenSimplex2.noise2(STONE_RANDOM_SEED, i, j, size, MINERAL_FREQ);

		if (stone > STONE_THRESHOLD) {
			return Map.entry(Artifacts.STONE, (int)((stone - STONE_THRESHOLD) / (1 - STONE_THRESHOLD) * 100));
		} else if (iron > IRON_THRESHOLD) {
			return Map.entry(Artifacts.IRON, (int)((iron - IRON_THRESHOLD) / (1 - IRON_THRESHOLD) * 100));
		} else if (gold > GOLD_THRESHOLD) {
			return Map.entry(Artifacts.GOLD, (int)((gold - GOLD_THRESHOLD) / (1 - GOLD_THRESHOLD) * 100));
		}  
		return null;
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

	public boolean isType(int i, int j, Type type) {
		Field f = getField(i, j);
		if (f != null && f.getType() == type) {
			return true;
		}
		return false;
	}

	public boolean hasNeighbor(int i, int j, Type type) {
		if (isType(i - 1 , j, type)) {
			return true;
		}
		if (isType(i + 1 , j, type)) {
			return true;
		}
		if (isType(i, j - 1, type)) {
			return true;
		}
		if (isType(i, j + 1, type)) {
			return true;
		}
		return false;
	}

	public boolean hasNeighbor(int i, int j, Function<Field, Boolean> fn) {
		Field f1 = getField(i - 1, j);
		if (f1 != null && fn.apply(f1)) {
			return true;
		}
		Field f2 = getField(i + 1, j);
		if (f2 != null && fn.apply(f2)) {
			return true;
		}
		Field f3 = getField(i, j - 1);
		if (f3 != null && fn.apply(f3)) {
			return true;
		}
		Field f4 = getField(i, j + 1);
		if (f4 != null && fn.apply(f4)) {
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

	public void tick(Calendar calendar) {
		for (int i = 0; i < getSize(); i++) {
			Field[] fields = getFields()[i];
			for (int j = 0; j < getSize(); j++) {
				Field field = fields[j];
				if (field.getObject() != null) {
					field.getObject().tick(this, calendar);
				}
			}
		}
	}

	public List<Field> getNeighbors(Field field) {
		List<Field> result = new ArrayList<>(4);
		int i = field.getI();
		int j = field.getJ();

		Field f1 = getField(i - 1, j);
		if (f1 != null) {
			result.add(f1);
		}
		Field f2 = getField(i + 1, j);
		if (f2 != null) {
			result.add(f2);
		}
		Field f3 = getField(i, j - 1);
		if (f3 != null) {
			result.add(f3);
		}
		Field f4 = getField(i, j + 1);
		if (f4 != null) {
			result.add(f4);
		}
		return result;
	}
}
