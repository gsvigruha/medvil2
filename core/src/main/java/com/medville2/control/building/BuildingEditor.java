package com.medville2.control.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.google.common.collect.ImmutableList;
import com.medville2.control.Editor;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.BuildingObject;

public abstract class BuildingEditor extends Editor {

	protected abstract BuildingObject getFieldObject();

	protected abstract Iterable<Actor> getActorsImpl();

	protected int height;
	protected TextureAtlas textureAtlas;

	public BuildingEditor(int height, TextureAtlas textureAtlas) {
		this.height = height;
		this.textureAtlas = textureAtlas;
	}

	public Iterable<Actor> getActors() {
		Image moneyImage = new Image(textureAtlas.findRegion("coin"));
		moneyImage.setSize(ARTIFACT_SX, ARTIFACT_SY);
		moneyImage.setPosition(10, 560);

		Label moneyLabel = new Label(String.valueOf(getFieldObject().getMoney()), new LabelStyle(font, Color.WHITE));
		moneyLabel.setPosition(80, 580);
		moneyLabel.setAlignment(Align.left | Align.top);

		Image peopleImage = new Image(textureAtlas.findRegion("head"));
		peopleImage.setSize(ARTIFACT_SX, ARTIFACT_SY);
		peopleImage.setPosition(130, 560);

		Label peopleLabel = new Label(String.valueOf(getFieldObject().getNumPeople()), new LabelStyle(font, Color.WHITE));
		peopleLabel.setPosition(200, 580);
		peopleLabel.setAlignment(Align.left | Align.top);

		Label townLabel = headerLabel(
				getFieldObject().getClass().getSimpleName() + " of " + getFieldObject().getTown().getName());
		return ImmutableList.<Actor>builder().addAll(getActorsImpl()).addAll(getArtifactActors()).add(townLabel)
				.add(moneyImage).add(moneyLabel).add(peopleImage).add(peopleLabel).build();
	}

	protected abstract Artifacts getArtifacts();

	protected Map<String, Integer> getPrices() {
		return null;
	}

	public Iterable<Actor> getArtifactActors() {
		List<Actor> elements = new ArrayList<>();

		Image storageImage = new Image(textureAtlas.findRegion("storage"));
		storageImage.setPosition(0, height - 955);
		storageImage.setSize(320, 427);
		elements.add(storageImage);

		Map<String, Integer> prices = getPrices();

		int i = 0;
		for (String artifact : Artifacts.ARTIFACTS) {
			Integer quantity = getArtifacts().get(artifact);
			int x = (i % 4) * ARTIFACT_PX;
			int y = (i / 4) * ARTIFACT_PY;
			Image artifactImage = new Image(textureAtlas.findRegion("artifact_" + artifact.toLowerCase()));
			artifactImage.setPosition(x + 10, height - 600 - y);
			artifactImage.setSize(ARTIFACT_SX, ARTIFACT_SY);
			elements.add(artifactImage);
			if (quantity == null) {
				artifactImage.getColor().a = 0.5f;
			} else {
				Label quantityLabel = new Label(String.valueOf(quantity), new LabelStyle(smallFont, Color.WHITE));
				quantityLabel.setPosition(12 + x, height - 615 - y);
				elements.add(quantityLabel);
			}

			if (prices != null) {
				Label priceLabel = new Label("$" + String.valueOf(prices.get(artifact)),
						new LabelStyle(smallFont, Color.WHITE));
				priceLabel.setPosition(50 + x, height - 615 - y);
				elements.add(priceLabel);
			}
			i++;
		}
		return elements;
	}
}
