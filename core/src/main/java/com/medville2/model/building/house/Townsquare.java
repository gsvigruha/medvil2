package com.medville2.model.building.house;

import java.util.Map;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.society.Market;
import com.medville2.model.time.Calendar;

public class Townsquare extends BuildingObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("townsquare", 2, Townsquare.class);

	private final Market market;

	public Townsquare(Field field) {
		super(field, Type);
		this.market = new Market();
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		
	}

	public Map<String, Integer> getPrices() {
		return market.getPrices();
	}
}
