package com.medville2.model.terrain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.time.Calendar;

public abstract class TerrainObject extends FieldObject {

	private static final long serialVersionUID = 1L;

	protected static class Yield {
		protected float probability;
		protected String artifact;
		protected int quantity;

		protected Yield(float probability, String artifact, int quantity) {
			this.probability = probability;
			this.artifact = artifact;
			this.quantity = quantity;
		}		
	}

	protected List<Yield> yields;

	public TerrainObject(Field field, FieldObjectType type, List<Yield> yields) {
		super(field, type);
		this.yields = yields;
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		
	}

	@Override
	public Map<String, Integer> getYield(Calendar calendar, int numPeople) {
		Map<String, Integer> result = new HashMap<>();
		for (Yield yield : yields) {
			if (Math.random() < yield.probability * numPeople) {
				result.put(yield.artifact, yield.quantity);
			}
		}
		return result;
	}
}
