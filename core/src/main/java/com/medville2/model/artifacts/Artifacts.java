package com.medville2.model.artifacts;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Artifacts {

	public static final String SHEEP = "SHEEP";
	public static final String LOGS = "LOGS";
	public static final String GRAIN = "GRAIN";
	public static final String FISH = "FISH";

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
}
