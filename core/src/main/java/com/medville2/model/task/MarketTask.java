package com.medville2.model.task;

import java.io.Serializable;
import java.util.Map;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.house.Townsquare;


public class MarketTask extends Task implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Townsquare townsquare;
	private final Field marketField;
	private final Field homeField;
	private final Map<String, Integer> buyArtifactsAt;
	private final Map<String, Integer> sellArtifactsAt;
	private boolean toMarket;

	public MarketTask(Townsquare townsquare, BuildingObject home, Terrain terrain, Map<String, Integer> buyArtifactsAt, Map<String, Integer> sellArtifactsAt) {
		this.townsquare = townsquare;
		this.marketField = terrain.getField(townsquare.getI(), townsquare.getJ());
		this.homeField = terrain.getField(home.getI(), home.getJ());
		this.buyArtifactsAt = buyArtifactsAt;
		this.sellArtifactsAt = sellArtifactsAt;
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
}
