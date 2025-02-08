package com.medville2.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.medville2.model.building.house.Blacksmith;
import com.medville2.model.building.house.Farm;
import com.medville2.model.building.house.Mill;
import com.medville2.model.building.house.Mine;
import com.medville2.model.building.house.Workshop;
import com.medville2.model.building.infra.Bridge;
import com.medville2.model.building.infra.Tower;
import com.medville2.model.building.infra.Wall;
import com.medville2.model.society.Person;
import com.medville2.model.terrain.Hill;
import com.medville2.model.terrain.Mountain;

public class Field implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Type {
		WATER,
		RIVER,
		GRASS,
		ROCK,
	}

	private final int i;
	private final int j;

	private Type type;
	private Type cornerType;
	private double height;
	private FieldObject object;
	private int distanceFromWater;
	private Set<Person> people;

	public Field(int i, int j) {
		this.type = Type.GRASS;
		this.i = i;
		this.j = j;
		this.people = new HashSet<>();
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public FieldObject getObject() {
		return object;
	}

	public void setObject(FieldObject object) {
		this.object = object;
	}

	public Type getCornerType() {
		return cornerType;
	}

	public void setCornerType(Type cornerType) {
		this.cornerType = cornerType;
	}

	public float getCropYield() {
		if (type != Type.GRASS) {
			return 0;
		}
		return (10f - Math.min(Math.max((float) distanceFromWater - 1, 0f), 10f)) / 10f;
	}

	public int getDistanceFromWater() {
		return distanceFromWater;
	}

	public void setDistanceFromWater(int distanceFromWater) {
		this.distanceFromWater = distanceFromWater;
	}

	@Override
	public int hashCode() {
		return Objects.hash(i, j);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		return i == other.i && j == other.j;
	}

	public void unregister(Person person) {
		people.remove(person);
	}

	public void register(Person person) {
		people.add(person);
	}

	public Iterable<Person> getPeople() {
		return people;
	}

	private static final ImmutableSet<FieldObjectType> blockingObjects = ImmutableSet.of(
			Wall.Type, Tower.Type, Mountain.Type, Hill.Type,
			Blacksmith.Type, Mill.Type, Farm.Type, Mine.Type, Workshop.Type);

	public boolean walkable() {
		if (getType() == Field.Type.RIVER || getType() == Field.Type.WATER) {
			if (getObject() != null && getObject().getType() == Bridge.Type) {
				return true;
			}
			return false;
		}
		if (getObject() != null) {
			if (blockingObjects.contains(getObject().getType())) {
				return false;
			}
		}
		return true;
	}
 }
