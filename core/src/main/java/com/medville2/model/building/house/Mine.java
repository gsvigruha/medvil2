package com.medville2.model.building.house;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.terrain.Hill;
import com.medville2.model.time.Calendar;

public class Mine extends BuildingObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("mine", 1, Mine.class);

	private final Hill hill;

	public Mine(Field field) {
		super(field, Type);
		this.hill = null;
	}

	public Mine(Field field, Hill hill) {
		super(field, Type);
		this.hill = hill;
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		if (hill.getMineral() != null && !hill.isEmpty() && calendar.getHour() == 1) {
			artifacts.add(hill.getMineral(), hill.mine());
		}
	}
}
