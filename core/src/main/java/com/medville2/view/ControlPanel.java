package com.medville2.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.common.collect.ImmutableList;
import com.medville2.control.BuildingRules;
import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.building.house.Blacksmith;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.house.Farm;
import com.medville2.model.building.house.Mill;
import com.medville2.model.building.house.Mine;
import com.medville2.model.building.house.Townsquare;
import com.medville2.model.building.infra.Bridge;
import com.medville2.model.building.infra.InfraObject;
import com.medville2.model.building.infra.Road;
import com.medville2.model.building.infra.Tower;
import com.medville2.model.building.infra.Wall;
import com.medville2.view.FieldCheckStatus.FieldWithStatus;

public class ControlPanel {

	private BitmapFont font;
	private Stage stage;
	private Label label;
	private Viewport hudViewport;
	private TextureAtlas textureAtlas;

	private ControlPanelState state;
	private Class<?> buildingClass;
	private boolean checkAllFields;

	private List<Class<? extends BuildingObject>> houses = ImmutableList.of(Farm.class, Mine.class, Blacksmith.class,
			Townsquare.class, Mill.class);
	private List<Class<? extends InfraObject>> infra = ImmutableList.of(Road.class, Bridge.class, Tower.class, Wall.class);

	public ControlPanel(Viewport hudViewport, TextureAtlas textureAtlas) {
		this.hudViewport = hudViewport;
		this.textureAtlas = textureAtlas;
		this.state = ControlPanelState.DO_NOTHING;

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(2);

		stage = new Stage(hudViewport);
		font = new BitmapFont();

		label = new Label("Hello, LibGDX!", new LabelStyle(font, Color.WHITE));
		label.setPosition(10, 30);

		addMenuButtons();
	}

	private void addMenuButtons() {
		addMenuButton(new TextureRegion(new Texture("house_icon.png")), 90, (int) hudViewport.getWorldHeight() - 80,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						stage.clear();
						addMenuButtons();
						for (int i = 0; i < houses.size(); i++) {
							BuildingObject building = BuildingRules.newHouse(houses.get(i), 0, 0);
							addBuildingButton(textureAtlas.findRegion(building.getName()), 10,
									(int) hudViewport.getWorldHeight() - i * 140 - 240, ControlPanelState.BUILD_HOUSE,
									houses.get(i));
						}
					}
				});

		addMenuButton(new TextureRegion(new Texture("bridge_icon.png")), 170, (int) hudViewport.getWorldHeight() - 80,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						stage.clear();
						addMenuButtons();
						for (int i = 0; i < infra.size(); i++) {
							InfraObject building = BuildingRules.newInfra(infra.get(i), 0, 0);
							addBuildingButton(textureAtlas.findRegion(building.getName()), 10,
									(int) hudViewport.getWorldHeight() - i * 140 - 240, ControlPanelState.BUILD_INFRA,
									infra.get(i));
						}
					}
				});

		addMenuButton(new TextureRegion(new Texture("arrow_small.png")), 10, (int) hudViewport.getWorldHeight() - 80,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						stage.clear();
						addMenuButtons();
						ControlPanel.this.state = ControlPanelState.SELECT;
					}
				});

		stage.addActor(label);
	}

	private void addBuildingButton(TextureRegion icon, int x, int y, ControlPanelState state, Class<?> buildingClass) {
		ImageButton button = new ImageButton(new TextureRegionDrawable(icon));
		button.setSize(128, 128);
		button.setPosition(x, y);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ControlPanel.this.state = state;
				ControlPanel.this.buildingClass = buildingClass;
			}
		});
		stage.addActor(button);
	}

	private void addMenuButton(TextureRegion icon, int x, int y, ClickListener listener) {
		ImageButton button = new ImageButton(new TextureRegionDrawable(icon));
		button.setSize(64, 64);
		button.setPosition(x, y);
		button.addListener(listener);
		stage.addActor(button);
	}

	public void dispose() {
		stage.dispose();
	}

	public void render() {
		int fps = Gdx.graphics.getFramesPerSecond();
		label.setText("FPS: " + fps);
		stage.act();
		stage.draw();
	}

	public Stage getStage() {
		return stage;
	}

	public boolean getCheckAllFields() {
		return checkAllFields;
	}

	public void setCheckAllFields(boolean checkAllFields) {
		this.checkAllFields = checkAllFields;
	}

	public int getActiveFieldSize() {
		if (state == ControlPanelState.BUILD_HOUSE) {
			BuildingObject building = BuildingRules.newHouse((Class<? extends BuildingObject>) buildingClass, 0, 0);
			return building.getSize();
		}
		return 1;
	}

	public void click(Field field, Terrain terrain) {
		FieldCheckStatus fcs = BuildingRules.getFieldCheckStatus(field, terrain, state, buildingClass);
		if (fcs.getStatus() && fcs.getBuildableObject() != null) {
			if (state == ControlPanelState.BUILD_HOUSE || state == ControlPanelState.BUILD_INFRA) {
				for (FieldWithStatus fws : fcs.getFields()) {
					fws.getField().setObject(fcs.getBuildableObject());
				}
				BuildingRules.setupWalls(field.getI(), field.getJ(), terrain);
				BuildingRules.setupWalls(field.getI() - 1, field.getJ(), terrain);
				BuildingRules.setupWalls(field.getI() + 1, field.getJ(), terrain);
				BuildingRules.setupWalls(field.getI(), field.getJ() - 1, terrain);
				BuildingRules.setupWalls(field.getI(), field.getJ() + 1, terrain);
			} else if (state == ControlPanelState.SELECT) {

			}
		}
	}

	public Class<?> getBuildingClass() {
		return buildingClass;
	}

	public ControlPanelState getState() {
		return state;
	}
}
