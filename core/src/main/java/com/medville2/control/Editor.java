package com.medville2.control;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.google.common.collect.ImmutableList;
import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.view.buttons.ButtonHelper;

public abstract class Editor {

	public static final int ARTIFACT_SX = 64;
	public static final int ARTIFACT_SY = 64;
	public static final int ARTIFACT_PX = 78;
	public static final int ARTIFACT_PY = 80;

	protected BitmapFont font;
	protected BitmapFont smallFont;

	public abstract void handleClick(Field field);

	public abstract Iterable<Actor> getActors(Terrain terrain);

	public Editor() {
		this.font = FontHelper.getInstance().getFont();
		this.smallFont = FontHelper.getInstance().getSmallFont();
	}

	protected ImageButton createButton(TextureRegion icon, int x, int y, EventListener listener) {
		ImageButton button = new ImageButton(new TextureRegionDrawable(icon));
		button.setSize(ButtonHelper.BUTTON_SMALL_SX, ButtonHelper.BUTTON_SMALL_SY);
		button.setPosition(x, y);
		button.addListener(listener);
		button.getStyle().checked = ButtonHelper.getInstance().buttonBGSelectedLarge;
		return button;
	}

	protected Label createLabel(int x, int y) {
		Label label = new Label("", new LabelStyle(font, Color.WHITE));
		label.setPosition(x, y);
		label.setAlignment(Align.left | Align.top);
		return label;
	}

	public String getLabel(Field field) {
		return null;
	}

	public String getIcon(Field field) {
		return null;
	}

	public Iterable<Field> getActiveFields() {
		return ImmutableList.of();
	}

	public Label headerLabel(String title) {
		Label townLabel = new Label(title, new LabelStyle(font, Color.WHITE));
		townLabel.setPosition(10, 960);
		townLabel.setAlignment(Align.left | Align.top);
		return townLabel;
	}
}
