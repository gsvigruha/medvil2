package com.medville2.model.building.house;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Manufacturing;
import com.medville2.model.time.Calendar;

public abstract class Artisan extends BuildingObject {

	private static final long serialVersionUID = 1L;

	protected List<Manufacturing> manufacturing;

	public Artisan(Field field, FieldObjectType type, List<Manufacturing> manufacturing) {
		super(field, type);
		this.manufacturing = manufacturing;
	}

	@Override
	protected Map<String, Integer> artifactsToSell() {
		Map<String, Integer> artifacts = new HashMap<>();
		for (Manufacturing m : manufacturing) {
			for (var o : m.getOutputs().entrySet()) {
				pickArtifact(artifacts, o.getKey(), o.getValue() * 5);
			}
		}
		return artifacts;
	}

	@Override
	protected Map<String, Integer> artifactsToBuy() {
		Map<String, Integer> artifacts = new HashMap<>();
		artifacts.putAll(super.artifactsToBuy());
		for (Manufacturing m : manufacturing) {
			for (var o : m.getInputs().entrySet()) {
				artifacts.put(o.getKey(), o.getValue() * 5);
			}
		}
		return artifacts;
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		super.tick(terrain, calendar);
		
	}

	public List<Manufacturing> getManufacturing() {
		return manufacturing;
	}
}
