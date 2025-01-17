package com.medville2.model.building.house;

import com.medville2.model.FieldObject;

public abstract class BuildingObject extends FieldObject {

	public BuildingObject(int i, int j) {
		super(i, j);
	}

	@Override
	public boolean isHill() {
		return false;
	}
}
