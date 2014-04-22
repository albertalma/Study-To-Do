package almartapps.studytodo.view.utils;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

public class Utils {
	
	public static String getPrettyDate(Date date) {
		PrettyTime prettytime = new PrettyTime();
		return prettytime.format(date);
	}

}
