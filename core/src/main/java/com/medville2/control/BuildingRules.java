package com.medville2.control;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.house.Mill;
import com.medville2.model.building.house.Mine;
import com.medville2.model.building.house.Townsquare;
import com.medville2.model.building.infra.Bridge;
import com.medville2.model.building.infra.InfraObject;
import com.medville2.model.building.infra.Road;
import com.medville2.model.building.infra.Tower;
import com.medville2.model.building.infra.Wall;
import com.medville2.model.terrain.Hill;
import com.medville2.model.terrain.Mountain;
import com.medville2.view.ControlPanelState;
import com.medville2.view.FieldCheckStatus;
import com.medville2.view.FieldCheckStatus.FieldWithStatus;

public class BuildingRules {

	private static final Set<Field.Type> InfraTypes = ImmutableSet.of(Field.Type.GRASS, Field.Type.ROCK);
	private static final Set<Field.Type> BuildingTypes = ImmutableSet.of(Field.Type.GRASS);
	private static final Set<Field.Type> MineTypes = ImmutableSet.of(Field.Type.ROCK);

	public static FieldCheckStatus getFieldCheckStatus(Field field, Terrain terrain, ControlPanelState state,
			Class<?> buildingClass, Editor editor) {
		if (field == null) {
			return FieldCheckStatus.fail(field);
		}
		if (state == ControlPanelState.BUILD_INFRA) {
			if (buildingClass.equals(Road.class)) {
				if (field != null && InfraTypes.contains(field.getType()) && field.getObject() == null) {
					return FieldCheckStatus.success(field, new Road(field));
				}
			} else if (buildingClass.equals(Bridge.class)) {
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
			} else if (buildingClass.equals(Tower.class)) {
				if (field != null && InfraTypes.contains(field.getType()) && field.getObject() == null) {
					return FieldCheckStatus.success(field, new Tower(field));
				}
			} else if (buildingClass.equals(Wall.class)) {
				if (field != null && InfraTypes.contains(field.getType()) && field.getObject() == null) {
					Wall wall = new Wall(field);
					return FieldCheckStatus.success(field, wall);
				}
			}
		}

		if (state == ControlPanelState.BUILD_HOUSE) {
			if (buildingClass.equals(Mine.class)) {
				if (MineTypes.contains(field.getType()) && field.getObject() != null && field.getObject().isHill()
						&& terrain.hasNeighbor(field.getI(), field.getJ(), f -> f.getObject() == null
								|| !(f.getObject().isHill() || f.getObject().getClass().equals(Mountain.class)))) {
					Hill hill = ((Hill) field.getObject());
					FieldCheckStatus fcs = FieldCheckStatus.success(field, new Mine(field, hill));
					if (!hill.isEmpty()) {
						fcs.setLabel(hill.getMineral() + ": " + hill.getQuantity());
						fcs.setIcon("artifact_" + hill.getMineral().toLowerCase());
					}
					return fcs;
				}
			} else if (buildingClass.equals(Mill.class)) {
				BuildingObject building = newHouse((Class<? extends BuildingObject>) buildingClass, field);
				if (BuildingTypes.contains(field.getType()) && field.getObject() == null
						&& terrain.hasNeighbor(field.getI(), field.getJ(), Field.Type.RIVER)) {
					return FieldCheckStatus.success(field, building);
				}
			} else {
				BuildingObject building = newHouse((Class<? extends BuildingObject>) buildingClass, field);
				FieldCheckStatus fcs = checkFields(field, building, terrain);
				fcs.setBuildableObject(building);
				return fcs;
			}
		}

		if (state == ControlPanelState.FOUND_TOWN) {
			BuildingObject building = newHouse(Townsquare.class, field);
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

	public static BuildingObject newHouse(Class<? extends BuildingObject> clss, Field field) {
		try {
			return clss.getConstructor(Field.class).newInstance(field);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static InfraObject newInfra(Class<? extends InfraObject> clss, Field field) {
		try {
			return clss.getConstructor(Field.class).newInstance(field);
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
		if (Wall.class.isAssignableFrom(f.getObject().getClass())) {
			return true;
		}
		return false;
	}

	public static void setupWalls(int i, int j, Terrain terrain) {
		Field f = terrain.getField(i, j);
		if (f == null || f.getObject() == null) {
			return;
		}
		if (Wall.class.isAssignableFrom(f.getObject().getClass())) {
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
