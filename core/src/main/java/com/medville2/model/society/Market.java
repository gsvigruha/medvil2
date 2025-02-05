package com.medville2.model.society;

import java.util.HashMap;
import java.util.Map;

import com.medville2.model.artifacts.Artifacts;

public class Market {

	private final Map<String, Integer> prices;

	public Market() {
		this.prices = new HashMap<>();
		for (String artifact : Artifacts.ARTIFACTS) {
			prices.put(artifact, 10);
		}
	}

	public Integer getPrice(String artifact) {
		return prices.get(artifact);
	}

	public Map<String, Integer> getPrices() {
		return prices;
	}
}
