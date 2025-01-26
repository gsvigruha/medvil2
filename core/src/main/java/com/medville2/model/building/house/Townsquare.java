package com.medville2.model.building.house;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;

public class Townsquare extends BuildingObject {

	public Townsquare(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "townsquare";
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		
	}
}
