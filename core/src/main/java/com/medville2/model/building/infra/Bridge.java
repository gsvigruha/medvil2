package com.medville2.model.building.infra;

public class Bridge extends InfraObject {

	public Bridge(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "bridge";
	}
}
