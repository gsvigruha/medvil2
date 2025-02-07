package com.medville2.model.terrain;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.time.Calendar;

public class Grain extends TerrainObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("grain", 1, Grain.class);

	public Grain(Field field) {
		super(field, Type);
	}

	@Override
	public Map<String, Integer> getYield(Calendar calendar) {
		if (calendar.getDay() % 180 == 1) {
			return ImmutableMap.of(Artifacts.GRAIN, (int) (field.getCropYield() * 10));
		} else {
			return ImmutableMap.of();
		}
	}
}
