package com.medville2.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontHelper {

	private static FontHelper instance;

	private final BitmapFont font;
	private final BitmapFont mapFont;

	private FontHelper() {
		{
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Arial.ttf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameter.size = 18;
			parameter.color = Color.valueOf("D0D0D0");
			font = generator.generateFont(parameter);
			generator.dispose();
		}

		{
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Arial Bold.ttf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameter.size = 24;
			parameter.color = Color.valueOf("002080");
			mapFont = generator.generateFont(parameter);
			generator.dispose();
		}

	}

	public static FontHelper getInstance() {
		if (instance == null) {
			instance = new FontHelper();
		}
		return instance;
	}

	public BitmapFont getFont() {
		return font;
	}

	public BitmapFont getMapFont() {
		return mapFont;
	}

	public int estimateHeight(String text) {
		return (int) ((text.split("\n").length + 1) * font.getLineHeight());
	}
}
