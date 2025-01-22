package com.medville2.model.building.house;

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
		
	}

	@Override
	public void handleClick(Field field) {
		if (field.getObject() == null) {
			field.setObject(new Grain(field.getI(), field.getJ()));
		}
	}

	@Override
	public String getLabel(Field field) {
		if (field.getObject() == null && field.getCropYield() > 0) {
			return String.format("%.0f", field.getCropYield() * 100) + "%";
		}
		return null;
	}
}
