package com.medville2.model.building.infra;

import com.medville2.model.Field;

public class Bridge extends InfraObject {

	private final boolean flip;
	
	public Bridge(Field field, boolean flip) {
		super(field);
		this.flip = flip;
	}

	public Bridge(Field field) {
		super(field);
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
