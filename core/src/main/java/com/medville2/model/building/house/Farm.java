package com.medville2.model.building.house;

import java.util.HashSet;
import java.util.Set;

import com.medville2.model.Field;
import com.medville2.model.Terrain;

public class Farm extends BuildingObject {

	private static final int MAX_CAPACITY = 10;

	private Set<Field> fields;
	private int capacityUsed;

	public Farm(int i, int j) {
		super(i, j);
		this.fields = new HashSet<>();
	}

	public boolean hasCapacity(Field field) {
		return getDistance(field) + capacityUsed <= MAX_CAPACITY;
	}

	private void computeCapacity() {
		capacityUsed = 0;
		for (Field field : fields) {
			int distance = getDistance(field);
			capacityUsed += distance;
		}
	}

	private int getDistance(Field field) {
		return Math.abs(field.getI() - getI()) + Math.abs(field.getJ() - getJ());
	}

	public boolean addField(Field field) {
		fields.add(field);
		computeCapacity();
		if (capacityUsed > MAX_CAPACITY) {
			fields.remove(field);
			computeCapacity();
			return false;
		}
		return true;
	}

	public boolean removeField(Field field) {
		boolean removed = fields.remove(field);
		computeCapacity();
		return removed;
	}

	public boolean hasField(Field field) {
		return fields.contains(field);
	}

	public Iterable<Field> getFields() {
		return fields;
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
	public void tick(Terrain terrain) {

	}
}
