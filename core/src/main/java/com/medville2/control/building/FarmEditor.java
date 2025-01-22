package com.medville2.control.building;

import com.medville2.control.Editor;
import com.medville2.model.Field;
import com.medville2.model.building.house.Farm;
import com.medville2.model.terrain.Grain;

public class FarmEditor extends Editor {

	private final Farm farm;

	public FarmEditor(Farm farm) {
		this.farm = farm;
	}

	@Override
	public void handleClick(Field field) {
		if (field.getObject() == null) {
			field.setObject(new Grain(field.getI(), field.getJ()));
		}
	}

	@Override
	public Farm getFieldObject() {
		return farm;
	}
}
