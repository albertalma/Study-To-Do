package almartapps.studytodo.data.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import almartapps.studytodo.data.DAO.ScheduledClassDAO;
import almartapps.studytodo.data.exceptions.ObjectNotExistsException;
import almartapps.studytodo.data.exceptions.TaskNotExistsException;
import almartapps.studytodo.data.sqlite.tables.ScheduledClassesTable;
import almartapps.studytodo.data.sqlite.tables.TasksTable;
import almartapps.studytodo.data.sqlite.utils.MappingUtils;
import almartapps.studytodo.data.sqlite.utils.SQLiteParseException;
import almartapps.studytodo.domain.model.ScheduledClass;
import almartapps.studytodo.domain.model.WeekDay;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ScheduledClassDAOsqlite extends GenericDAOsqlite<ScheduledClass> implements ScheduledClassDAO {

	private static final String TAG = "data.sqlite.ScheduledClassDAOsqlite";
	
	public ScheduledClassDAOsqlite(Context context) {
		super(context);
	}

	@Override
	public ScheduledClass get(long classId) throws ObjectNotExistsException {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = 
				"SELECT * FROM " + ScheduledClassesTable.TABLE_SCHEDULED_CLASS + 
				" WHERE " + TasksTable.ID_COLUMN + " = ?";
		String [] selectionArgs = new String[]{String.valueOf(classId)}; 
		Cursor cursor = db.rawQuery(queryStatement, selectionArgs);
		
		//check result
		if (cursor.getCount() != 1) 
			throw new TaskNotExistsException("No ScheduledClass exists with ID=" + classId);
		
		//map result
		cursor.moveToFirst();
		ScheduledClass scheduledClass= map(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return scheduledClass;
	}

	@Override
	public List<ScheduledClass> getAll() {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = "SELECT * FROM " + ScheduledClassesTable.TABLE_SCHEDULED_CLASS;
		Log.i(TAG, "getting all ScheduledClasses stored. SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		cursor.moveToFirst();
		List<ScheduledClass> classes = mapAll(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return classes;
	}
	
	@Override
	public List<ScheduledClass> getScheduledClasses(WeekDay weekDay, Set<Long> subjectsIds) {
		//first: dummy check
		if (subjectsIds.size() <= 0)
			return new ArrayList<ScheduledClass>();
		
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String whereInClause = "(";
		boolean first = true;
		for (Long subjectId : subjectsIds) {
			if (first) {
				whereInClause = whereInClause + subjectId;
				first = false;
			}
			else {
				whereInClause = whereInClause + "," + subjectId;
			}
		}
		whereInClause += ")";
		String queryStatement = "SELECT * FROM " + ScheduledClassesTable.TABLE_SCHEDULED_CLASS +
				" WHERE " + ScheduledClassesTable.SUBJECT_KEY_COLUMN + " IN " + whereInClause +
				" AND " + ScheduledClassesTable.WEEK_DAY_COLUMN + " = " + MappingUtils.mapWeekDayToSQL(weekDay) +
				" ORDER BY " + ScheduledClassesTable.START_TIME_COLUMN;
		Log.i(TAG, "getting ScheduledClasses in weekDay=" + weekDay + ". SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		cursor.moveToFirst();
		List<ScheduledClass> classes = mapAll(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return classes;
	}
	
	@Override
	public List<ScheduledClass> getScheduledClassesFromSubject(long subjectId) {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = "SELECT * FROM " + ScheduledClassesTable.TABLE_SCHEDULED_CLASS +
				" WHERE " + ScheduledClassesTable.SUBJECT_KEY_COLUMN + " = " + subjectId;
		Log.i(TAG, "getting all ScheduledClasses belonging to the Subject with id=" + subjectId + 
				". SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		cursor.moveToFirst();
		List<ScheduledClass> scheduledClasses = mapAll(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return scheduledClasses;
	}

	@Override
	public ScheduledClass insert(ScheduledClass scheduledClass) {
		if (scheduledClass.getWeekDay() == null)
			throw new IllegalArgumentException("Argument 'weekDay' was null, but a ScheduledClass must have a weekDay.");
		if (scheduledClass.getStartTime() == null) 
			throw new IllegalArgumentException("Argument 'startTime' was null, but a ScheduledClass must have a startTime.");
		if (scheduledClass.getEndTime() == null) 
			throw new IllegalArgumentException("Argument 'endTime' was null, but a ScheduledClass must have an endTime.");
		if (scheduledClass.getType() == null) 
			throw new IllegalArgumentException("Argument 'type' was null, but a ScheduledClass must have a type.");
		
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//get mapping for values
		ContentValues values = getContentValues(scheduledClass);
		
		//perform insert
		Log.i(TAG, "inserting ScheduledTask");
		long id = db.insert(ScheduledClassesTable.TABLE_SCHEDULED_CLASS, null, values);
		
		//set the ID returned to the task
		scheduledClass.setId(id);
		
		//release connection
		closeDb(db);
		
		//return
		return scheduledClass;
	}

	@Override
	public boolean delete(ScheduledClass scheduledClass) {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//delete object
		String whereStatement = "_id = ?";
		String [] whereArgs = new String[]{String.valueOf(scheduledClass.getId())};
		Log.i(TAG, "deleting ScheduledClass with _id=" + scheduledClass.getId() 
				+ ". SQL WHERE clause is: " + whereStatement + ", " + scheduledClass.getId());
		int count = db.delete(ScheduledClassesTable.TABLE_SCHEDULED_CLASS, whereStatement, whereArgs);
		
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
		Log.i(TAG, "deleting ALL ScheduledClasses (this is not a 'DROP TABLE' statement)");
		int count = db.delete(ScheduledClassesTable.TABLE_SCHEDULED_CLASS, whereClause, null);
		
		//release connection
		closeDb(db);
		
		//return
		return count;
	}

	@Override
	protected ContentValues getContentValues(ScheduledClass scheduledClass) {
		ContentValues values = new ContentValues();
		values.put(ScheduledClassesTable.SUBJECT_KEY_COLUMN, scheduledClass.getSubjectId());
		values.put(ScheduledClassesTable.WEEK_DAY_COLUMN, MappingUtils.mapWeekDayToSQL(scheduledClass.getWeekDay()));
		values.put(ScheduledClassesTable.START_TIME_COLUMN, MappingUtils.formatTimeToSQL(scheduledClass.getStartTime()));
		values.put(ScheduledClassesTable.END_TIME_COLUMN, MappingUtils.formatTimeToSQL(scheduledClass.getEndTime()));
		if (scheduledClass.getPlace() != null) {
			values.put(ScheduledClassesTable.PLACE_COLUMN, scheduledClass.getPlace());
		} else {
			values.putNull(ScheduledClassesTable.PLACE_COLUMN);
		}
		values.put(ScheduledClassesTable.TYPE_COLUMN, MappingUtils.mapClassTypeToSQL(scheduledClass.getType()));
		return values;
	}

	@Override
	protected ScheduledClass map(Cursor cursor) {
		ScheduledClass scheduledClass = new ScheduledClass();
		scheduledClass.setId(cursor.getLong(0));
		scheduledClass.setSubjectId(cursor.getLong(1));
		try {
			scheduledClass.setWeekDay(MappingUtils.parseSQLWeekDay(cursor.getInt(2)));
		} catch (SQLiteParseException e) {
			Log.e(TAG, e.getMessage());
		}
		scheduledClass.setStartTime(MappingUtils.parseSQLTime(cursor.getString(3)));
		scheduledClass.setEndTime(MappingUtils.parseSQLTime(cursor.getString(4)));
		if (cursor.getString(5) != null) {
			scheduledClass.setPlace(cursor.getString(5));
		}
		else scheduledClass.setPlace(null);
		try {
			scheduledClass.setType(MappingUtils.parseSQLClassType(cursor.getInt(6)));
		} catch (SQLiteParseException e) {
			Log.e(TAG, e.getMessage());
		}
		return scheduledClass;
	}

}
