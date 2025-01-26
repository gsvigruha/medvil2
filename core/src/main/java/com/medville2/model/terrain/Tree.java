package com.medville2.model.terrain;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.time.Calendar;

public class Tree extends TerrainObject {

	public enum Type {
		GREEN, BLOOMING, SMALL,
	}

	private final Type type;

	public Tree(Type type, Field field) {
		super(field);
		this.type = type;
	}

	@Override
	public String getName() {
		switch (type) {
		case GREEN:
			return "tree";
		case BLOOMING:
			return "tree_cherry";
		case SMALL:
			return "tree_small";
		}
		return "tree";
	}

	@Override
	public boolean isHill() {
		return false;
	}

	@Override
	public Map<String, Integer> getYield(Calendar calendar) {
		if (calendar.getDay() == 1 && calendar.getYear() % 5 == 1) {
			return ImmutableMap.of(Artifacts.LOG, 10);
		} else {
			return ImmutableMap.of();
		}
	}
}
