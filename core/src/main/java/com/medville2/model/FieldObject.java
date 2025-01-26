package com.medville2.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.time.Calendar;

public abstract class FieldObject {

	protected final Field field;

	public FieldObject(Field field) {
		this.field = field;
	}

	public int getI() {
		return field.getI();
	}

	public int getJ() {
		return field.getJ();
	}

	public abstract String getName();

	public abstract int getSize();

	public abstract boolean isHill();

	public boolean isFlip() {
		return false;
	}

	public abstract void tick(Terrain terrain, Calendar calendar);

	public Map<String, Integer> getYield(Calendar calendar) {
		return ImmutableMap.of();
	}
}
