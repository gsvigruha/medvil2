package com.medville2.model.building.house;

import java.util.HashMap;
import java.util.Map;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.terrain.Tree;
import com.medville2.model.terrain.Tree.TreeType;
import com.medville2.model.time.Calendar;

public class Farm extends BuildingObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("farm", 1, Farm.class);

	private static final int MAX_CAPACITY = 20;

	public static enum State {
		GRAIN, FISH, CATTLE, WOOD, DESELECT
	}

	private Map<Field, State> fields;
	private int capacityUsed;
	private final TreeType treeType;

	public Farm(Field field) {
		super(field, Type);
		this.fields = new HashMap<>();
		this.treeType = Tree.randomType();
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
		super.tick(terrain, calendar);
		int numPeople = getNumPeopleHome();
		if (calendar.getHour() == 1) {
			for (Field field : fields.keySet()) {
				artifacts.addAll(field.getObject().getYield(calendar, numPeople));
			}
		}
	}

	public float getUsedCapacity() {
		return (float) capacityUsed / (float) MAX_CAPACITY;
	}

	@Override
	protected Map<String, Integer> artifactsToSell() {
		Map<String, Integer> artifacts = new HashMap<>();
		pickArtifact(artifacts, Artifacts.GRAIN, 10);
		pickArtifact(artifacts, Artifacts.FISH, 10);
		pickArtifact(artifacts, Artifacts.SHEEP, 2);
		pickArtifact(artifacts, Artifacts.WOOL, 10);
		pickArtifact(artifacts, Artifacts.LOGS, 10);
		return artifacts;
	}

	public TreeType getTreeType() {
		return treeType;
	}	
}
