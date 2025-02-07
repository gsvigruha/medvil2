package com.medville2.model;

import java.io.ObjectStreamException;
import java.io.Serializable;

import com.medville2.model.building.house.Blacksmith;
import com.medville2.model.building.house.Farm;
import com.medville2.model.building.house.Mill;
import com.medville2.model.building.house.Mine;
import com.medville2.model.building.house.Townsquare;
import com.medville2.model.building.house.Workshop;
import com.medville2.model.building.infra.Bridge;
import com.medville2.model.building.infra.Road;
import com.medville2.model.building.infra.Tower;
import com.medville2.model.building.infra.Wall;
import com.medville2.model.terrain.Fishnet;
import com.medville2.model.terrain.Grain;
import com.medville2.model.terrain.Hill;
import com.medville2.model.terrain.Mountain;
import com.medville2.model.terrain.Sheep;
import com.medville2.model.terrain.Tree;

public class FieldObjectType implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final int size;
	private final Class<? extends FieldObject> clss;

	public FieldObjectType(String name, int size, Class<? extends FieldObject> clss) {
		this.name = name;
		this.size = size;
		this.clss = clss;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public Class<? extends FieldObject> getClss() {
		return clss;
	}

	private Object readResolve() throws ObjectStreamException {
		if (clss.equals(Wall.class)) {
			return Wall.Type;
		} else if (clss.equals(Tower.class)) {
			return Tower.Type;
		} else if (clss.equals(Blacksmith.class)) {
			return Blacksmith.Type;
		} else if (clss.equals(Farm.class)) {
			return Farm.Type;
		} else if (clss.equals(Mill.class)) {
			return Mill.Type;
		} else if (clss.equals(Mine.class)) {
			return Mine.Type;
		} else if (clss.equals(Townsquare.class)) {
			return Townsquare.Type;
		} else if (clss.equals(Workshop.class)) {
			return Workshop.Type;
		} else if (clss.equals(Bridge.class)) {
			return Bridge.Type;
		} else if (clss.equals(Road.class)) {
			return Road.Type;
		}

		if (clss.equals(Fishnet.class)) {
			return Fishnet.Type;
		} else if (clss.equals(Grain.class)) {
			return Grain.Type;
		} else if (clss.equals(Hill.class)) {
			return Hill.Type;
		} else if (clss.equals(Mountain.class)) {
			return Mountain.Type;
		} else if (clss.equals(Sheep.class)) {
			return Sheep.Type;
		} else if (clss.equals(Tree.class)) {
			return Tree.Type;
		} 
		return this;
	}
}
