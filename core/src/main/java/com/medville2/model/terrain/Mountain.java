package com.medville2.model.terrain;

import com.medville2.model.Field;

public class Mountain extends TerrainObject {

	public Mountain(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "mountain";
	}

	@Override
	public boolean isHill() {
		return false;
	}

	@Override
	public int getSize() {
		return 2;
	}
}
