package com.medville2.model.terrain;

import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;

public class Fishnet extends TerrainObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("fishnet", 1, Fishnet.class);

	public Fishnet(Field field) {
		super(field, Type, ImmutableList.of(new Yield(1f/90f, Artifacts.FISH, 1)));
	}
}
