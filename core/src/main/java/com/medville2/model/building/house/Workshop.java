package com.medville2.model.building.house;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;

public class Workshop extends BuildingObject {

	public static final FieldObjectType Type = new FieldObjectType("workshop", 1, Workshop.class);

	public Workshop(Field field) {
		super(field, Type);
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		
	}
}