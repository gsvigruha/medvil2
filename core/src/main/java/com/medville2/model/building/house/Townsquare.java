package com.medville2.model.building.house;

import com.medville2.model.Terrain;

public class Townsquare extends BuildingObject {

	public Townsquare(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "townsquare";
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public void tick(Terrain terrain) {
		
	}
}
