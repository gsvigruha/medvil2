package com.medville2.model.building.house;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.society.Town;

public abstract class BuildingObject extends FieldObject {

	private static final long serialVersionUID = 1L;

	protected final Artifacts artifacts;
	protected int money;
	protected Town town;

	public BuildingObject(Field field, FieldObjectType type) {
		super(field, type);
		this.artifacts = new Artifacts();
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
