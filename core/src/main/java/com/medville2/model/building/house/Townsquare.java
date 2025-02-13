package com.medville2.model.building.house;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Game;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.society.Market;
import com.medville2.model.society.Person;
import com.medville2.model.time.Calendar;

public class Townsquare extends BuildingObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("townsquare", 2, Townsquare.class);
	public static final int MAX_PEOPLE = 25;
	public static final float POP_RATE = 1f / 90f;

	private final Market market;

	public Townsquare(Field field) {
		super(field, Type);
		this.market = new Market(this);
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		super.tick(terrain, calendar);
		if (getNumPeople() < MAX_PEOPLE && calendar.getHour() == 1 && Math.random() < POP_RATE
				&& artifacts.check(Artifacts.FOOD) > getNumPeople()) {
			addPerson(new Person(this, Game.globals().nextPersonID()), terrain);
		}
	}

	public Map<String, Integer> getPrices() {
		return market.getPrices();
	}

	@Override
	protected Map<String, Integer> artifactsToSell() {
		return ImmutableMap.of();
	}

	public Market getMarket() {
		return market;
	}
}
