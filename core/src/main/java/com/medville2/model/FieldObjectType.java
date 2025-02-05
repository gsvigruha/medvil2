package com.medville2.model;

import java.io.Serializable;

public class FieldObjectType implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final int size;
	private final Class<? extends FieldObject> clss;

	public FieldObjectType(String name, int size, Class<? extends FieldObject> clss) {
		this.name = name;
		this.size = size;
		this.clss = clss;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public Class<? extends FieldObject> getClss() {
		return clss;
	}
}
