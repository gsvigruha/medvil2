package com.medville2.model.terrain;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.time.Calendar;

public class Sheep extends TerrainObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("sheep", 1, Sheep.class);

	public Sheep(Field field) {
		super(field, Type);
	}

	@Override
	public Map<String, Integer> getYield(Calendar calendar) {
		if (calendar.getDay() == 1 && calendar.getYear() % 3 == 1) {
			return ImmutableMap.of(Artifacts.SHEEP, 1);
		} else {
			return ImmutableMap.of();
		}
	}
}
