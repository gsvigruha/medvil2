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

public class Blacksmith extends Artisan {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("blacksmith", 1, Blacksmith.class);
	public static final List<Manufacturing> BLACKSMITH_MANUFACTURE = ImmutableList.of(
			new Manufacturing("swordmaking", 30, Map.of(Artifacts.PLANKS, 1, Artifacts.IRON, 2, Artifacts.LEATHER, 1),
					Map.of(Artifacts.SWORD, 1)),
			new Manufacturing("toolmaking", 30, Map.of(Artifacts.PLANKS, 1, Artifacts.IRON, 1),
					Map.of(Artifacts.TOOLS, 1)),
			new Manufacturing("bowmaking", 30, Map.of(Artifacts.PLANKS, 1, Artifacts.LEATHER, 1),
					Map.of(Artifacts.BOW, 1)),
			new Manufacturing("arrowmaking", 30, Map.of(Artifacts.PLANKS, 1, Artifacts.IRON, 1),
					Map.of(Artifacts.ARROWS, 25)));

	public Blacksmith(Field field) {
		super(field, Type, BLACKSMITH_MANUFACTURE);
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
