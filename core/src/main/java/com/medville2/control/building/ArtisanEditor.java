package com.medville2.control.building;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.artifacts.Manufacturing;
import com.medville2.model.building.house.Artisan;
import com.medville2.model.building.house.BuildingObject;

public class ArtisanEditor extends BuildingEditor {

	private final Artisan artisan;
	private List<Label> manufactureLabels;

	public ArtisanEditor(Artisan artisan, int height, TextureAtlas textureAtlas) {
		super(height, textureAtlas);
		this.artisan = artisan;
		this.manufactureLabels = new ArrayList<>();

		int i = 0;
		for (Manufacturing m : artisan.getManufacturing()) {
			Label label = createLabel(10, height - 200 - i * 24);
			label.setText(m.getName());
			manufactureLabels.add(label);
			i++;
		}
	}

	@Override
	protected BuildingObject getFieldObject() {
		return artisan;
	}

	@Override
	protected Iterable<Actor> getActorsImpl() {
		return ImmutableList.copyOf(manufactureLabels);
	}

	@Override
	protected Artifacts getArtifacts() {
		return artisan.getArtifacts();
	}

	@Override
	public void handleClick(Field field) {
		
	}
}
