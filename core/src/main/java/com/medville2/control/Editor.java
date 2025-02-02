package com.medville2.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.view.buttons.ButtonHelper;

public abstract class Editor {

	public static final int ARTIFACT_SX = 64;
	public static final int ARTIFACT_SY = 64;
	public static final int ARTIFACT_PX = 72;
	public static final int ARTIFACT_PY = 80;

	protected BitmapFont font;

	public abstract void handleClick(Field field);

	public abstract FieldObject getFieldObject();

	public abstract Actor[] getActors();

	public Editor() {
		this.font = FontHelper.getInstance().getFont();
	}

	protected ImageButton createButton(TextureRegion icon, int x, int y, EventListener listener) {
		ImageButton button = new ImageButton(new TextureRegionDrawable(icon));
		button.setSize(ButtonHelper.BUTTON_SMALL_SX, ButtonHelper.BUTTON_SMALL_SY);
		button.setPosition(x, y);
		button.addListener(listener);
		button.getStyle().checked = ButtonHelper.getInstance().buttonBGSelectedLarge;
		return button;
	}

	protected Label createLabel(int x, int y) {
		Label label = new Label("", new LabelStyle(font, Color.WHITE));
		label.setPosition(x, y);
		label.setAlignment(Align.left | Align.top);
		return label;
	}

	public String getLabel(Field field) {
		return null;
	}

	public Iterable<Field> getActiveFields() {
		return ImmutableList.of();
	}

	protected abstract Artifacts getArtifacts();

	public Iterable<Actor> getArtifactActors(int height, TextureAtlas textureAtlas) {
		List<Actor> elements = new ArrayList<>();

		Image storageImage = new Image(textureAtlas.findRegion("storage"));
		storageImage.setPosition(0, height - 850);
		storageImage.setSize(320, 320);
		elements.add(storageImage);

		int i = 0;
		for (Map.Entry<String, Integer> artifact : getArtifacts().iterable()) {
			int x = (i % 4) * ARTIFACT_PX;
			int y = (i / 4) * ARTIFACT_PY;
			Image artifactImage = new Image(textureAtlas.findRegion("artifact_" + artifact.getKey().toLowerCase()));
			artifactImage.setPosition(x + 10, height - 600 - y);
			artifactImage.setSize(ARTIFACT_SX, ARTIFACT_SY);
			elements.add(artifactImage);
			Label quantityLabel = new Label(String.valueOf(artifact.getValue()), new LabelStyle(font, Color.WHITE));
			quantityLabel.setPosition(8 + x, height - 615 - y);
			elements.add(quantityLabel);
			i++;
		}
		return elements;
	}
}
