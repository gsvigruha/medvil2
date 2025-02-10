package com.medville2.model.time;

import java.io.Serializable;

public class Calendar implements Serializable {

	private static final long serialVersionUID = 1L;

	private int year;
	private int day;
	private int hour;

	public void tick() {
		hour++;
		if (hour == 25) {
			hour = 1;
			day++;
		}
		if (day == 366) {
			day = 1;
			year++;
		}
	}

	public int getYear() {
		return year;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public String render() {
		return String.format("year of %d, day %d", year, day);
	}

	public boolean isTaxTime() {
		return hour ==1 && day == 1;
	}

	public boolean isMarketTime() {
		return hour ==1 && day % 90 == 0;
	}
}
