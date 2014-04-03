package almartapps.studytodo.domain.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import almartapps.studytodo.data.DAO.ScheduledClassDAO;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.sqlite.ScheduledClassDAOsqlite;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.domain.model.Course;
import almartapps.studytodo.domain.model.ScheduledClass;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.WeekDay;
import almartapps.studytodo.domain.utils.WeekDayDateMapper;
import android.content.Context;


public class TxTodayScheduledClasses extends Transaction {

	public TxTodayScheduledClasses(Context context) {
		super(context);
	}
	
	public List<ScheduledClass> getTodayScheduledClasses() {
		/*
		 * get current courses from date
		 * get the set of current subjects 
		 * get the set of scheduled classes belonging to these subjects
		 */
		
		//get current courses
		TxCurrentCourses currentCoursesTransaction = new TxCurrentCourses(context);
		List<Course> courses = currentCoursesTransaction.getCurrentCourses();
		
		//get current subjects
		SubjectDAO subjectDAO = new SubjectDAOsqlite(context);
		List<Subject> subjects = new ArrayList<Subject>();
		for (Course course : courses) {
			List<Subject> subjectsFromCourse = subjectDAO.getSubjectsFromCourse(course.getId());
			subjects.addAll(subjectsFromCourse);
		}
		
		//build subjectIds set
		Set<Long> subjectsIds = new HashSet<Long>();
		for (Subject subject : subjects) {
			subjectsIds.add(subject.getId());
		}
		
		//get scheduled classes
		ScheduledClassDAO classesDAO = new ScheduledClassDAOsqlite(context);
		Date today = Calendar.getInstance(Locale.getDefault()).getTime();
		WeekDay weekDay = WeekDayDateMapper.mapDate(today); 
		List<ScheduledClass> classes = classesDAO.getScheduledClasses(weekDay, subjectsIds);
		
		//return
		return classes;
	}
	
}
