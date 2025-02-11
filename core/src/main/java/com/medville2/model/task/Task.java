package com.medville2.model.task;

import java.io.Serializable;

import com.medville2.model.Field;

public abstract class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract Field nextDestination();

	public abstract boolean arrivedAt(Field field);

	public abstract void start();
}
