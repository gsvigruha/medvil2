package com.medville2.model.building.house;

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
}