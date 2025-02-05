package com.medville2.model.terrain;

import com.medville2.model.Field;
import com.medville2.model.FieldObjectType;

public class Hill extends TerrainObject {

	private static final long serialVersionUID = 1L;

	public static final FieldObjectType Type = new FieldObjectType("hill", 1, Hill.class);

	private String mineral;
	private int quantity;

	public Hill(Field field, String mineral, int quanity) {
		super(field, Type);
		this.mineral = mineral;
		this.quantity = quanity;
	}

	public boolean isEmpty() {
		return quantity == 0;
	}

	public int mine() {
		if (quantity > 0) {
			quantity--;
			return 1;
		} else {
			mineral = null;
			return 0;
		}
	}

	public String getMineral() {
		return mineral;
	}

	public int getQuantity() {
		return quantity;
	}
}
