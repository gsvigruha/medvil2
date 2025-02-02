package com.medville2.view.building;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.medville2.model.FieldObject;
import com.medville2.model.Terrain;
import com.medville2.model.building.infra.Tower;
import com.medville2.model.building.infra.Wall;

public class WallRenderer {

	private TextureAtlas textureAtlas;
	private TextureRegion wallTexture;

	public WallRenderer(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
		this.wallTexture = textureAtlas.findRegion("wall");
	}

	public void renderWall(FieldObject fo, int ox, int oy, SpriteBatch batch) {
		Wall wall = (Wall) fo;
		if (wall.hasSegment(3)) {
			Sprite objectSprite = new Sprite(wallTexture);
			objectSprite.translate(ox + Terrain.DX / 4 - 9, oy + Terrain.DY / 2 - 10);
			objectSprite.draw(batch);
		}
		if (wall.hasSegment(1)) {
			Sprite objectSprite = new Sprite(wallTexture);
			objectSprite.flip(true, false);
			objectSprite.translate(ox + Terrain.DX / 2 - 9, oy + Terrain.DY / 2 - 10);
			objectSprite.draw(batch);
		}
		int crop = 0;
		if (fo.getClass().equals(Tower.class)) {
			Sprite objectSprite = new Sprite(textureAtlas.findRegion(fo.getName()));
			objectSprite.translate(ox, oy);
			objectSprite.draw(batch);
			crop = 24;
		}
		if (wall.hasSegment(2)) {
			Sprite objectSprite = new Sprite(new TextureRegion(wallTexture, crop, 0, wallTexture.getRegionWidth() - crop, wallTexture.getRegionHeight()));
			objectSprite.translate(ox + Terrain.DX / 2 + crop - 9, oy + Terrain.DY / 4 - 10);
			objectSprite.draw(batch);
		}
		if (wall.hasSegment(0)) {
			Sprite objectSprite = new Sprite(new TextureRegion(wallTexture, crop, 0, wallTexture.getRegionWidth() - crop, wallTexture.getRegionHeight()));
			objectSprite.flip(true, false);
			objectSprite.translate(ox + Terrain.DX / 4 - 9, oy + Terrain.DY / 4 - 10);
			objectSprite.draw(batch);
		}
	}
}
