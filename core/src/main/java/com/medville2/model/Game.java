package com.medville2.model;

import java.util.ArrayList;
import java.util.List;

import com.medville2.model.society.Country;
import com.medville2.model.time.Calendar;

public class Game {

	private final Calendar calendar;
	private final Terrain terrain;
	private final Country player;
	private final List<Country> opponents;

	public Game(Calendar calendar, Terrain terrain) {
		this.calendar = calendar;
		this.terrain = terrain;
		this.player = new Country(true);
		this.opponents = new ArrayList<>();
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
}
