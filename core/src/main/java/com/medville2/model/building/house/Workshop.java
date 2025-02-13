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

public class Workshop extends Artisan {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("workshop", 1, Workshop.class);
	public static final List<Manufacturing> WORKSHOP_MANUFACTURE = ImmutableList.of(
			new Manufacturing("woodwork", 90, Map.of(Artifacts.LOGS, 1),
					Map.of(Artifacts.PLANKS, 1)),
			new Manufacturing("stonecutting", 90, Map.of(Artifacts.STONE, 1),
					Map.of(Artifacts.BRICKS, 1)));

	public Workshop(Field field) {
		super(field, Type, WORKSHOP_MANUFACTURE);
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