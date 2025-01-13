package com.medville2.model.terrain;

import com.medville2.model.FieldObject;

public abstract class TerrainObject extends FieldObject {

	public TerrainObject(int i, int j) {
		super(i, j);
	}

	@Override
	public int getSize() {
		return 1;
	}
}
