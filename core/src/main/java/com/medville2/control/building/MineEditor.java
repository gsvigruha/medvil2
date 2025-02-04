package com.medville2.control.building;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.Mine;

public class MineEditor extends BuildingEditor {

	private final Mine mine;

	public MineEditor(Mine mine, int height, TextureAtlas textureAtlas) {
		this.mine = mine;
	}

	@Override
	public Mine getFieldObject() {
		return mine;
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
		return mine.getArtifacts();
	}
}
