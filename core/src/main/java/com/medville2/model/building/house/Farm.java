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

	private void computeCapacity() {
		capacityUsed = 0;
		for (Field field : fields) {
			int distance = Math.abs(field.getI() - getI()) + Math.abs(field.getJ() - getJ());
			capacityUsed += distance;
		}
	}

	public boolean addField(Field field) {
		fields.add(field);
		computeCapacity();
		if (capacityUsed > MAX_CAPACITY) {
			fields.remove(field);
			return false;
		}
		return true;
	}

	public boolean removeField(Field field) {
		return fields.remove(field);
	}

	public boolean hasField(Field field) {
		return fields.contains(field);
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
