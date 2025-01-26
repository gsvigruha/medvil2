package com.medville2.model.building.infra;

import com.medville2.model.Field;

public class Road extends InfraObject {

	public Road(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "road";
	}
}
