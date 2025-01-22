package com.medville2.control;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.medville2.model.Field;
import com.medville2.model.FieldObject;

public abstract class Editor {

	protected BitmapFont font = new BitmapFont();

	public abstract void handleClick(Field field);

	public abstract FieldObject getFieldObject();

	public abstract Actor[] getActors();

}
