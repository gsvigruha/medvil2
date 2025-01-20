package com.medville2.model.building.house;

import com.medville2.model.Terrain;

public class Mill extends BuildingObject {

	public Mill(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "mill";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void tick(Terrain terrain) {
		
	}
}