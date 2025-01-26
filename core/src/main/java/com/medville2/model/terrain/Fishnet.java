package com.medville2.model.terrain;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.time.Calendar;

public class Fishnet extends TerrainObject {

	public Fishnet(Field field) {
		super(field);
	}

	@Override
	public String getName() {
		return "fishnet";
	}

	@Override
	public boolean isHill() {
		return false;
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
