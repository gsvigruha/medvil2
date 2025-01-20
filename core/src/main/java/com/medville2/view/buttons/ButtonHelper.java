package com.medville2.view.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ButtonHelper {

	public static final int BUTTON_LARGE_SX = 128;
	public static final int BUTTON_LARGE_SY = 128;
	public static final int BUTTON_SMALL_SX = 64;
	public static final int BUTTON_SMALL_SY = 64;

	public final Drawable buttonBGSelectedLarge;
	public final Drawable buttonBGSelectedSmall;
	public final InputEvent touchDownEvent;
	public final InputEvent touchUpEvent;

	public ButtonHelper() {
		Pixmap labelColor = new Pixmap(BUTTON_LARGE_SX, BUTTON_LARGE_SY, Pixmap.Format.RGB888);
		labelColor.setColor(Color.valueOf("40A020"));
		labelColor.fill();
		buttonBGSelectedLarge = new Image(new Texture(labelColor)).getDrawable();

		labelColor = new Pixmap(BUTTON_SMALL_SX, BUTTON_SMALL_SY, Pixmap.Format.RGB888);
		labelColor.setColor(Color.valueOf("40A020"));
		labelColor.fill();
		buttonBGSelectedSmall = new Image(new Texture(labelColor)).getDrawable();

		touchDownEvent = new InputEvent();
		touchDownEvent.setType(InputEvent.Type.touchDown);
		touchUpEvent = new InputEvent();
		touchUpEvent.setType(InputEvent.Type.touchUp);
	}
	
}
