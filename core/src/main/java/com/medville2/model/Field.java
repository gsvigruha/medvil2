package com.medville2.model;

import java.util.Objects;

public class Field {

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

	public Field(int i, int j) {
		this.type = Type.GRASS;
		this.i = i;
		this.j = j;
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
		return (10f - Math.min(Math.max((float) height, 0f), 10f)) / 10f;
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
}
