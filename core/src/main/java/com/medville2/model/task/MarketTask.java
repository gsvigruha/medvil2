package com.medville2.model.task;

import java.io.Serializable;
import java.util.Map;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.house.Townsquare;
import com.medville2.model.society.Person;


public class MarketTask extends Task implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Townsquare townsquare;
	private final Person person;
	private final Field marketField;
	private final Field homeField;
	private final Map<String, Integer> maxBuyArtifacts;
	private final Map<String, Integer> maxSellArtifacts;
	private Artifacts buyArtifacts;
	private Artifacts sellArtifacts;
	private boolean toMarket;

	public MarketTask(Townsquare townsquare, Person person, Terrain terrain, Map<String, Integer> maxBuyArtifacts, Map<String, Integer> maxSellArtifacts) {
		this.townsquare = townsquare;
		this.person = person;
		this.marketField = terrain.getField(townsquare.getI(), townsquare.getJ());
		BuildingObject home = person.getHome();
		this.homeField = terrain.getField(home.getI(), home.getJ());
		this.maxBuyArtifacts = maxBuyArtifacts;
		this.maxSellArtifacts = maxSellArtifacts;
		toMarket = true;
	}

	@Override
	public void start() {
		sellArtifacts = new Artifacts(maxSellArtifacts);
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
			townsquare.getMarket().sell(sellArtifacts, person.getHome());
			buyArtifacts = townsquare.getMarket().buy(maxBuyArtifacts, person.getHome());
			toMarket = false;
			return false;
		} else {
			person.getHome().getArtifacts().addAll(buyArtifacts);
			return true;
		}
	}
}
