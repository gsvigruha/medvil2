package com.medville2.model.terrain;

import com.medville2.model.Field;

public class Hill extends TerrainObject {

	private String mineral;
	private int quantity;

	public Hill(Field field, String mineral, int quanity) {
		super(field);
		this.mineral = mineral;
		this.quantity = quanity;
	}

	@Override
	public String getName() {
		return "hill";
	}

	@Override
	public boolean isHill() {
		return true;
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
