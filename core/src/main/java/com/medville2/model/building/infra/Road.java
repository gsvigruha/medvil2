package com.medville2.model.building.infra;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;

public class Road extends InfraObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("road", 1, Road.class);

	public Road(Field field) {
		super(field, Type);
	}
}
