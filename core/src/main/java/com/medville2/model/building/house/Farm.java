package com.medville2.model.building.house;

import java.util.HashMap;
import java.util.Map;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.time.Calendar;

public class Farm extends BuildingObject {

	private static final int MAX_CAPACITY = 20;

	public static enum State {
		GRAIN, FISH, CATTLE, WOOD, DESELECT
	}

	private Map<Field, State> fields;
	private int capacityUsed;

	public Farm(Field field) {
		super(field);
		this.fields = new HashMap<>();
	}

	public boolean hasCapacity(Field field) {
		return getDistance(field) + capacityUsed <= MAX_CAPACITY;
	}

	private void computeCapacity() {
		capacityUsed = 0;
		for (Field field : fields.keySet()) {
			int distance = getDistance(field);
			capacityUsed += distance;
		}
	}

	private int getDistance(Field field) {
		return Math.abs(field.getI() - getI()) + Math.abs(field.getJ() - getJ());
	}

	public boolean addField(Field field, State state) {
		fields.put(field, state);
		computeCapacity();
		if (capacityUsed > MAX_CAPACITY) {
			fields.remove(field);
			computeCapacity();
			return false;
		}
		return true;
	}

	public boolean removeField(Field field) {
		boolean removed = fields.remove(field) != null;
		computeCapacity();
		return removed;
	}

	public boolean hasField(Field field) {
		return fields.containsKey(field);
	}

	public Iterable<Field> getFields() {
		return fields.keySet();
	}

	@Override
	public String getName() {
		return "farm";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		if (calendar.getHour() == 1) {
			for (Map.Entry<Field, State> field : fields.entrySet()) {
				for (Map.Entry<String, Integer> yield : field.getKey().getObject().getYield(calendar).entrySet()) {
					artifacts.add(yield.getKey(), yield.getValue());
				}
			}
		}
	}

	public float getUsedCapacity() {
		return (float) capacityUsed / (float) MAX_CAPACITY;
	}

	public Artifacts getArtifacts() {
		return artifacts;
	}
}
