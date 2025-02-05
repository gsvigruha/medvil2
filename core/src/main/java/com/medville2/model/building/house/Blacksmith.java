package com.medville2.model.building.house;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;

public class Blacksmith extends BuildingObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("blacksmith", 1, Blacksmith.class);

	public Blacksmith(Field field) {
		super(field, Type);
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		
	}
}
