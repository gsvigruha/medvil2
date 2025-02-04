package com.medville2.model.building.house;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.society.Town;

public abstract class BuildingObject extends FieldObject {

	protected final Artifacts artifacts;
	protected int money;
	protected Town town;

	public BuildingObject(Field field) {
		super(field);
		this.artifacts = new Artifacts();
	}

	@Override
	public boolean isHill() {
		return false;
	}

	public Artifacts getArtifacts() {
		return artifacts;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}
}
