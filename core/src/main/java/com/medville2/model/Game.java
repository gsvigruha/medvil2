package com.medville2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.society.Country;
import com.medville2.model.time.Calendar;

public class Game {

	public static final Artifacts FOUNDER_ARTIFACTS = new Artifacts();
	static {
		FOUNDER_ARTIFACTS.add(Artifacts.LOGS, 20);
		FOUNDER_ARTIFACTS.add(Artifacts.PLANKS, 50);
		FOUNDER_ARTIFACTS.add(Artifacts.BRICKS, 50);

		FOUNDER_ARTIFACTS.add(Artifacts.FOOD, 100);
		FOUNDER_ARTIFACTS.add(Artifacts.IRON, 20);
		FOUNDER_ARTIFACTS.add(Artifacts.CLAY, 50);
		FOUNDER_ARTIFACTS.add(Artifacts.TEXTILE, 50);
		FOUNDER_ARTIFACTS.add(Artifacts.LEATHER, 20);
	}

	private final Calendar calendar;
	private final Terrain terrain;
	private final Country player;
	private final List<Country> opponents;
	private final List<String> availableTownNames;

	public Game(Calendar calendar, Terrain terrain) {
		this.calendar = calendar;
		this.terrain = terrain;
		this.player = new Country(true);
		this.opponents = new ArrayList<>();
		this.availableTownNames = new ArrayList<>();
		this.availableTownNames.addAll(
				ImmutableList.of("Ravenhold", "Drakenshire", "Eldermoor", "Highmere", "Grimthorne", "Vallenheim",
						"Stormwatch", "Blackhollow", "Ironhaven", "Thornwick", "Frosthelm", "Dunharrow", "Goldmere",
						"Shadowfen", "Wolfspire", "Westmere", "Stonebrook", "Gloomhaven", "Ebonford", "Harrowgate"));
		Collections.shuffle(this.availableTownNames);
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void tick() {
		calendar.tick();
		terrain.tick(calendar);
	}

	public Country getPlayer() {
		return player;
	}

	public String nextTownName() {
		return availableTownNames.remove(0);
	}
}
