package com.medville2.model.society;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.building.house.BuildingObject;

public class Person {

	public static final int D = 100;

	private int x;
	private int y;

	private BuildingObject home;

	public Person(BuildingObject home) {
		this.home = home;
		this.x = home.getI() * D;
		this.y = home.getJ() * D;
	}

	public Field getField(Terrain terrain) {
		int i = x / D;
		int j = y / D;
		return terrain.getField(i, j);
	}

	public void setHome(BuildingObject home) {
		this.home = home;
	}
}
