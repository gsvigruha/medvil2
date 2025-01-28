package com.medville2.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.common.collect.ImmutableList;
import com.medville2.control.BuildingRules;
import com.medville2.control.Editor;
import com.medville2.control.building.FarmEditor;
import com.medville2.model.Field;
import com.medville2.model.FieldObject;
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
import com.medville2.model.time.Calendar;
import com.medville2.view.FieldCheckStatus.FieldWithStatus;
import com.medville2.view.buttons.ButtonHelper;

public class ControlPanel {

	private BitmapFont font;
	private Stage stage;
	private Label label;
	private Viewport hudViewport;
	private TextureAtlas textureAtlas;
	private ButtonHelper helper;

	private ControlPanelState state;
	private Class<?> buildingClass;
	private boolean checkAllFields;
	private Editor editor;

	private ButtonGroup<ImageButton> menuButtons;
	private ButtonGroup<ImageButton> buildingButtons;
	private Group buildingButtonStack;
	private Group editorStack;

	private List<Class<? extends BuildingObject>> houses = ImmutableList.of(Farm.class, Mine.class, Blacksmith.class,
			Townsquare.class, Mill.class);
	private List<Class<? extends InfraObject>> infra = ImmutableList.of(Road.class, Bridge.class, Tower.class,
			Wall.class);

	public ControlPanel(Viewport hudViewport, TextureAtlas textureAtlas) {
		this.hudViewport = hudViewport;
		this.textureAtlas = textureAtlas;
		this.state = ControlPanelState.DO_NOTHING;
		this.helper = ButtonHelper.getInstance();

		this.menuButtons = new ButtonGroup<>();
		this.buildingButtons = new ButtonGroup<>();
		this.buildingButtonStack = new Group();
		this.editorStack = new Group();

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(2);

		stage = new Stage(hudViewport);
		stage.addActor(buildingButtonStack);
		stage.addActor(editorStack);
		font = new BitmapFont();

		label = new Label("Hello, LibGDX!", new LabelStyle(font, Color.WHITE));
		label.setPosition(10, 30);

		addMenuButtons();
	}

	private void clearBuildingButtons() {
		buildingButtons.clear();
		buildingButtonStack.clear();
		editorStack.clear();
		editor = null;
	}

	private void addMenuButtons() {
		addMenuButton(new TextureRegion(new Texture("house_icon.png")), 80, (int) hudViewport.getWorldHeight() - 80,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						clearBuildingButtons();
						for (int i = 0; i < houses.size(); i++) {
							BuildingObject building = BuildingRules.newHouse(houses.get(i), null);
							addBuildingButton(textureAtlas.findRegion(building.getName()), 0,
									(int) hudViewport.getWorldHeight() - i * 140 - 240, ControlPanelState.BUILD_HOUSE,
									houses.get(i), i);
						}
						buildingButtons.getButtons().get(0).fire(helper.touchDownEvent);
						buildingButtons.getButtons().get(0).fire(helper.touchUpEvent);
					}
				});

		addMenuButton(new TextureRegion(new Texture("bridge_icon.png")), 160, (int) hudViewport.getWorldHeight() - 80,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						clearBuildingButtons();
						for (int i = 0; i < infra.size(); i++) {
							InfraObject building = BuildingRules.newInfra(infra.get(i), null);
							addBuildingButton(textureAtlas.findRegion(building.getName()), 0,
									(int) hudViewport.getWorldHeight() - i * 140 - 240, ControlPanelState.BUILD_INFRA,
									infra.get(i), i);
						}
						buildingButtons.getButtons().get(0).fire(helper.touchDownEvent);
						buildingButtons.getButtons().get(0).fire(helper.touchUpEvent);
					}
				});

		addMenuButton(new TextureRegion(new Texture("arrow_small.png")), 0, (int) hudViewport.getWorldHeight() - 80,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						clearBuildingButtons();
						ControlPanel.this.state = ControlPanelState.SELECT;
						ControlPanel.this.editor = null;
					}
				});

		stage.addActor(label);
	}

	private void addBuildingButton(TextureRegion icon, int x, int y, ControlPanelState state, Class<?> buildingClass,
			int selectedButtonIdx) {
		ImageButton button = new ImageButton(new TextureRegionDrawable(icon));
		button.setSize(ButtonHelper.BUTTON_LARGE_SX, ButtonHelper.BUTTON_LARGE_SY);
		button.setPosition(x, y);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ControlPanel.this.state = state;
				ControlPanel.this.buildingClass = buildingClass;
			}
		});

		button.getStyle().checked = helper.buttonBGSelectedLarge;

		buildingButtonStack.addActor(button);
		buildingButtons.add(button);
	}

	private void addMenuButton(TextureRegion icon, int x, int y, ClickListener listener) {
		ImageButton button = new ImageButton(new TextureRegionDrawable(icon));
		button.setSize(ButtonHelper.BUTTON_SMALL_SX, ButtonHelper.BUTTON_SMALL_SY);
		button.setPosition(x, y);
		button.addListener(listener);

		button.getStyle().checked = helper.buttonBGSelectedSmall;

		stage.addActor(button);
		menuButtons.add(button);
	}

	public void dispose() {
		stage.dispose();
	}

	public void render(Calendar calendar) {
		int fps = Gdx.graphics.getFramesPerSecond();
		label.setText("FPS: " + fps + ", " + calendar.render());
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
			BuildingObject building = BuildingRules.newHouse((Class<? extends BuildingObject>) buildingClass, null);
			return building.getSize();
		}
		return 1;
	}

	public void click(Field field, Terrain terrain) {
		FieldCheckStatus fcs = BuildingRules.getFieldCheckStatus(field, terrain, state, buildingClass,
				editor);
		if (fcs.getStatus()) {
			if (state == ControlPanelState.BUILD_HOUSE || state == ControlPanelState.BUILD_INFRA) {
				if (fcs.getBuildableObject() != null) {
					for (FieldWithStatus fws : fcs.getFields()) {
						fws.getField().setObject(fcs.getBuildableObject());
					}
					BuildingRules.setupWalls(field.getI(), field.getJ(), terrain);
					BuildingRules.setupWalls(field.getI() - 1, field.getJ(), terrain);
					BuildingRules.setupWalls(field.getI() + 1, field.getJ(), terrain);
					BuildingRules.setupWalls(field.getI(), field.getJ() - 1, terrain);
					BuildingRules.setupWalls(field.getI(), field.getJ() + 1, terrain);
				}
			} else if (state == ControlPanelState.SELECT) {
				FieldObject selectedFieldObject = fcs.getBuildableObject();
				if (selectedFieldObject != null) {
					state = ControlPanelState.MODIFY;
					editor = createEditor(selectedFieldObject);
					editorStack.clear();
					if (editor != null) {
						for (Actor actor : editor.getActors()) {
							editorStack.addActor(actor);
						}
						for (Actor actor : editor.getArtifactActors((int) hudViewport.getWorldHeight(), textureAtlas)) {
							editorStack.addActor(actor);
						}
					}
				}
			} else if (state == ControlPanelState.MODIFY) {
				if (editor != null) {
					editor.handleClick(field);
				}
			}
		}
	}

	private Editor createEditor(FieldObject selectedFieldObject) {
		if (selectedFieldObject.getClass().equals(Farm.class)) {
			return new FarmEditor((Farm) selectedFieldObject, (int) hudViewport.getWorldHeight(), textureAtlas);
		}
		return null;
	}

	public Class<?> getBuildingClass() {
		return buildingClass;
	}

	public ControlPanelState getState() {
		return state;
	}

	public Editor getEditor() {
		return editor;
	}
}
