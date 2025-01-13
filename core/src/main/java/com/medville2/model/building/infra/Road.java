package com.medville2.model.building.infra;

public class Road extends InfraObject {

	public Road(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "road";
	}
}
