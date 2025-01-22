package com.medville2.model.building.house;

import com.medville2.model.Terrain;

public class Blacksmith extends BuildingObject {

	public Blacksmith(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "blacksmith";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void tick(Terrain terrain) {
		
	}
}
