package com.medville2.control;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

	public String getLabel(Field field) {
		return null;
	}
}
