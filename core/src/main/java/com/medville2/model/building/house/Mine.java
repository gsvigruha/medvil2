package com.medville2.model.building.house;


public class Mine extends BuildingObject {

	public Mine(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "mine";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public boolean isMine() {
		return true;
	}
}
