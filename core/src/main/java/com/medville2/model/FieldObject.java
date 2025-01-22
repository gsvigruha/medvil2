package com.medville2.model;

public abstract class FieldObject {

	private final int i;
	private final int j;

	public FieldObject(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public abstract String getName();

	public abstract int getSize();

	public abstract boolean isHill();

	public boolean isFlip() {
		return false;
	}

	public abstract void tick(Terrain terrain);

	public void handleClick(Field field) {
	}

	public String getLabel(Field field) {
		return null;
	}
}
