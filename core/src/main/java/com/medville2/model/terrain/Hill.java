package com.medville2.model.terrain;

import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;
import com.medville2.model.artifacts.Artifacts;

public class Hill extends TerrainObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("hill", 1, Hill.class);

	private Artifacts artifacts;

	public Hill(Field field, String mineral, int quantity) {
		super(field, Type, ImmutableList.of(new Yield(1f/30f, mineral, 1)));
		this.artifacts = new Artifacts();
		if (mineral != null && quantity > 0) {
			artifacts.add(mineral, quantity);
		}
	}

	public boolean isEmpty() {
		return artifacts.isEmpty();
	}

	public Artifacts mine(Map<String, Integer> minerals) {
		return artifacts.removeAll(minerals);
	}

	public String getMineral() {
		return artifacts.isEmpty() ? null : artifacts.types().get(0);
	}

	public int getQuantity() {
		return artifacts.isEmpty() ? 0 : artifacts.check(artifacts.types().get(0));
	}
}
