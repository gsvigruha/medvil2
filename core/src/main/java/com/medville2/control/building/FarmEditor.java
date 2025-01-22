package com.medville2.control.building;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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

	@Override
	public Actor[] getActors() {
		Label label = new Label("FARM!", new LabelStyle(font, Color.WHITE));
		label.setPosition(20, 120);
		return new Actor[] { label };
	}
}
