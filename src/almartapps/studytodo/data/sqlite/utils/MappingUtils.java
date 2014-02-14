package almartapps.studytodo.data.sqlite.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import almartapps.studytodo.model.TaskPriority;

public class MappingUtils {

	/**
	 * Formats a Java Date object to a string conforming the SQLite DATETIME
	 * type standard format. The string obtained will be of the form "yyyy-MM-dd HH:mm".
	 * 
	 * @param date the Date to be formatted
	 * @return a string containing the correct representation of the Date
	 */
	public static String formatDateToSQL(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.parse(date);
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
	
}
