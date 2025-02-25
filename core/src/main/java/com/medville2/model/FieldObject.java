package com.medville2.model;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.time.Calendar;

public abstract class FieldObject implements Serializable {

	private static final long serialVersionUID = 1L;

	protected final Field field;
	protected final FieldObjectType type;

	public FieldObject(Field field, FieldObjectType type) {
		this.field = field;
		this.type = type;
	}

	public int getI() {
		return field.getI();
	}

	public int getJ() {
		return field.getJ();
	}

	public FieldObjectType getType() {
		return type;
	}

	public String getName() {
		return getType().getName();
	}

	public int getSize() {
		return getType().getSize();
	}

	public boolean isFlip() {
		return false;
	}

	public abstract void tick(Terrain terrain, Calendar calendar);

	public Map<String, Integer> getYield(Calendar calendar, int numPeople) {
		return ImmutableMap.of();
	}
}
