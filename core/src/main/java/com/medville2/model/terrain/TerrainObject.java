package com.medville2.model.terrain;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;

public abstract class TerrainObject extends FieldObject {

	public TerrainObject(Field field) {
		super(field);
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		
	}
}
