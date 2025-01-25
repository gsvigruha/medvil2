package com.medville2.model.terrain;

public class Fishnet extends TerrainObject {

	public Fishnet(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "fishnet";
	}

	@Override
	public boolean isHill() {
		return false;
	}
}
