package com.medville2.model.terrain;

public class Hill extends TerrainObject {

	public Hill(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "hill";
	}

	@Override
	public boolean isHill() {
		return true;
	}
}
