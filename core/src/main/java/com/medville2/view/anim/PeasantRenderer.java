package com.medville2.view.anim;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.medville2.model.Terrain;
import com.medville2.model.society.Person;

public class PeasantRenderer {

	private TextureAtlas textureAtlas;
	private Animation<TextureRegion> walkAnimation;

	public PeasantRenderer(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
		walkAnimation = new Animation<>(0.125f, textureAtlas.findRegions("peasant_front"));
	}

	public void renderPeasant(Person person, int x, int y, float stateTime, SpriteBatch batch) {
		float pi = (float) (person.getX() % Person.D) / (float) Person.D;
		float pj = (float) (person.getY() % Person.D) / (float) Person.D;
		int px = x + (int) (pi * Terrain.DX / 2) - (int) (pj * Terrain.DX / 2);
		int py = y + (int) (pj * Terrain.DY / 2) + (int) (pi * Terrain.DY / 2);

		final Sprite personSprite;
		if (person.getDir() == 0 || person.getDir() == 1) {
			personSprite = new Sprite(walkAnimation.getKeyFrame(stateTime, true));
			if (person.getDir() == 0) {
				personSprite.flip(true, false);
			}
		} else {
			personSprite = new Sprite(textureAtlas.findRegion("peasant_back"));
			if (person.getDir() == 3) {
				personSprite.flip(true, false);
			}
		}
		personSprite.translate(px, py);
		personSprite.draw(batch);
	}
}
