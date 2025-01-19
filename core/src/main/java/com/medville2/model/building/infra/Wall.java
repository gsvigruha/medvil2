package com.medville2.model.building.infra;

public class Wall extends InfraObject {

	private boolean[] segments;

	public Wall(int i, int j) {
		super(i, j);
		this.segments = new boolean[4];
	}

	@Override
	public String getName() {
		return "wall";
	}

	public void setSegment(int i, boolean exists) {
		this.segments[i] = exists;
	}

	public boolean hasSegment(int i) {
		return segments[i];
	}
}
