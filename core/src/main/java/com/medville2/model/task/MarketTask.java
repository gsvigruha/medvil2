package com.medville2.model.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.house.Townsquare;


public class MarketTask extends Task implements Serializable {

	private static final long serialVersionUID = 1L;

	public static class Batch {
		private final String artifact;
		private final Integer price;
		private final Integer quantity;

		public Batch(String artifact, Integer price, Integer quantity) {
			this.artifact = artifact;
			this.price = price;
			this.quantity = quantity;
		}
	}

	private final Townsquare townsquare;
	private final Field marketField;
	private final Field homeField;
	private final List<Batch> buyArtifacts;
	private final List<Batch> sellArtifacts;
	private boolean toMarket;

	public MarketTask(Townsquare townsquare, BuildingObject home, Terrain terrain, List<Batch> buyArtifacts, List<Batch> sellArtifacts) {
		this.townsquare = townsquare;
		this.marketField = terrain.getField(townsquare.getI(), townsquare.getJ());
		this.homeField = terrain.getField(home.getI(), home.getJ());
		this.buyArtifacts = buyArtifacts;
		this.sellArtifacts = sellArtifacts;
		toMarket = true;
	}

	@Override
	public Field nextDestination() {
		if (toMarket) {
			return marketField;
		} else {
			return homeField;
		}
	}

	@Override
	public boolean arrivedAt(Field field) {
		if (toMarket) {
			toMarket = false;
			return false;
		} else {
			return true;
		}
	}

	public static List<Batch> createBatch(Map<String, Integer> prices, Artifacts artifacts) {
		List<Batch> result = new ArrayList<>(prices.size());
		for (String artifact : prices.keySet()) {
			if (artifacts.has(artifact)) {
				result.add(new Batch(artifact, prices.get(artifact), artifacts.get(artifact, 10)));
			}
		}
		return result;
	}
}
