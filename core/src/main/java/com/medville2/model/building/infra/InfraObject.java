package com.medville2.model.building.infra;

import com.medville2.model.FieldObject;
import com.medville2.model.Terrain;

public abstract class InfraObject extends FieldObject {

	public InfraObject(int i, int j) {
		super(i, j);
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public boolean isHill() {
		return false;
	}

	@Override
	public void tick(Terrain terrain) {
		
	}
}
