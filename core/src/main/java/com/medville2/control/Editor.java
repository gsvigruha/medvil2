package com.medville2.control;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;

public abstract class Editor {

	public abstract void handleClick(Field field);

	public abstract FieldObject getFieldObject();

}
