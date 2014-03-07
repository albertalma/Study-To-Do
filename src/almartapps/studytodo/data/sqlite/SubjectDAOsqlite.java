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
		Subject subject = mapSubject(cursor);
		
		//release connection
		db.close();
		
		return subject;
	}
	
	@Override
	public List<Subject> getAll() {
		// TODO Auto-generated method stub
		return null;
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
		Log.i(TAG, "inserting subject with name=" + subject.getName());
		long id = db.insert(SubjectsTable.TABLE_SUBJECTS, null, values);
		
		//set the ID returned to the task
		subject.setId(id);
		
		//release connection
		db.close();
		
		//return
		return subject;
	}

	@Override
	public boolean delete(Subject object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private ContentValues getContentValues(Subject subject) {
		ContentValues values = new ContentValues();
		
		values.put(SubjectsTable.NAME_COLUMN, subject.getName());
		//TODO getContentValues
		//values.put(SubjectsTable., value)
		
		/*
		values.put(TasksTable.SUBJECT_KEY_COLUMN, task.getSubjectId());
		values.put(TasksTable.NAME_COLUMN, task.getName()); //not null
		if (task.getDescription() != null) {
			values.put(TasksTable.DESCRIPTION_COLUMN, task.getDescription());
		} else {
			values.putNull(TasksTable.DESCRIPTION_COLUMN);
		}
		if (task.getDueDate() != null) {
			values.put(TasksTable.DUE_DATE_COLUMN, MappingUtils.formatDateToSQL(task.getDueDate()));
		} else {
			values.putNull(TasksTable.DUE_DATE_COLUMN);
		}
		values.put(TasksTable.PRIORITY_COLUMN, MappingUtils.mapPriorityToSQL(task.getPriority())); //not null
		values.put(TasksTable.COMPLETED_COLUMN, task.isCompleted()); //not null
		
		 */
		
		return values;
	}
	
	private Subject mapSubject(Cursor cursor) {
		//TODO mapSubject
		Subject subject = new Subject();
		//id
		subject.setId(cursor.getLong(0));
		return subject;
	}

}
