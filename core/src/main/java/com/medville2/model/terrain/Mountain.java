package com.medville2.model.terrain;

public class Mountain extends TerrainObject {

	public Mountain(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "mountain";
	}

	@Override
	public boolean isHill() {
		return false;
	}

	@Override
	public int getSize() {
		return 2;
	}
}
