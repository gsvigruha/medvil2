package com.medville2.model.building.house;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.artifacts.Manufacturing;
import com.medville2.model.time.Calendar;

public class Mill extends Artisan {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("mill", 1, Mill.class);
	public static final List<Manufacturing> MILL_MANUFACTURE = ImmutableList.of(
			new Manufacturing("woodwork", 30, Map.of(Artifacts.LOGS, 1),
					Map.of(Artifacts.PLANKS, 1)),
			new Manufacturing("milling", 30, Map.of(Artifacts.GRAIN, 1),
					Map.of(Artifacts.FLOUR, 1)),
			new Manufacturing("stonecutting", 30, Map.of(Artifacts.STONE, 1),
					Map.of(Artifacts.BRICKS, 1)));

	public Mill(Field field) {
		super(field, Type, MILL_MANUFACTURE);
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		super.tick(terrain, calendar);
	}

	@Override
	protected Map<String, Integer> artifactsToSell() {
		return ImmutableMap.of();
	}
}