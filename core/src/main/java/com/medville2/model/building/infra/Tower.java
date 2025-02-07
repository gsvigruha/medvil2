package com.medville2.model.building.infra;

import java.io.Serializable;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;

public class Tower extends Wall implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("tower", 1, Tower.class);

	public Tower(Field field) {
		super(field, Type);
	}
}