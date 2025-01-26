package com.medville2.model.terrain;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.time.Calendar;

public class Grain extends TerrainObject {

	public Grain(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "grain";
	}

	@Override
	public boolean isHill() {
		return false;
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
