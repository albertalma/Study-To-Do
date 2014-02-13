package almartapps.studytodo.data.sqlite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import almartapps.studytodo.model.TaskPriority;

public class MappingUtils {

	public static String formatDateToSQL(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(date);
	}
	
	public static Date parseSQLDate(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.parse(date);
	}

	public static int mapPriorityToSQL(TaskPriority priority) {
		switch (priority) {
			case high: return 3;
			case medium: return 2;
			case low: return 1;
		}
		return 0;
	}
	
	public static TaskPriority parseSQLPriority(int priority) {
		switch (priority) {
			case 3: return TaskPriority.high;
			case 2: return TaskPriority.medium;
			case 1: return TaskPriority.low;
		}
		throw new org.apache.http.ParseException("Invalid stored priority. Can't parse priority: " + priority);
	}
	
}
