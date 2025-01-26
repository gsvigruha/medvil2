package com.medville2.model.building.infra;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;

public abstract class InfraObject extends FieldObject {

	public InfraObject(Field field) {
		super(field);
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
	public void tick(Terrain terrain, Calendar calendar) {
		
	}
}
