package com.medville2.model.society;

import java.io.Serializable;
import java.util.Objects;

import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.infra.Road;
import com.medville2.model.task.Task;
import com.medville2.model.time.Calendar;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int D = 256;

	private final int id;
	private int x;
	private int y;
	private int dir;

	private BuildingObject home;
	private Task task;

	public Person(BuildingObject home, int id) {
		this.home = home;
		this.id = id;
		this.x = home.getI() * D;
		this.y = home.getJ() * D;
	}

	public Field getField(Terrain terrain) {
		int i = x / D;
		int j = y / D;
		return terrain.getField(i, j);
	}

	public void setHome(BuildingObject home) {
		this.home = home;
	}

	public void tick(Terrain terrain, Calendar calendar) {
		Field field = getField(terrain);
		if (task != null) {
			int steps = 1;
			if (field.getObject() != null && field.getObject().getType() == Road.Type) {
				steps = 2;
			}
			Field dest = task.nextDestination();
			for (int i = 0; i < steps; i++) {
				if (dest.getI() < field.getI()) {
					x--;
					dir = 0;
				} else if (dest.getI() > field.getI()) {
					x++;
					dir = 2;
				} else if (dest.getJ() < field.getJ()) {
					y--;
					dir = 1;
				} else if (dest.getJ() > field.getJ()) {
					y++;
					dir = 3;
				}
			}
		}
		Field field2 = getField(terrain);
		if (field != field2) {
			field.unregister(this);
			field2.register(this);
		}
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDir() {
		return dir;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return id == other.id;
	}
}
