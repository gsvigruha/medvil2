package com.medville2.view;

import com.medville2.model.FieldObject;
import com.medville2.model.society.Person;

public interface Renderable {

	int getRenderingOrder();

	FieldObject getFieldObject();

	Person getPerson();

	int getX();

	int getY();

	public static class FieldObjectRenderable implements Renderable {
		private final FieldObject fo;
		private final int x;
		private final int y;

		public FieldObjectRenderable(FieldObject fo, int x, int y) {
			this.fo = fo;
			this.x = x;
			this.y = y;
		}

		@Override
		public int getRenderingOrder() {
			return fo.getI() * 2 + fo.getJ() * 2 + fo.getSize();
		}

		@Override
		public FieldObject getFieldObject() {
			return fo;
		}

		@Override
		public Person getPerson() {
			return null;
		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public int getY() {
			return y;
		}
	}

	public static class PersonRenderable implements Renderable {
		private final Person p;
		private final int x;
		private final int y;

		public PersonRenderable(Person p, int x, int y) {
			this.p = p;
			this.x = x;
			this.y = y;
		}

		@Override
		public int getRenderingOrder() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public FieldObject getFieldObject() {
			return null;
		}

		@Override
		public Person getPerson() {
			return p;
		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public int getY() {
			return y;
		}
	}
}
