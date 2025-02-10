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
	private Artifacts buyArtifacts;
	private final Artifacts sellArtifacts;
	private boolean toMarket;

	public MarketTask(Townsquare townsquare, Person person, Terrain terrain, Map<String, Integer> buyArtifacts, Map<String, Integer> sellArtifacts) {
		this.townsquare = townsquare;
		this.person = person;
		this.marketField = terrain.getField(townsquare.getI(), townsquare.getJ());
		BuildingObject home = person.getHome();
		this.homeField = terrain.getField(home.getI(), home.getJ());
		this.buyArtifacts = new Artifacts(buyArtifacts);
		this.sellArtifacts = new Artifacts(sellArtifacts);
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
			townsquare.getMarket().sell(sellArtifacts, person.getHome());
			buyArtifacts = townsquare.getMarket().buy(buyArtifacts, person.getHome());
			toMarket = false;
			return false;
		} else {
			person.getHome().getArtifacts().addAll(buyArtifacts);
			return true;
		}
	}
}
