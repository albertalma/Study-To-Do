package almartapps.studytodo.data.sqlite.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import almartapps.studytodo.domain.model.ClassType;
import almartapps.studytodo.domain.model.TaskPriority;
import almartapps.studytodo.domain.model.Time;
import almartapps.studytodo.domain.model.WeekDay;

public class MappingUtils {

	/**
	 * Formats a Java Date object to a string conforming the SQLite DATETIME
	 * type standard format. The string obtained will be of the form "yyyy-MM-dd HH:mm".
	 * 
	 * @param date the Date to be formatted
	 * @return a string containing the correct representation of the Date
	 */
	public static String formatDateToSQL(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
		return formatter.format(date);
	}
	
	/**
	 * Parses an SQLite DATETIME string to a Java Date object. The string must
	 * conform to the format: "yyyy-MM-dd HH:mm" in order to be parsed.
	 * 
	 * @param date the String containing the date stored in a SQLite database
	 * @return the Java Date object correctly parsed
	 * @throws ParseException if the string could not be parsed according to the expected format
	 */
	public static Date parseSQLDate(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
		return formatter.parse(date);
	}
	
	/**
	 * Formats a {@link Time} object to a string conforming to the pattern "HH:mm".
	 * 
	 * @param time the Time to be formatted
	 * @return a String containing the right representation of the Time object
	 */
	public static String formatTimeToSQL(Time time) {
		String formatted = "";
		if (time.getHour() < 10) formatted = formatted + "0";
		formatted = formatted + String.valueOf(time.getHour());
		formatted = formatted + ":";
		if (time.getMinute() < 10) formatted = formatted + "0";
		formatted = formatted + String.valueOf(time.getMinute());
		return formatted;
	}
	
	/**
	 * Parses an SQLite DATETIME string to a {@link Time} object. The string must
	 * conform to the format "HH:mm" in order to be parsed.
	 * 
	 * @param sqlTime the String containing the stored Time object
	 * @return the Time object correctly parsed according to the expected format
	 */
	public static Time parseSQLTime(String sqlTime) {
		int hour = Integer.parseInt(sqlTime.substring(0, 2));
		int minute = Integer.parseInt(sqlTime.substring(3, 5));
		Time time = new Time(hour, minute);
		return time;
	}
	
	/**
	 * Maps a TaskPriority (an element of an enumeration) to an integer,
	 * to be able to store it in an SQLite database.
	 * If used with its counterpart, this method provides a consistent bijective
	 * mapping (see {@link parseSQLPriority})
	 * 
	 * @param priority the TaskPriority to be mapped.
	 * @return the integer representing that priority.
	 */
	public static int mapPriorityToSQL(TaskPriority priority) {
		if (priority == null) return 0;
		switch (priority) {
			case high: return 3;
			case medium: return 2;
			case low: return 1;
			default: return 0;
		}
	}
	
	/**
	 * Parses an integer retrieved from an SQLite database to a TaskPriority
	 * element.
	 * If used with its counterpart, this method provides a consistent bijective
	 * mapping (see {@link mapPriorityToSQL})
	 * 
	 * @param priority the integer retrieved from the database
	 * @return the TaskPriority corresponding to this representation
	 */
	public static TaskPriority parseSQLPriority(int priority) throws SQLiteParseException {
		switch (priority) {
			case 3: return TaskPriority.high;
			case 2: return TaskPriority.medium;
			case 1: return TaskPriority.low;
			case 0: return null;
		}
		throw new SQLiteParseException("Invalid stored priority. Can't parse priority: " + priority);
	}
	
	/**
	 * Maps a {@link WeekDay} value to an integer, to be able to store it
	 * in an SQLite database.
	 * 
	 * @param weekDay the WeekDay value to be mapped
	 * @return an integer representing the mapping
	 */
	public static int mapWeekDayToSQL(WeekDay weekDay) {
		if (weekDay == null) return 0;
		switch (weekDay) {
			case monday: return 1;
			case tuesday: return 2;
			case wednesday: return 3;
			case thursday: return 4;
			case friday: return 5;
			case saturday: return 6;
			case sunday: return 7;
			default: return 0;
		}
	}
	
	/**
	 * Parses an integer retrieved from an SQLite database to a {@link WeekDay} object.
	 * 
	 * @param weekDay the integer retrieved from the database
	 * @return the WeekDay corresponding to its representation
	 */
	public static WeekDay parseSQLWeekDay (int weekDay) throws SQLiteParseException {
		switch (weekDay) {
			case 0: return null;
			case 1:	return WeekDay.monday;
			case 2: return WeekDay.tuesday;
			case 3: return WeekDay.wednesday;
			case 4: return WeekDay.thursday;
			case 5: return WeekDay.friday;
			case 6: return WeekDay.saturday;
			case 7: return WeekDay.sunday;
		}
		throw new SQLiteParseException("Invalid stored weekday. Can't parse weekday: " + weekDay);
	}
	
	public static int mapClassTypeToSQL(ClassType type) {
		if (type == null) return 0;
		switch (type) {
			case laboratory: return 1;
			case practice: return 2;
			case problems: return 3;
			case theory: return 4;
			default: return 0;
		}
	}
	
	public static ClassType parseSQLClassType(int type) throws SQLiteParseException {
		switch (type) {
			case 0: return null;
			case 1: return ClassType.laboratory;
			case 2: return ClassType.practice;
			case 3: return ClassType.problems;
			case 4: return ClassType.theory;
		}
		throw new SQLiteParseException("Invalid stored ClassType. Can't parse ClassType: " + type);
	}
	
}
