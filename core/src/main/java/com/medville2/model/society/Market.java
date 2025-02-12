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

	public Artifacts buy(Map<String, Integer> maxBuyArtifacts, BuildingObject buyer) {
		Artifacts artifacts = new Artifacts();
		for (Entry<String, Integer> a : maxBuyArtifacts.entrySet()) {
			int fullBatchPrice = prices.get(a.getKey()) * a.getValue();
			if (buyer.getMoney() >= fullBatchPrice) {
				Integer quantity = townsquare.getArtifacts().remove(a.getKey(), a.getValue());
				if (quantity != null) {
					int actualBatchPrice = prices.get(a.getKey()) * quantity;
					artifacts.add(a.getKey(), a.getValue());
					townsquare.addMoney(actualBatchPrice);
					buyer.addMoney(-actualBatchPrice);
				}
			}
		}
		return artifacts;
	}

	public void sell(Artifacts sellerArtifacts, BuildingObject seller) {
		for (Entry<String, Integer> a : sellerArtifacts.iterable()) {
			int price = prices.get(a.getKey()) * a.getValue();
			if (townsquare.getMoney() >= price) {
				townsquare.getArtifacts().add(a.getKey(), a.getValue());
				sellerArtifacts.remove(a.getKey(), a.getValue());
				townsquare.addMoney(-price);
				seller.addMoney(price);
			}
		}
	}
}
