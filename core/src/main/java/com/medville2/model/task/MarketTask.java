package com.medville2.model.task;

import java.io.Serializable;

import com.medville2.model.Field;

public class MarketTask extends Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Field nextDestination() {
		return null;
	}
}
