package com.medville2.model.terrain;

public class Sheep extends TerrainObject {

	public Sheep(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "sheep";
	}

	@Override
	public boolean isHill() {
		return false;
	}
}
