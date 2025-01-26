package com.medville2.model.building.infra;

import com.medville2.model.Field;

public class Tower extends Wall {

	public Tower(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "tower";
	}
}