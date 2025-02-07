package com.medville2.model.terrain;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;

public abstract class TerrainObject extends FieldObject {

	private static final long serialVersionUID = 1L;

	public TerrainObject(Field field, FieldObjectType type) {
		super(field, type);
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		
	}
}
