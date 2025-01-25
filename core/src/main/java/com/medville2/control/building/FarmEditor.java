package com.medville2.control.building;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.medville2.control.Editor;
import com.medville2.model.Field;
import com.medville2.model.building.house.Farm;
import com.medville2.model.terrain.Grain;

public class FarmEditor extends Editor {

	private enum State {
		GRAIN,
		FISH,
		CATTLE,
		WOOD,
		DESELECT
	}

	private final Farm farm;

	private final ImageButton selectGrainButton;
	private final ImageButton selectFishButton;
	private final ImageButton deselectButton;

	private State state;

	public FarmEditor(Farm farm, int height, TextureAtlas textureAtlas) {
		this.farm = farm;
		this.state = State.GRAIN;
		
		selectGrainButton = createButton(textureAtlas.findRegion("grain"), 20, height - 200, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.GRAIN;
			}
		});
		selectFishButton = createButton(textureAtlas.findRegion("grain"), 20, height - 300, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.FISH;
			}
		});
		deselectButton = createButton(textureAtlas.findRegion("grain"), 20, height - 400, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.DESELECT;
			}
		});
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
		return new Actor[] { selectGrainButton, selectFishButton, deselectButton };
	}
}
