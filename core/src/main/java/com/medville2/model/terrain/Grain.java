package com.medville2.model.terrain;

import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;

public class Grain extends TerrainObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("grain", 1, Grain.class);

	public Grain(Field field) {
		super(field, Type, ImmutableList.of(new Yield(field.getCropYield() / 180f, Artifacts.GRAIN, 3)));
	}
}
