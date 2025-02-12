package com.medville2.model.building.house;

import java.util.HashMap;
import java.util.Map;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.society.Person;
import com.medville2.model.terrain.Hill;
import com.medville2.model.time.Calendar;

public class Mine extends BuildingObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("mine", 1, Mine.class);

	private final Hill hill;

	public Mine(Field field) {
		super(field, Type);
		this.hill = null;
	}

	public Mine(Field field, Hill hill) {
		super(field, Type);
		this.hill = hill;
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		super.tick(terrain, calendar);
		if (hill.getMineral() != null && !hill.isEmpty() && calendar.getHour() == 1) {
			var minerals = hill.getYield(calendar, getNumPeopleHome());
			artifacts.addAll(hill.mine(minerals));
		}
		if (hill.isEmpty()) {
			for (Person person : people) {
				town.getTownsquare().addPerson(person, terrain);
			}
			town.getTownsquare().addMoney(money);
			people.clear();
			terrain.getField(getI(), getJ()).setObject(null);
		}
	}

	@Override
	protected Map<String, Integer> artifactsToSell() {
		Map<String, Integer> artifacts = new HashMap<>();
		pickArtifact(artifacts, Artifacts.GOLD, 10);
		pickArtifact(artifacts, Artifacts.IRON, 10);
		pickArtifact(artifacts, Artifacts.STONE, 10);
		pickArtifact(artifacts, Artifacts.CLAY, 10);
		return artifacts;
	}
}
