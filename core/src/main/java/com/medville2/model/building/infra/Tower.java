package com.medville2.model.building.infra;

public class Tower extends InfraObject {

	public Tower(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "tower";
	}
}