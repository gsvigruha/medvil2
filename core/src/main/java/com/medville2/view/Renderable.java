package com.medville2.view;

import com.medville2.model.FieldObject;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.society.Person;

public interface Renderable {

	int getRenderingOrder();

	FieldObject getFieldObject();

	Person getPerson();

	public static class FieldObjectRenderable implements Renderable {
		private final FieldObject fo;

		public FieldObjectRenderable(FieldObject fo) {
			this.fo = fo;
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
	}

	public static class PersonRenderable implements Renderable {
		private final Person p;

		public PersonRenderable(Person p) {
			this.p = p;
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
	}
}
