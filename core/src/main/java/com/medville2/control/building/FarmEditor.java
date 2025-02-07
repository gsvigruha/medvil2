package com.medville2.control.building;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.building.house.Farm;
import com.medville2.model.building.house.Farm.State;
import com.medville2.model.terrain.Fishnet;
import com.medville2.model.terrain.Grain;
import com.medville2.model.terrain.Sheep;
import com.medville2.model.terrain.Tree;

public class FarmEditor extends BuildingEditor {

	private final Farm farm;

	private final ImageButton selectGrainButton;
	private final ImageButton selectFishButton;
	private final ImageButton selectCattleButton;
	private final ImageButton selectTreeButton;
	private final ImageButton deselectButton;
	private final Label capacityLabel;
	private final Label explanationLabel;

	private final ButtonGroup<ImageButton> selectButtonGroup;

	private Farm.State state;

	public FarmEditor(Farm farm, int height, TextureAtlas textureAtlas) {
		super(height, textureAtlas);
		this.farm = farm;
		this.state = State.GRAIN;
		this.selectButtonGroup = new ButtonGroup<>();

		selectGrainButton = createButton(textureAtlas.findRegion("grain"), 10, height - 200, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.GRAIN;
				explanationLabel.setText(
						"Grain can be used to mill flower.\n" +
						"\n" +
						"Grain needs to grow new water.\n" +
						"The closer it is to water the\n" +
						"higher the yeld."
						);
			}
		});
		selectButtonGroup.add(selectGrainButton);

		selectFishButton = createButton(textureAtlas.findRegion("fishnet"), 90, height - 200, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.FISH;
				explanationLabel.setText("");
			}
		});
		selectButtonGroup.add(selectFishButton);

		selectTreeButton = createButton(textureAtlas.findRegion("tree"), 170, height - 200, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.WOOD;
				explanationLabel.setText("");
			}
		});
		selectButtonGroup.add(selectTreeButton);

		selectCattleButton = createButton(textureAtlas.findRegion("sheep"), 250, height - 200, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.CATTLE;
				explanationLabel.setText("");
			}
		});
		selectButtonGroup.add(selectCattleButton);

		deselectButton = createButton(textureAtlas.findRegion("cancel"), 10, height - 280, new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = State.DESELECT;
				explanationLabel.setText("");
			}
		});
		selectButtonGroup.add(deselectButton);

		capacityLabel = createLabel(90, height - 240);
		explanationLabel = createLabel(10, height - 290);
		updateCapacityLabel();
	}

	@Override
	public void handleClick(Field field) {
		if (state == State.DESELECT) {
			if (farm.removeField(field)) {
				field.setObject(null);
			}
		} else if (field.getObject() == null || field.getObject().getClass().equals(Tree.class)) {
			if (farm.addField(field, state)) {
				switch (state) {
				case GRAIN:
					field.setObject(new Grain(field));
					break;
				case FISH:
					field.setObject(new Fishnet(field));
					break;
				case CATTLE:
					field.setObject(new Sheep(field));
					break;
				case WOOD:
					field.setObject(new Tree(Tree.TreeType.GREEN, field));
					break;
				default:
				}
			}
		}
		updateCapacityLabel();
	}

	private void updateCapacityLabel() {
		capacityLabel.setText(String.format("Used capacity: %.0f", farm.getUsedCapacity() * 100) + "%");
	}

	@Override
	public Farm getFieldObject() {
		return farm;
	}

	@Override
	public Iterable<Actor> getActorsImpl() {
		return ImmutableList.of(selectGrainButton, selectFishButton, selectCattleButton, selectTreeButton, deselectButton,
				capacityLabel, explanationLabel);
	}

	@Override
	public String getLabel(Field field) {
		if (state == State.DESELECT && farm.hasField(field) && field.getObject() != null) {
			return field.getObject().getName();
		} else {
			if (field.getObject() == null || field.getObject().getClass().equals(Tree.class)) {
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
					if (field.getType() == Field.Type.WATER) {
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

	@Override
	protected Artifacts getArtifacts() {
		return farm.getArtifacts();
	}
}
