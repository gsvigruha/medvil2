package com.medville2.model.building.house;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Manufacturing;
import com.medville2.model.time.Calendar;

public class Blacksmith extends Artisan {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("blacksmith", 1, Blacksmith.class);
	public static final List<Manufacturing> BLACKSMITH_MANUFACTURE = ImmutableList.of();

	public Blacksmith(Field field) {
		super(field, Type, BLACKSMITH_MANUFACTURE);
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		super.tick(terrain, calendar);
	}

	@Override
	protected Map<String, Integer> artifactsToSell() {
		return ImmutableMap.of();
	}
}
