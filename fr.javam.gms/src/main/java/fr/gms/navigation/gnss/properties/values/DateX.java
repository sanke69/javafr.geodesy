package fr.gms.navigation.gnss.properties.values;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateX {
	private static final String DATE_PATTERN 	= "%d-%02d-%02d"; // ISO 8601 date format pattern
	public  static final int 	PIVOT_YEAR 		= 50;

	private int day;
	private int month;
	private int year;

	public DateX() {
		GregorianCalendar c = new GregorianCalendar();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH);
	}
	public DateX(String date) {
		setDay(Integer.parseInt(date.substring(0, 2)));
		setMonth(Integer.parseInt(date.substring(2, 4)));
		setYear(Integer.parseInt(date.substring(4)));
	}
	public DateX(int year, int month, int day) {
		setYear(year);
		setMonth(month);
		setDay(day);
	}

	public void setYear(int _year) {
		if (_year < 0 || (_year > 99 && _year < 1000) || _year > 9999)
			throw new IllegalArgumentException("Year must be two or four digit value");

		if (_year < 100 && _year > PIVOT_YEAR)
			year = 1900 + _year;
		else if (_year < 100 && _year <= PIVOT_YEAR)
			year = 2000 + _year;
		else
			year = _year;

	}
	public int getYear() {
		return year;
	}

	public void setMonth(int _month) {
		if (_month < 1 || _month > 12)
			throw new IllegalArgumentException("Month value out of bounds [1..12]");

		month = _month;
	}
	public int getMonth() {
		return month;
	}

	public void setDay(int _day) {
		if (_day < 1 || _day > 31)
			throw new IllegalArgumentException("Day out of bounds [1..31]");

		day = _day;
	}
	public int getDay() {
		return day;
	}




	public String toISO8601() {
		return String.format(DATE_PATTERN, getYear(), getMonth(), getDay());
	}

	public String toISO8601(TimeX t) {
		return toISO8601().concat("T").concat(t.toISO8601());
	}

	public java.util.Date toDate() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, getYear());
		cal.set(Calendar.MONTH, getMonth() - 1);
		cal.set(Calendar.DAY_OF_MONTH, getDay());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	@Override
	public int hashCode() {
		return toISO8601().hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof DateX) {
			DateX d = (DateX) obj;
			if (d.getDay() == getDay() && d.getMonth() == getMonth() && d.getYear() == getYear()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		int y = getYear();
		String ystr = String.valueOf(y);
		String year = ystr.substring(2);
		String date = String.format("%02d%02d%s", getDay(), getMonth(), year);
		return date;
	}

}
