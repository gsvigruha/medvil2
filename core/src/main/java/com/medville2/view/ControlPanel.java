package com.medville2.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

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
import com.google.common.collect.ImmutableSet;
import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.building.house.Blacksmith;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.house.Farm;
import com.medville2.model.building.house.Mine;
import com.medville2.model.building.house.Mill;
import com.medville2.model.building.house.Townsquare;
import com.medville2.model.building.infra.Bridge;
import com.medville2.model.building.infra.InfraObject;
import com.medville2.model.building.infra.Road;
import com.medville2.model.building.infra.Tower;
import com.medville2.view.FieldCheckStatus.FieldWithStatus;

public class ControlPanel {

	private static final Set<Field.Type> RoadTypes = ImmutableSet.of(Field.Type.GRASS, Field.Type.ROCK);
	private static final Set<Field.Type> FarmTypes = ImmutableSet.of(Field.Type.GRASS);
	private static final Set<Field.Type> MineTypes = ImmutableSet.of(Field.Type.ROCK);

	private BitmapFont font;
	private Stage stage;
	private Label label;
	private Viewport hudViewport;
	private TextureAtlas textureAtlas;

	private ControlPanelState state;
	private Class<?> buildingClass;

	private List<Class<? extends BuildingObject>> houses = ImmutableList.of(Farm.class, Mine.class, Blacksmith.class,
			Townsquare.class, Mill.class);
	private List<Class<? extends InfraObject>> infra = ImmutableList.of(Road.class, Bridge.class, Tower.class);

	public static BuildingObject newHouse(Class<? extends BuildingObject> clss, int i, int j) {
		try {
			return clss.getConstructor(int.class, int.class).newInstance(i, j);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static InfraObject newInfra(Class<? extends InfraObject> clss, int i, int j) {
		try {
			return clss.getConstructor(int.class, int.class).newInstance(i, j);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

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
							BuildingObject building = newHouse(houses.get(i), 0, 0);
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
							InfraObject building = newInfra(infra.get(i), 0, 0);
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

	private FieldCheckStatus checkFields(Field field, BuildingObject building, Terrain terrain) {
		FieldCheckStatus fcs = new FieldCheckStatus();
		int i0 = field.getI();
		int j0 = field.getJ();
		for (int i = i0; i < i0 + building.getSize(); i++) {
			for (int j = j0; j < j0 + building.getSize(); j++) {
				Field f = terrain.getField(i, j);
				if (f == null) {
					fcs.addFieldWithStatus(new FieldWithStatus(new Field(i, j), false));
				} else {
					boolean fieldStatus = !(f == null || !FarmTypes.contains(f.getType()) || f.getObject() != null);
					fcs.addFieldWithStatus(new FieldWithStatus(f, fieldStatus));
				}
			}
		}
		return fcs;
	}

	public FieldCheckStatus getFieldCheckStatus(Field field, Terrain terrain) {
		if (field == null) {
			return FieldCheckStatus.fail(field);
		}
		if (state == ControlPanelState.BUILD_INFRA) {
			if (buildingClass.equals(Road.class)) {
				if (field != null && RoadTypes.contains(field.getType()) && field.getObject() == null) {
					return FieldCheckStatus.success(field, new Road(field.getI(), field.getJ()));
				} else if (field != null && field.getType() == Field.Type.RIVER && field.getObject() == null) {
					return FieldCheckStatus.success(field, new Bridge(field.getI(), field.getJ()));
				}
			} else {
				if (field != null && RoadTypes.contains(field.getType()) && field.getObject() == null) {
					return FieldCheckStatus.success(field, new Tower(field.getI(), field.getJ()));
				}
			}
		}

		if (state == ControlPanelState.BUILD_HOUSE) {
			BuildingObject building = newHouse((Class<? extends BuildingObject>) buildingClass, field.getI(),
					field.getJ());
			if (buildingClass.equals(Mine.class)) {
				if (MineTypes.contains(field.getType()) && field.getObject() != null && field.getObject().isHill()) {
					return FieldCheckStatus.success(field, building);
				}
			} else if (buildingClass.equals(Mill.class)) {
				if (FarmTypes.contains(field.getType()) && field.getObject() == null && terrain.hasNeighbor(field.getI(), field.getJ(), Field.Type.RIVER)) {
					return FieldCheckStatus.success(field, building);
				}
			} else {
				FieldCheckStatus fcs = checkFields(field, building, terrain);
				fcs.setBuildableObject(building);
				return fcs;
			}
		}

		if (state == ControlPanelState.SELECT) {
			if (field.getObject() != null) {
				return FieldCheckStatus.success(field, field.getObject());
			}
		}
		return FieldCheckStatus.fail(field);
	}

	public int getActiveFieldSize() {
		if (state == ControlPanelState.BUILD_HOUSE) {
			BuildingObject building = newHouse((Class<? extends BuildingObject>) buildingClass, 0, 0);
			return building.getSize();
		}
		return 1;
	}

	public void click(Field field, Terrain terrain) {
		FieldCheckStatus fcs = getFieldCheckStatus(field, terrain);
		if (fcs.getStatus() && fcs.getBuildableObject() != null) {
			if (state == ControlPanelState.BUILD_HOUSE || state == ControlPanelState.BUILD_INFRA) {
				for (FieldWithStatus fws : fcs.getFields()) {
					fws.getField().setObject(fcs.getBuildableObject());
				}
			} else if (state == ControlPanelState.SELECT) {

			}
		}
	}
}
