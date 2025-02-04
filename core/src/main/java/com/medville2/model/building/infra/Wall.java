package com.medville2.model.building.infra;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;

public class Wall extends InfraObject {

	public static final FieldObjectType Type = new FieldObjectType("wall", 1, Wall.class);

	private boolean[] segments;

	public Wall(Field field) {
		super(field, Type);
		this.segments = new boolean[4];
	}

	public Wall(Field field, FieldObjectType type2) {
		super(field, type2);
		this.segments = new boolean[4];
	}

	public void setSegment(int i, boolean exists) {
		this.segments[i] = exists;
	}

	public boolean hasSegment(int i) {
		return segments[i];
	}
}
