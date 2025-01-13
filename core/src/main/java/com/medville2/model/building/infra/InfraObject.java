package com.medville2.model.building.infra;

import com.medville2.model.FieldObject;

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
}
