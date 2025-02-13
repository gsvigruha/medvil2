package com.medville2.model.artifacts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;

public class Artifacts implements Serializable {

	private static final long serialVersionUID = 1L;

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

	public static final String TOOLS = "TOOLS";
	public static final String SWORD = "SWORD";
	public static final String BOW = "BOW";
	public static final String ARROWS = "ARROWS";

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
			CLAY,
			TOOLS,
			SWORD,
			BOW,
			ARROWS);

	private final Map<String, Integer> artifacts;

	public Artifacts() {
		this.artifacts = new HashMap<>();
	}

	public Artifacts(Map<String, Integer> artifacts) {
		this.artifacts = new HashMap<>(artifacts);
	}

	public void add(String artifact, int quantity) {
		Integer existing = artifacts.get(artifact);
		if (existing != null) {
			artifacts.put(artifact, existing + quantity);
		} else {
			artifacts.put(artifact, quantity);
		}
	}

	public Integer remove(String artifact, int quantity) {
		Integer existing = artifacts.get(artifact);
		if (existing != null) {
			if (existing >= quantity) {
				artifacts.put(artifact, existing - quantity);
				return quantity;
			} else {
				artifacts.put(artifact, 0);
				return existing;
			}
		}
		return null;
	}

	public Iterable<Entry<String, Integer>> iterable() {
		return artifacts.entrySet();
	}

	public Integer check(String artifact) {
		return artifacts.get(artifact);
	}

	public Artifacts removeAll(Map<String, Integer> artifactsToGet) {
		Map<String, Integer> result = new HashMap<>();
		for (Map.Entry<String, Integer> e : artifactsToGet.entrySet()) {
			result.put(e.getKey(), remove(e.getKey(), e.getValue()));
		}
		return new Artifacts(result);
	}

	public void addAll(Artifacts artifacts) {
		for (Map.Entry<String, Integer> artifact : artifacts.iterable()) {
			add(artifact.getKey(), artifact.getValue());
		}
	}

	public boolean has(String artifact) {
		return artifacts.containsKey(artifact) && artifacts.get(artifact) > 0;
	}

	@Override
	public String toString() {
		return "Artifacts [artifacts=" + artifacts + "]";
	}

	public void addAll(Map<String, Integer> artifacts) {
		for (Map.Entry<String, Integer> artifact : artifacts.entrySet()) {
			add(artifact.getKey(), artifact.getValue());
		}
	}

	public boolean isEmpty() {
		for (Map.Entry<String, Integer> a : artifacts.entrySet()) {
			if (a.getValue() > 0) {
				return false;
			}
		}
		return true;
	}

	public List<String> types() {
		return ImmutableList.copyOf(artifacts.keySet());
	}
}
