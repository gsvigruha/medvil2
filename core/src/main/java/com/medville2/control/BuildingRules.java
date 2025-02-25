package com.medville2.control;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.house.Mill;
import com.medville2.model.building.house.Mine;
import com.medville2.model.building.house.Townsquare;
import com.medville2.model.building.infra.Bridge;
import com.medville2.model.building.infra.Road;
import com.medville2.model.building.infra.Tower;
import com.medville2.model.building.infra.Wall;
import com.medville2.model.society.Town;
import com.medville2.model.terrain.Hill;
import com.medville2.model.terrain.Mountain;
import com.medville2.view.ControlPanelState;
import com.medville2.view.FieldCheckStatus;
import com.medville2.view.FieldCheckStatus.FieldWithStatus;

public class BuildingRules {

	private static final Set<Field.Type> InfraTypes = ImmutableSet.of(Field.Type.GRASS, Field.Type.ROCK);
	private static final Set<Field.Type> BuildingTypes = ImmutableSet.of(Field.Type.GRASS);
	private static final Set<Field.Type> MineTypes = ImmutableSet.of(Field.Type.ROCK);

	private static final int MAX_DISTANCE_FROM_TOWNSQUARE = 25;

	public static FieldCheckStatus getFieldCheckStatus(Field field, Terrain terrain, ControlPanelState state,
			FieldObjectType buildingType, Editor editor, Town activeTown) {
		if (field == null) {
			return FieldCheckStatus.fail(field);
		}
		if (state == ControlPanelState.BUILD_INFRA) {
			if (buildingType == Road.Type) {
				if (field != null && InfraTypes.contains(field.getType()) && field.getObject() == null) {
					return FieldCheckStatus.success(field, new Road(field));
				}
			} else if (buildingType == Bridge.Type) {
				if (field != null && field.getType() == Field.Type.RIVER && field.getObject() == null) {
					if (terrain.isType(field.getI() - 1, field.getJ(), Field.Type.GRASS)
							&& terrain.isType(field.getI() + 1, field.getJ(), Field.Type.GRASS)
							&& terrain.isType(field.getI(), field.getJ() - 1, Field.Type.RIVER)
							&& terrain.isType(field.getI(), field.getJ() + 1, Field.Type.RIVER)) {
						return FieldCheckStatus.success(field, new Bridge(field, false));
					}
					if (terrain.isType(field.getI() - 1, field.getJ(), Field.Type.RIVER)
							&& terrain.isType(field.getI() + 1, field.getJ(), Field.Type.RIVER)
							&& terrain.isType(field.getI(), field.getJ() - 1, Field.Type.GRASS)
							&& terrain.isType(field.getI(), field.getJ() + 1, Field.Type.GRASS)) {
						return FieldCheckStatus.success(field, new Bridge(field, true));
					}
				}
			} else if (buildingType == Tower.Type) {
				if (field != null && InfraTypes.contains(field.getType()) && field.getObject() == null) {
					return FieldCheckStatus.success(field, new Tower(field));
				}
			} else if (buildingType == Wall.Type) {
				if (field != null && InfraTypes.contains(field.getType()) && field.getObject() == null) {
					Wall wall = new Wall(field);
					return FieldCheckStatus.success(field, wall);
				}
			}
		}

		if (state == ControlPanelState.BUILD_HOUSE) {
			if (activeTown == null && buildingType != Townsquare.Type) {
				return FieldCheckStatus.fail(field);
			} else if (activeTown != null && distance(activeTown.getTownsquare(), field) > MAX_DISTANCE_FROM_TOWNSQUARE) {
				return FieldCheckStatus.fail(field);
			}
			if (buildingType == Mine.Type) {
				if (MineTypes.contains(field.getType()) && field.getObject() != null && field.getObject().getType() == Hill.Type
						&& terrain.hasNeighbor(field.getI(), field.getJ(), f -> f.getObject() == null
								|| !(f.getObject().getType() == Hill.Type || f.getObject().getType() == Mountain.Type))) {
					Hill hill = ((Hill) field.getObject());
					FieldCheckStatus fcs = FieldCheckStatus.success(field, new Mine(field, hill));
					if (!hill.isEmpty()) {
						fcs.setLabel(hill.getMineral() + ": " + hill.getQuantity());
						fcs.setIcon("artifact_" + hill.getMineral().toLowerCase());
					}
					return fcs;
				}
			} else if (buildingType == Mill.Type) {
				BuildingObject building = newHouse(buildingType, field);
				if (BuildingTypes.contains(field.getType()) && field.getObject() == null
						&& terrain.hasNeighbor(field.getI(), field.getJ(), Field.Type.RIVER)) {
					return FieldCheckStatus.success(field, building);
				}
			} else {
				BuildingObject building = newHouse(buildingType, field);
				FieldCheckStatus fcs = checkFields(field, building, terrain);
				fcs.setBuildableObject(building);
				return fcs;
			}
		}

		if (state == ControlPanelState.FOUND_TOWN) {
			BuildingObject building = newHouse(Townsquare.Type, field);
			FieldCheckStatus fcs = checkFields(field, building, terrain);
			fcs.setBuildableObject(building);
			return fcs;
		}

		if (state == ControlPanelState.SELECT) {
			if (field.getObject() != null) {
				return FieldCheckStatus.success(field, field.getObject());
			}
		}

		if (state == ControlPanelState.MODIFY && editor != null) {
			String label = editor.getLabel(field);
			String icon = editor.getIcon(field);
			if (label == null) {
				return FieldCheckStatus.fail(field);
			} else {
				return FieldCheckStatus.success(field, label, icon);
			}
		}

		return FieldCheckStatus.fail(field);
	}

	private static int distance(FieldObject fo, Field field) {
		double dx = fo.getI() - field.getI();
		double dy = fo.getJ() - field.getJ();
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	private static FieldCheckStatus checkFields(Field field, BuildingObject building, Terrain terrain) {
		FieldCheckStatus fcs = new FieldCheckStatus();
		int i0 = field.getI();
		int j0 = field.getJ();
		for (int i = i0; i < i0 + building.getSize(); i++) {
			for (int j = j0; j < j0 + building.getSize(); j++) {
				Field f = terrain.getField(i, j);
				if (f == null) {
					fcs.addFieldWithStatus(new FieldWithStatus(new Field(i, j), false));
				} else {
					boolean fieldStatus = !(f == null || !BuildingTypes.contains(f.getType()) || f.getObject() != null);
					fcs.addFieldWithStatus(new FieldWithStatus(f, fieldStatus));
				}
			}
		}
		return fcs;
	}

	public static BuildingObject newHouse(FieldObjectType buildingType, Field field) {
		try {
			java.lang.reflect.Constructor<? extends FieldObject> c = buildingType.getClss().getConstructor(Field.class);
			return (BuildingObject) c.newInstance(field);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean connectWall(int i, int j, Terrain terrain) {
		Field f = terrain.getField(i, j);
		if (f == null) {
			return false;
		}
		if (f.getObject() == null) {
			return false;
		}
		if (f.getObject().getType() == Wall.Type || f.getObject().getType() == Tower.Type) {
			return true;
		}
		return false;
	}

	public static void setupWalls(int i, int j, Terrain terrain) {
		Field f = terrain.getField(i, j);
		if (f == null || f.getObject() == null) {
			return;
		}
		if (f.getObject().getType() == Wall.Type || f.getObject().getType() == Tower.Type) {
			Wall wall = (Wall) f.getObject();
			wall.setSegment(0, connectWall(wall.getI() - 1, wall.getJ(), terrain));
			wall.setSegment(1, connectWall(wall.getI() + 1, wall.getJ(), terrain));
			wall.setSegment(2, connectWall(wall.getI(), wall.getJ() - 1, terrain));
			wall.setSegment(3, connectWall(wall.getI(), wall.getJ() + 1, terrain));
			if (wall.hasSegment(0) && !wall.hasSegment(1) && !wall.hasSegment(2) && !wall.hasSegment(3)) {
				wall.setSegment(1, true);
			} else if (!wall.hasSegment(0) && wall.hasSegment(1) && !wall.hasSegment(2) && !wall.hasSegment(3)) {
				wall.setSegment(0, true);
			} else if (!wall.hasSegment(0) && !wall.hasSegment(1) && wall.hasSegment(2) && !wall.hasSegment(3)) {
				wall.setSegment(3, true);
			} else if (!wall.hasSegment(0) && !wall.hasSegment(1) && !wall.hasSegment(2) && wall.hasSegment(3)) {
				wall.setSegment(2, true);
			}
			if (f.getObject().getClass().equals(Wall.class) && !wall.hasSegment(0) && !wall.hasSegment(1) && !wall.hasSegment(2) && !wall.hasSegment(3)) {
				wall.setSegment(0, true);
				wall.setSegment(1, true);
				wall.setSegment(2, true);
				wall.setSegment(3, true);
			}
		}
	}
}
