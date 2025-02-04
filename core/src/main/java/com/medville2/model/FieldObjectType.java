package com.medville2.model;

public class FieldObjectType {

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
