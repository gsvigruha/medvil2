package com.medville2.view;

import com.medville2.model.FieldObject;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;

public interface Renderable {

	int getRenderingOrder();

	int getScreenX();

	int getScreenY();

	int getSize();

	FieldObjectType getType();

	String getName();

	boolean isFlip();

	public static class FieldObjectRenderable implements Renderable {
		private final FieldObject fo;

		public FieldObjectRenderable(FieldObject fo) {
			this.fo = fo;
		}

		@Override
		public int getScreenX() {
			return fo.getI() * Terrain.DX / 2 - fo.getJ() * Terrain.DX / 2;
		}

		@Override
		public int getScreenY() {
			return fo.getI() * Terrain.DY / 2 + fo.getJ() * Terrain.DY / 2;
		}

		@Override
		public int getSize() {
			return fo.getSize();
		}

		@Override
		public FieldObjectType getType() {
			return fo.getType();
		}

		@Override
		public String getName() {
			return fo.getName();
		}

		@Override
		public boolean isFlip() {
			return fo.isFlip();
		}

		@Override
		public int getRenderingOrder() {
			return fo.getI() * 2 + fo.getJ() * 2 + fo.getSize();
		}
	}
}
