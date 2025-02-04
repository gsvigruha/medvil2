package com.medville2.model.society;

import java.util.ArrayList;
import java.util.List;

import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.Townsquare;

public class Country {

	private List<Town> towns;

	public Country(boolean player) {
		this.towns = new ArrayList<>();
	}

	public Town foundTown(Townsquare townsquare, String name, Artifacts artifacts) {
		Town town = new Town(this, townsquare, name);
		townsquare.setTown(town);
		townsquare.getArtifacts().addAll(artifacts);
		this.towns.add(town);
		return town;
	}
}
