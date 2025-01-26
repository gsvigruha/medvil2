package com.medville2.model.terrain;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.time.Calendar;

public class Sheep extends TerrainObject {

	public Sheep(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "sheep";
	}

	@Override
	public boolean isHill() {
		return false;
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
