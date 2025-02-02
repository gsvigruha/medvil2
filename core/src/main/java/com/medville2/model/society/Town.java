package com.medville2.model.society;

import com.medville2.model.building.house.Townsquare;

public class Town {

	private final String name;
	private Country country;
	private Townsquare townsquare;

	public Town(Country country, Townsquare townsquare) {
		this.country = country;
		this.townsquare = townsquare;
		this.name = country.pickCityName();
	}
}
