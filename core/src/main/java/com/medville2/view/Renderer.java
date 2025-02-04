package com.medville2.view;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.medville2.control.BuildingRules;
import com.medville2.control.FontHelper;
import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.Terrain;
import com.medville2.model.building.infra.Tower;
import com.medville2.model.building.infra.Wall;
import com.medville2.model.terrain.Fishnet;
import com.medville2.model.terrain.Hill;
import com.medville2.view.FieldCheckStatus.FieldWithStatus;
import com.medville2.view.building.WallRenderer;
import com.medville2.view.terrain.FieldRenderer;

public class Renderer {

	private Terrain terrain;
	private ControlPanel controlPanel;
	private Vector2 worldPos;
	private TextureRegion selectionRed;
	private TextureRegion selectionGreen;
	private TextureRegion grassCube;
	private TextureRegion waterCube;
	private TextureRegion riverCube;
	private TextureRegion rockCube;

	private Rectangle projectedViewport;

	private Field activeField;
	private TextureAtlas textureAtlas;
	private ArrayList<FieldObject> objectsToRender;
	private FieldRenderer fieldRenderer;
	private WallRenderer wallRenderer;

	public Renderer(Terrain terrain, ControlPanel controlPanel, TextureAtlas textureAtlas) {
		this.terrain = terrain;
		this.controlPanel = controlPanel;
		this.textureAtlas = textureAtlas;

		this.selectionRed = textureAtlas.findRegion("selection_red");
		this.selectionGreen = textureAtlas.findRegion("selection_green");
		this.grassCube = textureAtlas.findRegion("grass_cube");
		this.waterCube = textureAtlas.findRegion("water_cube");
		this.riverCube = textureAtlas.findRegion("river_cube");
		this.rockCube = textureAtlas.findRegion("rock_cube");
		this.fieldRenderer = new FieldRenderer(textureAtlas);
		this.wallRenderer = new WallRenderer(textureAtlas);

		this.worldPos = new Vector2();
		this.projectedViewport = new Rectangle();
		this.objectsToRender = new ArrayList<>();
	}

	public void setWorldPos(Vector2 worldPos) {
		this.worldPos = worldPos;
	}

	public void setProjectedViewport(float x, float y, float width, float height) {
		this.projectedViewport.x = x;
		this.projectedViewport.y = y;
		this.projectedViewport.width = width;
		this.projectedViewport.height = height;
	}

	private boolean outOfBounds(int x, int y) {
		return (x < projectedViewport.x - Terrain.DX || x > projectedViewport.x + projectedViewport.width + Terrain.DX
				|| y < projectedViewport.y - Terrain.DY
				|| y > projectedViewport.y + projectedViewport.height + Terrain.DY);
	}

	public void render(SpriteBatch batch) {
		double x0 = worldPos.x;
		double y0 = worldPos.y;
		double maxD = Double.MAX_VALUE;
		objectsToRender.clear();

		for (int i = 0; i < terrain.getSize(); i++) {
			Field[] fields = terrain.getFields()[i];
			for (int j = 0; j < terrain.getSize(); j++) {
				int x = i * Terrain.DX / 2 - j * Terrain.DX / 2;
				int y = j * Terrain.DY / 2 + i * Terrain.DY / 2;
				if (outOfBounds(x, y)) {
					continue;
				}
				Field field = fields[j];
				fieldRenderer.renderField(field, x, y, batch);

				double d = Math.sqrt((x0 - x - Terrain.DX / 2) * (x0 - x - Terrain.DX / 2)
						+ (y0 - y - Terrain.DY / 2) * (y0 - y - Terrain.DY / 2));
				if (d < maxD) {
					maxD = d;
					activeField = field;
				}
			}
		}

		for (int i = terrain.getSize() - 1; i >= 0; i--) {
			Field[] fields = terrain.getFields()[i];
			for (int j = terrain.getSize() - 1; j >= 0; j--) {
				int x = i * Terrain.DX / 2 - j * Terrain.DX / 2;
				int y = j * Terrain.DY / 2 + i * Terrain.DY / 2;
				if (outOfBounds(x, y)) {
					continue;
				}
				Field field = fields[j];

				if (!controlPanel.getCheckAllFields() && field.getCornerType() != null) {
					Sprite cornerSprite;
					if (field.getCornerType() == Field.Type.GRASS) {
						cornerSprite = new Sprite(grassCube);
					} else if (field.getCornerType() == Field.Type.WATER) {
						cornerSprite = new Sprite(waterCube);
					} else if (field.getCornerType() == Field.Type.RIVER) {
						cornerSprite = new Sprite(riverCube);
					} else if (field.getCornerType() == Field.Type.ROCK) {
						cornerSprite = new Sprite(rockCube);
					} else {
						cornerSprite = null;
					}
					cornerSprite.translate(x + Terrain.DX / 2 - cornerSprite.getWidth() / 2,
							y + Terrain.DY - cornerSprite.getHeight() / 2);
					cornerSprite.draw(batch);
				}

				if (field.getObject() != null && field.getObject().getI() == i && field.getObject().getJ() == j) {
					objectsToRender.add(field.getObject());
				}

				if (controlPanel.getCheckAllFields()) {
					FieldCheckStatus fcs = BuildingRules.getFieldCheckStatus(field, terrain, controlPanel.getState(),
							controlPanel.getBuildingType(), controlPanel.getEditor());
					final Sprite objectSprite;
					if (fcs.getStatus()) {
						objectSprite = new Sprite(selectionGreen);
					} else {
						objectSprite = new Sprite(selectionRed);
					}
					objectSprite.setSize(Terrain.DX, Terrain.DY);
					objectSprite.translate(x, y);
					objectSprite.draw(batch);
				}

				// font.draw(batch, String.valueOf(field.getDistanceFromWater()), x + 60, y +
				// 60);
			}
		}

		if (controlPanel.getEditor() != null) {
			for (Field field : controlPanel.getEditor().getActiveFields()) {
				int i = field.getI();
				int j = field.getJ();
				int x = i * Terrain.DX / 2 - j * Terrain.DX / 2;
				int y = j * Terrain.DY / 2 + i * Terrain.DY / 2;
				final Sprite objectSprite;
				objectSprite = new Sprite(selectionGreen);
				objectSprite.setSize(Terrain.DX, Terrain.DY);
				objectSprite.translate(x, y);
				objectSprite.draw(batch);
			}
		}

		objectsToRender.sort((fo1, fo2) -> {
			int y1 = fo1.getI() * 2 + fo1.getJ() * 2 + fo1.getSize();
			int y2 = fo2.getI() * 2 + fo2.getJ() * 2 + fo2.getSize();
			return -Integer.compare(y1, y2);
		});

		for (FieldObject fo : objectsToRender) {
			int x = fo.getI() * Terrain.DX / 2 - fo.getJ() * Terrain.DX / 2;
			int y = fo.getI() * Terrain.DY / 2 + fo.getJ() * Terrain.DY / 2;
			int ox = x;
			int oy = y;
			if (fo.getType() == Wall.Type || fo.getType() == Tower.Type) {
				wallRenderer.renderWall(fo, ox, oy, batch);
			} else {
				if (fo.getSize() == 2) {
					ox = x - Terrain.DX / 2;
					oy = y + Terrain.DY * (fo.getSize() - 2);
				}
				if (fo.getType() == Fishnet.Type) {
					oy += (Math.sin(((double) System.currentTimeMillis()) / 250.0 + fo.getI())) * 2.0;
				}
				final Sprite objectSprite = new Sprite(textureAtlas.findRegion(fo.getName()));
				if (fo.isFlip()) {
					objectSprite.flip(true, false);
				}
				objectSprite.translate(ox, oy);
				objectSprite.draw(batch);
			}

			if (controlPanel.getShowAllMinerals()) {
				if (fo.getType() == Hill.Type) {
					Hill hill = (Hill) fo;
					if (hill.getMineral() != null) {
						Sprite mineralSprite = new Sprite(
								textureAtlas.findRegion("artifact_" + hill.getMineral().toLowerCase()));
						mineralSprite.setSize(96, 96);
						mineralSprite.translate(ox + Terrain.DX / 2 - mineralSprite.getWidth() / 2,
								oy + Terrain.DY / 2 - mineralSprite.getHeight() / 2);
						mineralSprite.draw(batch);
					}
				}
			}
		}

		if (activeField != null) {
			FieldCheckStatus fcs = BuildingRules.getFieldCheckStatus(activeField, terrain, controlPanel.getState(),
					controlPanel.getBuildingType(), controlPanel.getEditor());
			for (FieldWithStatus fws : fcs.getFields()) {
				int i = fws.getField().getI();
				int j = fws.getField().getJ();
				int x = i * Terrain.DX / 2 - j * Terrain.DX / 2;
				int y = j * Terrain.DY / 2 + i * Terrain.DY / 2;
				final Sprite objectSprite;
				if (fws.getStatus()) {
					objectSprite = new Sprite(selectionGreen);
				} else {
					objectSprite = new Sprite(selectionRed);
				}
				objectSprite.setSize(Terrain.DX, Terrain.DY);
				objectSprite.translate(x, y);
				objectSprite.draw(batch);
			}
			if (fcs.getLabel() != null) {
				BitmapFont font = FontHelper.getInstance().getMapFont();
				int i = activeField.getI();
				int j = activeField.getJ();
				int x = i * Terrain.DX / 2 - j * Terrain.DX / 2 + Terrain.DX / 2 - 24;
				int y = j * Terrain.DY / 2 + i * Terrain.DY / 2 + Terrain.DY / 2 + (int) font.getLineHeight() / 2;
				font.draw(batch, fcs.getLabel(), x, y);
			}
			if (fcs.getIcon() != null) {
				int i = activeField.getI();
				int j = activeField.getJ();
				int x = i * Terrain.DX / 2 - j * Terrain.DX / 2 + Terrain.DX / 2 - 24;
				int y = j * Terrain.DY / 2 + i * Terrain.DY / 2 + Terrain.DY / 2;
				Sprite iconSprite = new Sprite(textureAtlas.findRegion(fcs.getIcon()));
				iconSprite.setSize(96, 96);
				iconSprite.translate(x - iconSprite.getWidth(), y - iconSprite.getHeight() / 2);
				iconSprite.draw(batch);
			}
		}
	}

	public void dispose() {
		for (Texture t : textureAtlas.getTextures()) {
			t.dispose();
		}
	}

	public Field getActiveField() {
		return activeField;
	}
}
