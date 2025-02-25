package com.medville2.view.terrain;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.view.Renderer;

public class FieldRenderer {

	private TextureAtlas textureAtlas;
	private TextureRegion grass;
	private TextureRegion water;
	private TextureRegion rock;
	private TextureRegion river;

	public FieldRenderer(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
		this.grass = textureAtlas.findRegion("grass");
		this.water = textureAtlas.findRegion("water");
		this.rock = textureAtlas.findRegion("rock");
		this.river = textureAtlas.findRegion("river");
	}

	public void renderField(Field field, int x, int y, SpriteBatch batch, int zoomLevel) {
		final Sprite sprite;
		if (field.getType() == Field.Type.GRASS) {
			sprite = new Sprite(grass);
		} else if (field.getType() == Field.Type.WATER) {
			sprite = new Sprite(water);
		} else if (field.getType() == Field.Type.RIVER) {
			sprite = new Sprite(river);
		} else if (field.getType() == Field.Type.ROCK) {
			sprite = new Sprite(rock);
		} else {
			sprite = null;
		}
		sprite.setSize(Terrain.DX, Terrain.DY);
		sprite.translate(x, y);
		sprite.draw(batch);

		if (zoomLevel == Renderer.ZOOM_LEVEL_CLOSE && (field.getType() == Field.Type.WATER || field.getType() == Field.Type.RIVER)) {
			long phase = ((System.currentTimeMillis() / 200) + field.getI() * 3 + field.getJ()) % 8;
			Sprite sparkSprite = new Sprite(textureAtlas.findRegion("water_sparkle_" + phase));
			sparkSprite.setSize(Terrain.DX, Terrain.DY);
			sparkSprite.translate(x, y);
			sparkSprite.draw(batch);
		}
	}
}
