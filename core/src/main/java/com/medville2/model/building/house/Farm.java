package com.medville2.model.building.house;

import java.util.HashSet;
import java.util.Set;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.terrain.Grain;

public class Farm extends BuildingObject {

	private Set<Field> fields;

	public Farm(int i, int j) {
		super(i, j);
		this.fields = new HashSet<>();
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
	public String getLabel(Field field) {
		if (field.getObject() == null && field.getCropYield() > 0) {
			return String.format("%.0f", field.getCropYield() * 100) + "%";
		}
		return null;
	}
}
