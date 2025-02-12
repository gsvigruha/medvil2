package com.medville2.model.terrain;

import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;

public class Tree extends TerrainObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("tree", 1, Tree.class);

	public enum TreeType {
		GREEN, BLOOMING, SMALL,
	}

	private final TreeType type;

	public Tree(TreeType type, Field field) {
		super(field, Type, ImmutableList.of(new Yield(1f / (365f * 5f), Artifacts.LOGS, 10)));
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
}
