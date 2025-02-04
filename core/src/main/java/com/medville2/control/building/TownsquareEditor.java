package com.medville2.control.building;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.Townsquare;

public class TownsquareEditor extends BuildingEditor {

	private final Townsquare townsquare;

	public TownsquareEditor(Townsquare townsquare, int height, TextureAtlas textureAtlas) {
		this.townsquare = townsquare;
	}

	@Override
	public Townsquare getFieldObject() {
		return townsquare;
	}

	@Override
	public void handleClick(Field field) {
	}

	@Override
	public Iterable<Actor> getActorsImpl() {
		return ImmutableList.of();
	}

	@Override
	protected Artifacts getArtifacts() {
		return townsquare.getArtifacts();
	}
}
