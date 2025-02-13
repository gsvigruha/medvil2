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

public class Kiln extends Artisan {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("kiln", 1, Kiln.class);
	public static final List<Manufacturing> KILN_MANUFACTURE = ImmutableList.of(
			new Manufacturing("brickmaking", 30, Map.of(Artifacts.LOGS, 1, Artifacts.CLAY, 3),
					Map.of(Artifacts.BRICKS, 3)),
			new Manufacturing("pottery", 30, Map.of(Artifacts.LOGS, 1, Artifacts.CLAY, 2),
					Map.of(Artifacts.POTTERY, 5)));

	public Kiln(Field field) {
		super(field, Type, KILN_MANUFACTURE);
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
