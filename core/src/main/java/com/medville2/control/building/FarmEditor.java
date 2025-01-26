package com.medville2.control.building;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.common.collect.ImmutableList;
import com.medville2.control.Editor;
import com.medville2.model.Field;
import com.medville2.model.building.house.Farm;
import com.medville2.model.terrain.Fishnet;
import com.medville2.model.terrain.Grain;
import com.medville2.model.terrain.Sheep;
import com.medville2.model.terrain.Tree;

public class FarmEditor extends Editor {

	private enum State {
		GRAIN, FISH, CATTLE, WOOD, DESELECT
	}

	private final Farm farm;

	private final ImageButton selectGrainButton;
	private final ImageButton selectFishButton;
	private final ImageButton selectCattleButton;
	private final ImageButton selectTreeButton;
	private final ImageButton deselectButton;

	private final ButtonGroup<ImageButton> selectButtonGroup;

	private State state;

	public FarmEditor(Farm farm, int height, TextureAtlas textureAtlas) {
		this.farm = farm;
		this.state = State.GRAIN;
		this.selectButtonGroup = new ButtonGroup<>();

		selectGrainButton = createButton(textureAtlas.findRegion("grain"), 20, height - 256, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.GRAIN;
			}
		});
		selectButtonGroup.add(selectGrainButton);

		selectFishButton = createButton(textureAtlas.findRegion("fishnet"), 20, height - 384, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.FISH;
			}
		});
		selectButtonGroup.add(selectFishButton);

		selectTreeButton = createButton(textureAtlas.findRegion("tree"), 20, height - 512, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.WOOD;
			}
		});
		selectButtonGroup.add(selectTreeButton);

		selectCattleButton = createButton(textureAtlas.findRegion("sheep"), 20, height - 640, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.CATTLE;
			}
		});
		selectButtonGroup.add(selectCattleButton);

		deselectButton = createButton(textureAtlas.findRegion("cancel"), 20, height - 768, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.DESELECT;
			}
		});
		selectButtonGroup.add(deselectButton);
	}

	@Override
	public void handleClick(Field field) {
		if (state == State.DESELECT) {
			if (farm.removeField(field)) {
				field.setObject(null);
			}
		} else if (field.getObject() == null) {
			if (farm.addField(field)) {
				switch (state) {
				case GRAIN:
					field.setObject(new Grain(field.getI(), field.getJ()));
					break;
				case FISH:
					field.setObject(new Fishnet(field.getI(), field.getJ()));
					break;
				case CATTLE:
					field.setObject(new Sheep(field.getI(), field.getJ()));
					break;
				case WOOD:
					field.setObject(new Tree(Tree.Type.GREEN, field.getI(), field.getJ()));
					break;
				default:
				}
			}
		}
	}

	@Override
	public Farm getFieldObject() {
		return farm;
	}

	@Override
	public Actor[] getActors() {
		return new Actor[] { selectGrainButton, selectFishButton, selectCattleButton, selectTreeButton,
				deselectButton };
	}

	@Override
	public String getLabel(Field field) {
		if (farm.hasField(field) && field.getObject() != null) {
			return field.getObject().getName();
		} else {
			if (field.getObject() == null) {
				if (!farm.hasCapacity(field)) {
					return null;
				}
				switch (state) {
				case GRAIN:
					if (field.getCropYield() > 0) {
						return String.format("%.0f", field.getCropYield() * 100) + "%";
					}
					return null;
				case FISH:
					if (field.getType() == Field.Type.RIVER || field.getType() == Field.Type.WATER) {
						return "fish";
					}
					return null;
				case CATTLE:
					if (field.getType() == Field.Type.GRASS) {
						return "sheep";
					}
					return null;
				case WOOD:
					if (field.getType() == Field.Type.GRASS) {
						return "tree";
					}
					return null;
				default:
				}
			}
		}
		return null;
	}

	@Override
	public Iterable<Field> getActiveFields() {
		return farm.getFields();
	}
}
