package com.medville2.model.society;

import java.io.Serializable;
import java.util.Objects;

import com.google.common.collect.ImmutableSet;
import com.medville2.model.Field;
import com.medville2.model.Terrain;
import com.medville2.model.building.house.BuildingObject;
import com.medville2.model.building.infra.Road;
import com.medville2.model.task.Task;
import com.medville2.model.terrain.Path;
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
	private Path path;

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
		if (path != null) {
			int steps = 1;
			if (field.getObject() != null && field.getObject().getType() == Road.Type) {
				steps = 2;
			}
			Field dest = path.nextField();
			if (dest != null) {
				if (dest == field) {
					path.consumeField();
				}
				for (int i = 0; i < steps; i++) {
					if (dest.getI() * Person.D + Person.D / 2 < x) {
						x--;
						dir = 0;
					} else if (dest.getI() * Person.D + Person.D / 2 > x) {
						x++;
						dir = 2;
					} else if (dest.getJ() * Person.D + Person.D / 2 < y) {
						y--;
						dir = 1;
					} else if (dest.getJ() * Person.D + Person.D / 2 > y) {
						y++;
						dir = 3;
					}
				}
			} else {

			}
		} else if (task != null) {
			path = Path.findPath(field, ImmutableSet.of(task.nextDestination()), terrain, Field::walkable);
		}

		Field field2 = getField(terrain);
		if (field != field2) {
			field.unregister(this);
			field2.register(this);
		}
	}

	public void setTask(Task task) {
		this.task = task;
		this.path = null;
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
