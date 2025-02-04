package com.medville2.control;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;

public class MenuEditor extends Editor {

	private String title;

	public MenuEditor(String title) {
		this.title = title;
	}

	@Override
	public void handleClick(Field field) {
	}

	@Override
	public Iterable<Actor> getActors() {
		return ImmutableList.of(headerLabel(title));
	}
}
