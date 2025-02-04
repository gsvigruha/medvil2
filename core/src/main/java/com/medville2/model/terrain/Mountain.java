package com.medville2.model.terrain;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;

public class Mountain extends TerrainObject {

	private static final FieldObjectType MountainType = new FieldObjectType("mountain", 2, Mountain.class);

	public Mountain(Field field) {
		super(field, MountainType);
	}
}
