package com.medville2.control;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.view.buttons.ButtonHelper;

public abstract class Editor {

	protected BitmapFont font = new BitmapFont();

	public abstract void handleClick(Field field);

	public abstract FieldObject getFieldObject();

	public abstract Actor[] getActors();

	protected ImageButton createButton(TextureRegion icon, int x, int y, EventListener listener) {
		ImageButton button = new ImageButton(new TextureRegionDrawable(icon));
		button.setSize(ButtonHelper.BUTTON_LARGE_SX, ButtonHelper.BUTTON_LARGE_SY);
		button.setPosition(x, y);
		button.addListener(listener);
		button.getStyle().checked = ButtonHelper.getInstance().buttonBGSelectedLarge;
		return button;
	}

	protected Label createLabel(int x, int y) {
		Label label = new Label("", new LabelStyle(font, Color.WHITE));
		label.setPosition(x, y);
		return label;
	}

	public String getLabel(Field field) {
		return null;
	}

	public Iterable<Field> getActiveFields() {
		return ImmutableList.of();
	}
}
