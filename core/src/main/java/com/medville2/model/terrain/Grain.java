package com.medville2.model.terrain;

public class Grain extends TerrainObject {

	public Grain(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "grain";
	}

	@Override
	public boolean isHill() {
		return false;
	}
}
