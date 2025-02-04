package com.medville2.model.terrain;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.time.Calendar;

public class Tree extends TerrainObject {

	private static final FieldObjectType TreeType = new FieldObjectType("tree", 1, Tree.class);

	public enum Type {
		GREEN, BLOOMING, SMALL,
	}

	private final Type type;

	public Tree(Type type, Field field) {
		super(field, TreeType);
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
	public Map<String, Integer> getYield(Calendar calendar) {
		if (calendar.getDay() == 1 && calendar.getYear() % 5 == 1) {
			return ImmutableMap.of(Artifacts.LOGS, 10);
		} else {
			return ImmutableMap.of();
		}
	}
}
