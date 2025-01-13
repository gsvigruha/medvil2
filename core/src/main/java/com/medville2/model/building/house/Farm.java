package com.medville2.model.building.house;


public class Farm extends BuildingObject {

	public Farm(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "farm";
	}

	@Override
	public int getSize() {
		return 1;
	}
}
