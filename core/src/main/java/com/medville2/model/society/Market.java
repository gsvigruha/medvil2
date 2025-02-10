package com.medville2.model.society;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.task.MarketTask.Batch;

public class Market implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Map<String, Integer> prices;

	public Market() {
		this.prices = new HashMap<>();
		for (String artifact : Artifacts.ARTIFACTS) {
			prices.put(artifact, 1);
		}
	}

	public Integer getPrice(String artifact) {
		return prices.get(artifact);
	}

	public Map<String, Integer> getPrices() {
		return prices;
	}

	public void transact(List<Batch> batches, BuildingObject buyer, BuildingObject seller) {
		
	}
}
