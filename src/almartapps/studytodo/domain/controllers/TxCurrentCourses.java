package almartapps.studytodo.domain.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import almartapps.studytodo.data.DAO.CourseDAO;
import almartapps.studytodo.data.sqlite.CourseDAOsqlite;
import almartapps.studytodo.domain.model.Course;
import android.content.Context;

public class TxCurrentCourses extends Transaction {

	public TxCurrentCourses(Context context) {
		super(context);
	}

	public List<Course> getCurrentCourses() {
		//get current date
		//calendar is set to the current date and time
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		Date currentDate = calendar.getTime();
		
		//get course
		CourseDAO courseDAO = new CourseDAOsqlite(context);
		List<Course> courses = courseDAO.getCourse(currentDate);
		
		//return
		return courses;
	}
	
}
