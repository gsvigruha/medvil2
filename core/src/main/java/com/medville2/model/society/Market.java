package com.medville2.model.society;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.house.Townsquare;

public class Market implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Map<String, Integer> prices;
	private final Townsquare townsquare;

	public Market(Townsquare townsquare) {
		this.prices = new HashMap<>();
		this.townsquare = townsquare;
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

	public void sell(Artifacts artifacts, BuildingObject buyer) {
		for (Entry<String, Integer> a : artifacts.iterable()) {
			int price = prices.get(a.getKey()) * a.getValue();
			if (townsquare.getMoney() >= price) {
				townsquare.getArtifacts().add(a.getKey(), a.getValue());
				artifacts.remove(a.getKey(), a.getValue());
				townsquare.addMoney(-price);
				buyer.addMoney(price);
			}
		}
	}
}
