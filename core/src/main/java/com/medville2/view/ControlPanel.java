package com.medville2.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
import com.medville2.control.FontHelper;
import com.medville2.control.MenuEditor;
import com.medville2.control.building.FarmEditor;
import com.medville2.control.building.MineEditor;
import com.medville2.control.building.TownsquareEditor;
import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Game;
import com.medville2.model.Terrain;
import com.medville2.model.building.house.Blacksmith;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.house.Farm;
import com.medville2.model.building.house.Mill;
import com.medville2.model.building.house.Mine;
import com.medville2.model.building.house.Townsquare;
import com.medville2.model.building.house.Workshop;
import com.medville2.model.building.infra.Bridge;
import com.medville2.model.building.infra.Road;
import com.medville2.model.building.infra.Tower;
import com.medville2.model.building.infra.Wall;
import com.medville2.model.society.Town;
import com.medville2.model.time.Calendar;
import com.medville2.view.FieldCheckStatus.FieldWithStatus;
import com.medville2.view.buttons.ButtonHelper;

public class ControlPanel {

	private Stage stage;
	private Label label;
	private Viewport hudViewport;
	private TextureAtlas textureAtlas;
	private ButtonHelper helper;

	private ControlPanelState state;
	private FieldObjectType buildingType;
	private boolean checkAllFields;
	private boolean showAllMinerals;
	private Editor editor;
	private Town activeTown;

	private ButtonGroup<ImageButton> menuButtons;
	private ButtonGroup<ImageButton> buildingButtons;
	private Group buildingButtonStack;
	private Group editorStack;

	private List<FieldObjectType> houses = ImmutableList.of(Farm.Type, Mine.Type, Blacksmith.Type,
			Mill.Type, Workshop.Type);
	private List<FieldObjectType> infra = ImmutableList.of(Road.Type, Bridge.Type, Tower.Type,
			Wall.Type);

	public ControlPanel(Viewport hudViewport, TextureAtlas textureAtlas) {
		this.hudViewport = hudViewport;
		this.textureAtlas = textureAtlas;
		this.helper = ButtonHelper.getInstance();

		this.menuButtons = new ButtonGroup<>();
		this.buildingButtons = new ButtonGroup<>();
		this.buildingButtonStack = new Group();
		this.editorStack = new Group();

		stage = new Stage(hudViewport);
		stage.addActor(buildingButtonStack);
		stage.addActor(editorStack);

		label = new Label("Hello, LibGDX!", new LabelStyle(FontHelper.getInstance().getFont(), Color.WHITE));
		label.setPosition(10, 30);

		addMenuButtons();
	}

	private void clearBuildingButtons() {
		buildingButtons.clear();
		buildingButtonStack.clear();
		editorStack.clear();
		editor = null;
	}

	public void select() {
		clearBuildingButtons();
		state = ControlPanelState.SELECT;
		for (ImageButton button : menuButtons.getButtons()) {
			button.setDisabled(false);
			button.setTouchable(Touchable.enabled);
		}
	}

	public void foundTown() {
		state = ControlPanelState.FOUND_TOWN;
		for (ImageButton button : menuButtons.getButtons()) {
			button.setDisabled(true);
			button.setTouchable(Touchable.disabled);
		}
		setupEditor(new MenuEditor("Found a new town"));
	}

	private void addMenuButtons() {
		addMenuButton(new TextureRegion(new Texture("arrow_small.png")), 10, (int) hudViewport.getWorldHeight() - 80,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						clearBuildingButtons();
						ControlPanel.this.state = ControlPanelState.SELECT;
						setupEditor(new MenuEditor("Select objects on the map"));
					}
				});

		addMenuButton(new TextureRegion(new Texture("house_icon.png")), 90, (int) hudViewport.getWorldHeight() - 80,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						clearBuildingButtons();
						for (int i = 0; i < houses.size(); i++) {
							int bi = i % 3;
							int bj = i / 3;
							addBuildingButton(textureAtlas.findRegion(houses.get(i).getName()), bj * 140 + 10,
									(int) hudViewport.getWorldHeight() - bi * 140 - 260, ControlPanelState.BUILD_HOUSE,
									houses.get(i), i);
						}
						setupEditor(new MenuEditor("Select house to build"));
						buildingButtons.getButtons().get(0).fire(helper.touchDownEvent);
						buildingButtons.getButtons().get(0).fire(helper.touchUpEvent);
					}
				});

		addMenuButton(new TextureRegion(new Texture("bridge_icon.png")), 170, (int) hudViewport.getWorldHeight() - 80,
				new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						clearBuildingButtons();
						for (int i = 0; i < infra.size(); i++) {
							int bi = i % 3;
							int bj = i / 3;
							addBuildingButton(textureAtlas.findRegion(infra.get(i).getName()), bj * 140 + 10,
									(int) hudViewport.getWorldHeight() - bi * 140 - 260, ControlPanelState.BUILD_INFRA,
									infra.get(i), i);
						}
						setupEditor(new MenuEditor("Select infra to build"));
						buildingButtons.getButtons().get(0).fire(helper.touchDownEvent);
						buildingButtons.getButtons().get(0).fire(helper.touchUpEvent);
					}
				});

		stage.addActor(label);
	}

	private void addBuildingButton(TextureRegion icon, int x, int y, ControlPanelState state, FieldObjectType buildingType,
			int selectedButtonIdx) {
		ImageButton button = new ImageButton(new TextureRegionDrawable(icon));
		button.setSize(ButtonHelper.BUTTON_LARGE_SX, ButtonHelper.BUTTON_LARGE_SY);
		button.setPosition(x, y);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ControlPanel.this.state = state;
				ControlPanel.this.buildingType = buildingType;
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

	public boolean getShowAllMinerals() {
		return showAllMinerals;
	}

	public void setShowAllMinerals(boolean showAllMinerals) {
		this.showAllMinerals = showAllMinerals;
	}

	public int getActiveFieldSize() {
		if (state == ControlPanelState.BUILD_HOUSE) {
			return buildingType.getSize();
		}
		return 1;
	}

	public void click(Field field, Game game) {
		Terrain terrain = game.getTerrain();
		FieldCheckStatus fcs = BuildingRules.getFieldCheckStatus(field, terrain, state, buildingType,
				editor, activeTown);
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
					if (fcs.getBuildableObject() instanceof BuildingObject) {
						((BuildingObject) fcs.getBuildableObject()).setTown(activeTown);
					}
				}
			} else if (state == ControlPanelState.SELECT) {
				FieldObject selectedFieldObject = fcs.getBuildableObject();
				if (selectedFieldObject != null) {
					if (selectedFieldObject instanceof BuildingObject) {
						activeTown = ((BuildingObject) selectedFieldObject).getTown();
					}
					state = ControlPanelState.MODIFY;
					setupEditor(createEditor(selectedFieldObject));
				}
			} else if (state == ControlPanelState.MODIFY) {
				if (editor != null) {
					editor.handleClick(field);
				}
			} else if (state == ControlPanelState.FOUND_TOWN) {
				if (fcs.getBuildableObject() != null) {
					for (FieldWithStatus fws : fcs.getFields()) {
						fws.getField().setObject(fcs.getBuildableObject());
					}
				}
				activeTown = game.getPlayer().foundTown((Townsquare) fcs.getBuildableObject(), game.nextTownName(), Game.FOUNDER_ARTIFACTS);
				select();
			}
		}
	}

	private void setupEditor(Editor editor) {
		this.editor = editor;
		editorStack.clear();
		if (editor != null) {
			for (Actor actor : editor.getActors()) {
				editorStack.addActor(actor);
			}
		}
	}

	private Editor createEditor(FieldObject selectedFieldObject) {
		if (selectedFieldObject.getClass().equals(Farm.class)) {
			return new FarmEditor((Farm) selectedFieldObject, (int) hudViewport.getWorldHeight(), textureAtlas);
		} else if (selectedFieldObject.getClass().equals(Mine.class)) {
			return new MineEditor((Mine) selectedFieldObject, (int) hudViewport.getWorldHeight(), textureAtlas);
		} else if (selectedFieldObject.getClass().equals(Townsquare.class)) {
			return new TownsquareEditor((Townsquare) selectedFieldObject, (int) hudViewport.getWorldHeight(), textureAtlas);
		}
		return null;
	}

	public FieldObjectType getBuildingType() {
		return buildingType;
	}

	public ControlPanelState getState() {
		return state;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setActiveTown(Town activeTown) {
		this.activeTown = activeTown;
	}

	public Town getActiveTown() {
		return activeTown;
	}
}
