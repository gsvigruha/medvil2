package com.medville2.model.building.house;

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
}
