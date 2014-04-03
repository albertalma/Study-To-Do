package almartapps.studytodo.domain.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import almartapps.studytodo.domain.model.WeekDay;

public class WeekDayDateMapper {

	public static WeekDay mapDate(Date date) {
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTime(date);
		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		switch (dayOfWeek) {
			case Calendar.MONDAY: return WeekDay.monday;
			case Calendar.TUESDAY: return WeekDay.tuesday;
			case Calendar.WEDNESDAY: return WeekDay.wednesday;
			case Calendar.THURSDAY: return WeekDay.thursday;
			case Calendar.FRIDAY: return WeekDay.friday;
			case Calendar.SATURDAY: return WeekDay.saturday;
			case Calendar.SUNDAY: return WeekDay.sunday;
			default: return WeekDay.sunday;
		}
	}
	
}
