package com.medville2.model.society;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Country {

	private static final List<String> PLAYER_CITY_NAMES = ImmutableList.of("Ravenhold", "Drakenshire", "Eldermoor",
			"Highmere", "Grimthorne", "Vallenheim", "Stormwatch", "Blackhollow", "Ironhaven", "Thornwick", "Frosthelm",
			"Dunharrow", "Goldmere", "Shadowfen", "Wolfspire", "Westmere", "Stonebrook", "Gloomhaven", "Ebonford",
			"Harrowgate");
	private static final List<String> OPPONENT_CITY_NAMES = ImmutableList.of("Ravenhold", "Drakenshire", "Eldermoor",
			"Highmere", "Grimthorne", "Vallenheim", "Stormwatch", "Blackhollow", "Ironhaven", "Thornwick", "Frosthelm",
			"Dunharrow", "Goldmere", "Shadowfen", "Wolfspire", "Westmere", "Stonebrook", "Gloomhaven", "Ebonford",
			"Harrowgate");

	private List<Town> towns;
	private List<String> availableCityNames;

	public Country(boolean player) {
		this.towns = new ArrayList<>();
		this.availableCityNames = new ArrayList<>();
		if (player) {
			this.availableCityNames.addAll(PLAYER_CITY_NAMES);
		} else {
			this.availableCityNames.addAll(OPPONENT_CITY_NAMES);
		}
	}

	public String pickCityName() {
		return availableCityNames.remove(0);
	}
}
