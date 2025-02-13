package com.medville2.control.building;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.Artisan;
import com.medville2.model.building.house.BuildingObject;

public class ArtisanEditor extends BuildingEditor {

	private final Artisan artisan;

	public ArtisanEditor(Artisan artisan, int height, TextureAtlas textureAtlas) {
		super(height, textureAtlas);
		this.artisan = artisan;
	}

	@Override
	protected BuildingObject getFieldObject() {
		return artisan;
	}

	@Override
	protected Iterable<Actor> getActorsImpl() {
		return ImmutableList.of();
	}

	@Override
	protected Artifacts getArtifacts() {
		return artisan.getArtifacts();
	}

	@Override
	public void handleClick(Field field) {
		
	}
}
