package com.medville2.view;

import java.util.ArrayList;
import java.util.List;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;

public class FieldCheckStatus {

	public static class FieldWithStatus {

		private final Field field;
		private final boolean status;

		public FieldWithStatus(Field field, boolean status) {
			this.field = field;
			this.status = status;
		}

		public Field getField() {
			return field;
		}

		public boolean getStatus() {
			return status;
		}
	}

	public static FieldCheckStatus fail(Field field) {
		FieldCheckStatus fcs = new FieldCheckStatus();
		fcs.addFieldWithStatus(new FieldWithStatus(field, false));
		return fcs;
	}

	public static FieldCheckStatus success(Field field, FieldObject fieldObject) {
		FieldCheckStatus fcs = new FieldCheckStatus();
		fcs.addFieldWithStatus(new FieldWithStatus(field, true));
		fcs.setBuildableObject(fieldObject);
		return fcs;
	}

	public static FieldCheckStatus success(Field field, String label, String icon) {
		FieldCheckStatus fcs = new FieldCheckStatus();
		fcs.addFieldWithStatus(new FieldWithStatus(field, true));
		fcs.setLabel(label);
		fcs.setIcon(icon);
		return fcs;
	}

	private final List<FieldWithStatus> fields;
	private boolean status;
	private FieldObject buildableObject;
	private String label;
	private String icon;

	public FieldCheckStatus() {
		this.fields = new ArrayList<>();
		this.status = true;
	}

	public void addFieldWithStatus(FieldWithStatus fws) {
		this.fields.add(fws);
		if (!fws.getStatus()) {
			this.status = false;
		}
	}

	public List<FieldWithStatus> getFields() {
		return fields;
	}

	public boolean getStatus() {
		return status;
	}

	public FieldObject getBuildableObject() {
		return buildableObject;
	}

	public void setBuildableObject(FieldObject buildableObject) {
		this.buildableObject = buildableObject;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
