package com.medville2.model.society;

import java.util.ArrayList;
import java.util.List;

import com.medville2.model.building.house.Townsquare;

public class Country {

	private List<Town> towns;

	public Country(boolean player) {
		this.towns = new ArrayList<>();
	}

	public Town foundTown(Townsquare townsquare, String name) {
		Town town = new Town(this, townsquare, name);
		townsquare.setTown(town);
		this.towns.add(town);
		return town;
	}
}
