package com.medville2.model.building.infra;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;

public class Bridge extends InfraObject {

	public static final FieldObjectType Type = new FieldObjectType("bridge", 1, Bridge.class);

	private final boolean flip;
	
	public Bridge(Field field, boolean flip) {
		super(field, Type);
		this.flip = flip;
	}

	public Bridge(Field field) {
		super(field, Type);
		this.flip = false;
	}
	
	@Override
	public String getName() {
		return "bridge";
	}

	public boolean isFlip() {
		return flip;
	}
}
