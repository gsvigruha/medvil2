package com.medville2.model.building.infra;

public class Bridge extends InfraObject {

	private final boolean flip;
	
	public Bridge(int i, int j, boolean flip) {
		super(i, j);
		this.flip = flip;
	}

	public Bridge(int i, int j) {
		super(i, j);
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
