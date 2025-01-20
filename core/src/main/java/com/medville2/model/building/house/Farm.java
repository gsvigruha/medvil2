package com.medville2.model.building.house;

import java.util.Random;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.terrain.Grain;

public class Farm extends BuildingObject {

	public Farm(int i, int j) {
		super(i, j);
	}

	@Override
	public String getName() {
		return "farm";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void tick(Terrain terrain) {
		Random rand = new Random();
		if (Math.random() < 0.01) {
			Field field = terrain.getField(getI() + rand.nextInt(5) - 2, getJ() + rand.nextInt(5) - 2);
			if (field != null && field.getObject() == null) {
				field.setObject(new Grain(field.getI(), field.getJ()));
			}
		}
	}
}
