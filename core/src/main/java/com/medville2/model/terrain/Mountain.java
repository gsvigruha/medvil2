package com.medville2.model.terrain;

import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;

public class Mountain extends TerrainObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("mountain", 2, Mountain.class);

	public Mountain(Field field) {
		super(field, Type, ImmutableList.of());
	}
}
