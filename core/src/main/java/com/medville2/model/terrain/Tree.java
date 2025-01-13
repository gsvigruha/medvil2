package com.medville2.model.terrain;

public class Tree extends TerrainObject {

	public enum Type {
		GREEN, BLOOMING, SMALL,
	}

	private final Type type;

	public Tree(Type type, int i, int j) {
		super(i, j);
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
}
