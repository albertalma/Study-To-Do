package almartapps.studytodo.data.sqlite;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import almartapps.studytodo.data.DAO.CourseDAO;
import almartapps.studytodo.data.exceptions.CourseNotExistsException;
import almartapps.studytodo.data.sqlite.tables.CoursesTable;
import almartapps.studytodo.data.sqlite.utils.MappingUtils;
import almartapps.studytodo.domain.model.Course;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CourseDAOsqlite extends GenericDAOsqlite<Course> implements CourseDAO {

	private static final String TAG = "data.sqlite.CourseDAOsqlite";
	
	public CourseDAOsqlite(Context context) {
		super(context);
	}

	@Override
	public Course get(long courseId) throws CourseNotExistsException {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = 
				"SELECT * FROM " + CoursesTable.TABLE_COURSES + 
				" WHERE " + CoursesTable.ID_COLUMN + " = ?";
		String [] selectionArgs = new String[]{String.valueOf(courseId)}; 
		Cursor cursor = db.rawQuery(queryStatement, selectionArgs);
		
		//check result
		if (cursor.getCount() != 1) 
			throw new CourseNotExistsException("No Course exists with ID=" + courseId);
		
		//map result
		cursor.moveToFirst();
		Course course = map(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return course;
	}
	
	@Override
	public List<Course> getCourse(Date date) {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String formattedDate = MappingUtils.formatDateToSQL(date);
		String queryStatement = "SELECT * FROM " + CoursesTable.TABLE_COURSES + 
				" WHERE " + CoursesTable.START_DATE_COLUMN + " >= datetime('" + formattedDate + "') " +
						"AND " + CoursesTable.END_DATE_COLUMN + " <= datetime('" + formattedDate + "')";
		Log.i(TAG, "getting Courses that overlap with date=" + formattedDate + ". SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		cursor.moveToFirst();
		List<Course> courses = mapAll(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return courses;
	}
	
	@Override
	public List<Course> getAll() {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = "SELECT * FROM " + CoursesTable.TABLE_COURSES;
		Log.i(TAG, "getting all Courses stored. SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		cursor.moveToFirst();
		List<Course> courses = mapAll(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return courses;
	}

	@Override
	public Course insert(Course course) {
		if (course.getName() == null)
			throw new IllegalArgumentException("Argument 'name' was null, but a Course must have a name.");
		if (course.getStartDate() == null)
			throw new IllegalArgumentException("Argument 'startDate' was null, but a Course must have a startDate.");
		if (course.getEndDate() == null)
			throw new IllegalArgumentException("Argument 'endDate' was null, but a Course must have an endDate.");
		
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//get mapping for values
		ContentValues values = getContentValues(course);
		
		//perform insert
		Log.i(TAG, "inserting Course with name=" + course.getName());
		long id = db.insert(CoursesTable.TABLE_COURSES, null, values);
		
		//set the ID returned to the task
		course.setId(id);
		
		//release connection
		closeDb(db);
		
		//return
		return course;
	}

	@Override
	public boolean delete(Course course) {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//delete object
		String whereStatement = "_id = ?";
		String [] whereArgs = new String[]{String.valueOf(course.getId())};
		Log.i(TAG, "deleting course with _id=" + course.getId() 
				+ ". SQL WHERE clause is: " + whereStatement + ", " + course.getId());
		int count = db.delete(CoursesTable.TABLE_COURSES, whereStatement, whereArgs);
		
		//release connection
		closeDb(db);
		
		
		//return
		return count > 0;
	}

	@Override
	public int deleteAll() {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//delete all objects
		String whereClause = "1"; //this is the value we must pass to the delete() method to
						//delete all rows and still get the count for it
		Log.i(TAG, "deleting ALL Courses (this is not a 'DROP TABLE' statement)");
		int count = db.delete(CoursesTable.TABLE_COURSES, whereClause, null);
		
		//release connection
		closeDb(db);
		
		//return
		return count;
	}
	
	@Override
	protected ContentValues getContentValues(Course course) {
		ContentValues values = new ContentValues();
		values.put(CoursesTable.NAME_COLUMN, course.getName());
		values.put(CoursesTable.START_DATE_COLUMN, MappingUtils.formatDateToSQL(course.getStartDate()));
		values.put(CoursesTable.END_DATE_COLUMN, MappingUtils.formatDateToSQL(course.getEndDate()));
		return values;
	}
	
	@Override
	protected Course map(Cursor cursor) {
		Course course = new Course();
		course.setId(cursor.getLong(0));
		course.setName(cursor.getString(1));
		try {
			course.setStartDate(MappingUtils.parseSQLDate(cursor.getString(2)));
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
		}
		try {
			course.setEndDate(MappingUtils.parseSQLDate(cursor.getString(3)));
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
		}
		return course;
	}

}
