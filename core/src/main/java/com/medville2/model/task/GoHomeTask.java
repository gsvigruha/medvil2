package com.medville2.model.task;

import java.io.Serializable;

import com.medville2.model.Field;

public class GoHomeTask extends Task implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Field home;

	public GoHomeTask(Field home) {
		this.home = home;
	}

	@Override
	public Field nextDestination() {
		return home;
	}

	@Override
	public boolean arrivedAt(Field field) {
		return true;
	}

	@Override
	public void start() {		
	}
}
