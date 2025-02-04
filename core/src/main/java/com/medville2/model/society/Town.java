package com.medville2.model.society;

import com.medville2.model.building.house.Townsquare;

public class Town {

	private final String name;
	private Country country;
	private Townsquare townsquare;

	public Town(Country country, Townsquare townsquare, String name) {
		this.country = country;
		this.townsquare = townsquare;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Country getCountry() {
		return country;
	}

	public Townsquare getTownsquare() {
		return townsquare;
	}
}
