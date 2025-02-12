package com.medville2.model.terrain;

import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;

public class Sheep extends TerrainObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("sheep", 1, Sheep.class);

	public Sheep(Field field) {
		super(field, Type, ImmutableList.of(new Yield(1f / (365f * 3f), Artifacts.SHEEP, 1)));
	}
}
