package com.medville2.model.terrain;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.time.Calendar;

public class Fishnet extends TerrainObject {

	private static final FieldObjectType FishnetType = new FieldObjectType("fishnet", 1, Fishnet.class);

	public Fishnet(Field field) {
		super(field, FishnetType);
	}

	@Override
	public Map<String, Integer> getYield(Calendar calendar) {
		if (calendar.getDay() % 30 == 1) {
			return ImmutableMap.of(Artifacts.FISH, 2);
		} else {
			return ImmutableMap.of();
		}
	}
}
