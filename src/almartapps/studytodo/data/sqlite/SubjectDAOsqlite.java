package almartapps.studytodo.data.sqlite;

import java.util.List;

import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.exceptions.SubjectNotExistsException;
import almartapps.studytodo.data.sqlite.tables.SubjectsTable;
import almartapps.studytodo.domain.model.Subject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SubjectDAOsqlite extends GenericDAOsqlite<Subject> implements SubjectDAO {

	private static final String TAG = "data.sqlite.SubjectDAOsqlite";
	
	public SubjectDAOsqlite(Context context) {
		super(context);
	}

	@Override
	public Subject get(long subjectId) throws SubjectNotExistsException {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = 
				"SELECT * FROM " + SubjectsTable.TABLE_SUBJECTS + 
				" WHERE " + SubjectsTable.ID_COLUMN + " = ?";
		String [] selectionArgs = new String[]{String.valueOf(subjectId)}; 
		Cursor cursor = db.rawQuery(queryStatement, selectionArgs);
		
		//check result
		if (cursor.getCount() != 1) 
			throw new SubjectNotExistsException("No Subject exists with ID=" + subjectId);
		
		//map result
		cursor.moveToFirst();
		Subject subject = map(cursor);
		
		//release connection
		db.close();
		
		return subject;
	}
	
	@Override
	public List<Subject> getAll() {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = "SELECT * FROM " + SubjectsTable.TABLE_SUBJECTS;
		Log.i(TAG, "getting all Subjects stored. SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		cursor.moveToFirst();
		List<Subject> subjects = mapAll(cursor);
		
		//release connection
		db.close();
		
		return subjects;
	}
	
	@Override
	public List<Subject> getSubjectsFromCourse(long courseId) {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = "SELECT * FROM " + SubjectsTable.TABLE_SUBJECTS +
				" WHERE " + SubjectsTable.COURSE_KEY_COLUMN + " = " + courseId;
		Log.i(TAG, "getting all Subjects belonging to the course with id=" + courseId + 
				". SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		cursor.moveToFirst();
		List<Subject> subjects = mapAll(cursor);
		
		//release connection
		db.close();
		
		return subjects;
	}

	@Override
	public Subject insert(Subject subject) {
		if (subject.getName() == null)
			throw new IllegalArgumentException("Argument 'name' was null, but a Subject must have a name.");
		
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//get mapping for values
		ContentValues values = getContentValues(subject);
		
		//perform insert
		Log.i(TAG, "inserting Subject with name=" + subject.getName());
		long id = db.insert(SubjectsTable.TABLE_SUBJECTS, null, values);
		
		//set the ID returned to the task
		subject.setId(id);
		
		//release connection
		db.close();
		
		//return
		return subject;
	}

	@Override
	public boolean delete(Subject subject) {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//delete object
		String whereStatement = "_id = ?";
		String [] whereArgs = new String[]{String.valueOf(subject.getId())};
		Log.i(TAG, "deleting Subject with _id=" + subject.getId() 
				+ ". SQL WHERE clause is: " + whereStatement + ", " + subject.getId());
		int count = db.delete(SubjectsTable.TABLE_SUBJECTS, whereStatement, whereArgs);
		
		//release connection
		db.close();
		
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
		Log.i(TAG, "deleting ALL Subjects (this is not a 'DROP TABLE' statement)");
		int count = db.delete(SubjectsTable.TABLE_SUBJECTS, whereClause, null);
		
		//release connection
		db.close();
		
		//return
		return count;
	}
	
	@Override
	protected ContentValues getContentValues(Subject subject) {
		ContentValues values = new ContentValues();
		values.put(SubjectsTable.NAME_COLUMN, subject.getName());
		values.put(SubjectsTable.COURSE_KEY_COLUMN, subject.getCourseId());
		return values;
	}
	
	@Override
	protected Subject map(Cursor cursor) {
		Subject subject = new Subject();
		//id
		subject.setId(cursor.getLong(0));
		//course_id
		subject.setCourseId(cursor.getLong(1));
		//name
		subject.setName(cursor.getString(2));
		return subject;
	}

}
