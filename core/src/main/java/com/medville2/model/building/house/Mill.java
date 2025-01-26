package com.medville2.model.building.house;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;

public class Mill extends BuildingObject {

	public Mill(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "mill";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		
	}
}