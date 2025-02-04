package com.medville2.control.building;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.google.common.collect.ImmutableList;
import com.medville2.control.Editor;
import com.medville2.model.building.house.BuildingObject;

public abstract class BuildingEditor extends Editor {

	public abstract BuildingObject getFieldObject();

	protected abstract Iterable<Actor> getActorsImpl();

	public Iterable<Actor> getActors() {
		Label townLabel = new Label(getFieldObject().getClass().getSimpleName() + " of " + getFieldObject().getTown().getName(), new LabelStyle(font, Color.WHITE));
		townLabel.setPosition(10, 960);
		townLabel.setAlignment(Align.left | Align.top);
		return ImmutableList.<Actor>builder().addAll(getActorsImpl()).add(townLabel).build();
	}
}
