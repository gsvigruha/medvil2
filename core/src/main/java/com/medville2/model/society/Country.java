package com.medville2.model.society;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.medville2.model.Game;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.Townsquare;

public class Country implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Town> towns;

	public Country(boolean player) {
		this.towns = new ArrayList<>();
	}

	public Town foundTown(Townsquare townsquare, String name, Artifacts artifacts, int money, int people, Game game) {
		Town town = new Town(this, townsquare, name);
		townsquare.setTown(town);
		townsquare.getArtifacts().addAll(artifacts);
		townsquare.addMoney(money);
		for (int i = 0; i < people; i++) {
			townsquare.addPerson(new Person(townsquare, game.nextPersonID()), game.getTerrain());
		}
		this.towns.add(town);
		return town;
	}

	public Town firstTown() {
		if (towns.size() > 0) {
			return towns.get(0);
		}
		return null;
	}

	public void tick() {
		
	}
}
