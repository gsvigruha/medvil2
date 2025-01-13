package com.medville2.model.building.infra;

public class Wall extends InfraObject {

	public Wall(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "wall";
	}
}
