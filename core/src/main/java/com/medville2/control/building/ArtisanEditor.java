package com.medville2.control.building;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.artifacts.Manufacturing;
import com.medville2.model.building.house.Artisan;
import com.medville2.model.building.house.BuildingObject;

public class ArtisanEditor extends BuildingEditor {

	private final Artisan artisan;
	private List<Actor> manufactureActors;

	public ArtisanEditor(Artisan artisan, int height, TextureAtlas textureAtlas) {
		super(height, textureAtlas);
		this.artisan = artisan;
		this.manufactureActors = new ArrayList<>();
		var H = 68;
		var W = 64;
		var S = 48;

		int i = 0;
		for (Manufacturing m : artisan.getManufacturing()) {
			boolean enabled = m.isProfitable(artisan.getTown().getTownsquare().getMarket());
			Label label = createLabel(10, height - 200 + 4 - i * H);
			label.setText(m.getName());
			if (!enabled) {
				label.getColor().a = 0.5f;
			}
			manufactureActors.add(label);

			int j = 0;
			for (var e : m.getInputs().entrySet()) {
				Label artifactLabel = createLabel(12 + j * W, height - 200 - i * H + 36);
				artifactLabel.setText(e.getValue());
				if (!enabled) {
					artifactLabel.getColor().a = 0.5f;
				}
				manufactureActors.add(artifactLabel);

				Image artifactImage = new Image(textureAtlas.findRegion("artifact_" + e.getKey().toLowerCase()));
				artifactImage.setPosition(24 + j * W, height - 200 - i * H);
				artifactImage.setSize(S, S);
				if (!enabled) {
					artifactImage.getColor().a = 0.5f;
				}
				manufactureActors.add(artifactImage);
				j++;
			}
			j = 0;
			for (var e : m.getOutputs().entrySet()) {
				Label artifactLabel = createLabel(248 + j * W, height - 200 - i * H + 36);
				artifactLabel.setText(e.getValue());
				if (!enabled) {
					artifactLabel.getColor().a = 0.5f;
				}
				manufactureActors.add(artifactLabel);

				Image artifactImage = new Image(textureAtlas.findRegion("artifact_" + e.getKey().toLowerCase()));
				artifactImage.setPosition(260 + j * W, height - 200 - i * H);
				artifactImage.setSize(S, S);
				if (!enabled) {
					artifactImage.getColor().a = 0.5f;
				}
				manufactureActors.add(artifactImage);
				j--;
			}
			i++;
		}
	}

	@Override
	protected BuildingObject getFieldObject() {
		return artisan;
	}

	@Override
	protected Iterable<Actor> getActorsImpl() {
		return ImmutableList.copyOf(manufactureActors);
	}

	@Override
	protected Artifacts getArtifacts() {
		return artisan.getArtifacts();
	}

	@Override
	public void handleClick(Field field) {
		
	}
}
