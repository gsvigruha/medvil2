package com.medville2.model.artifacts;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;

public class Artifacts {

	public static final String SHEEP = "SHEEP";
	public static final String LOGS = "LOGS";
	public static final String GRAIN = "GRAIN";
	public static final String FISH = "FISH";

	public static final String STONE = "STONE";
	public static final String IRON = "IRON";
	public static final String GOLD = "GOLD";

	public static final String FOOD = "FOOD";
	public static final String PLANKS = "PLANKS";
	public static final String BRICKS = "BRICKS";
	public static final String WOOL = "WOOL";
	public static final String TEXTILE = "TEXTILE";
	public static final String POTTERY = "POTTERY";
	public static final String FLOUR = "FLOUR";
	public static final String LEATHER = "LEATHER";
	public static final String CLAY = "CLAY";

	public static final ImmutableList<String> ARTIFACTS = ImmutableList.of(
			SHEEP,
			LOGS,
			GRAIN,
			FISH,
			STONE,
			IRON,
			GOLD,
			FOOD,
			PLANKS,
			BRICKS,
			WOOL,
			TEXTILE,
			POTTERY,
			FLOUR,
			LEATHER,
			CLAY);

	private final Map<String, Integer> artifacts;

	public Artifacts() {
		this.artifacts = new HashMap<>();
	}

	public void add(String artifact, int quantity) {
		if (artifacts.containsKey(artifact)) {
			artifacts.put(artifact, artifacts.get(artifact) + quantity);
		} else {
			artifacts.put(artifact, quantity);
		}
	}

	public Iterable<Entry<String, Integer>> iterable() {
		return artifacts.entrySet();
	}

	public Integer get(String artifact) {
		return artifacts.get(artifact);
	}

	public void addAll(Artifacts artifacts) {
		for (Map.Entry<String, Integer> artifact : artifacts.iterable()) {
			add(artifact.getKey(), artifact.getValue());
		}
	}
}
