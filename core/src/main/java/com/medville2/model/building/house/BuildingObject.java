package com.medville2.model.building.house;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.artifacts.Artifacts;

public abstract class BuildingObject extends FieldObject {

	protected final Artifacts artifacts;
	protected int money;

	public BuildingObject(Field field) {
		super(field);
		this.artifacts = new Artifacts();
	}

	@Override
	public boolean isHill() {
		return false;
	}
}
