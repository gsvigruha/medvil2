package com.medville2.model.terrain;

import com.medville2.model.Field;

public class Hill extends TerrainObject {

	public Hill(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "hill";
	}

	@Override
	public boolean isHill() {
		return true;
	}
}
