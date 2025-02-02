package com.medville2.model.building.house;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;

public class Workshop extends BuildingObject {

	public Workshop(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "workshop";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		
	}
}