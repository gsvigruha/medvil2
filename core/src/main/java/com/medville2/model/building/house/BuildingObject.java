package com.medville2.model.building.house;

import java.util.ArrayList;
import java.util.List;

import com.medville2.model.Field;
import com.medville2.model.FieldObject;
import com.medville2.model.FieldObjectType;
import com.medville2.model.Terrain;
import com.medville2.model.artifacts.Artifacts;
import com.medville2.model.society.Person;
import com.medville2.model.society.Town;
import com.medville2.model.task.GoHomeTask;
import com.medville2.model.task.MarketTask;
import com.medville2.model.time.Calendar;

public abstract class BuildingObject extends FieldObject {

	private static final long serialVersionUID = 1L;

	protected final Artifacts artifacts;
	protected int money;
	protected Town town;
	protected List<Person> people;

	public BuildingObject(Field field, FieldObjectType type) {
		super(field, type);
		this.artifacts = new Artifacts();
		this.people = new ArrayList<>();
	}

	public Artifacts getArtifacts() {
		return artifacts;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}

	public int getMoney() {
		return money;
	}

	public int getNumPeople() {
		return people.size();
	}

	public void addPerson(Person person, Terrain terrain) {
		if (person.setHome(this)) {
			person.setTask(new GoHomeTask(terrain.getField(getI(), getJ())));
		}
		this.people.add(person);
	}

	public void addMoney(int money) {
		this.money += money;
	}

	public void reassignPeople(BuildingObject bo, int numPeople, Terrain terrain) {
		if (bo == this) {
			return;
		}
		for (int i = 0; i < numPeople; i++) {
			if (people.size() > 0) {
				Person person = people.remove(0);
				bo.addPerson(person, terrain);
			}
		}
	}

	@Override
	public void tick(Terrain terrain, Calendar calendar) {
		for (Person person : people) {
			person.tick(terrain, calendar);
		}
		if (calendar.getHour() == 1 && calendar.getDay() % 90 == 0 && people.size() > 0) {
			people.get(0).setTask(new MarketTask(town.getTownsquare(), this, terrain, null, null));
		}
	}
}
