package almartapps.studytodo.data.sqlite;

import java.util.List;

import almartapps.studytodo.data.DAO.ProfessorDAO;
import almartapps.studytodo.data.exceptions.ObjectNotExistsException;
import almartapps.studytodo.data.exceptions.TaskNotExistsException;
import almartapps.studytodo.data.sqlite.tables.ProfessorsTable;
import almartapps.studytodo.data.sqlite.tables.TasksTable;
import almartapps.studytodo.data.sqlite.tables.TaughtSubjectTable;
import almartapps.studytodo.domain.model.Professor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProfessorDAOsqlite extends GenericDAOsqlite<Professor> implements ProfessorDAO {

	private static final String TAG = "data.sqlite.ProfessorDAOsqlite";
	
	public ProfessorDAOsqlite(Context context) {
		super(context);
	}
	
	@Override
	public Professor get(long professorId) throws ObjectNotExistsException {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = 
				"SELECT * FROM " + ProfessorsTable.TABLE_PROFESSORS + 
				" WHERE " + TasksTable.ID_COLUMN + " = ?";
		String [] selectionArgs = new String[]{String.valueOf(professorId)}; 
		Cursor cursor = db.rawQuery(queryStatement, selectionArgs);
		
		//check result
		if (cursor.getCount() != 1) 
			throw new TaskNotExistsException("No Professor exists with ID=" + professorId);
		
		//map result
		cursor.moveToFirst();
		Professor professor = map(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return professor;
	}

	@Override
	public List<Professor> getAll() {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = "SELECT * FROM " + ProfessorsTable.TABLE_PROFESSORS;
		Log.i(TAG, "getting all Professors stored. SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		cursor.moveToFirst();
		List<Professor> professors = mapAll(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return professors;
	}

	@Override
	public List<Professor> getProfessorsFromSubject(long subjectId) {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement =
				"SELECT prof.* " +  
				"FROM " + ProfessorsTable.TABLE_PROFESSORS + " prof, " 
						+ TaughtSubjectTable.TABLE_TAUGH_SUBJECT + " taught " + 
				"WHERE prof." + ProfessorsTable.ID_COLUMN + " = taught." + TaughtSubjectTable.PROFESSOR_KEY_COLUMN +
					" AND " + subjectId + " = taught." + TaughtSubjectTable.SUBJECT_KEY_COLUMN;
		Log.i(TAG, "getting all Professors from the Subject with id=" + subjectId + ". SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		cursor.moveToFirst();
		List<Professor> professors = mapAll(cursor);
		cursor.close();
		
		//release connection
		closeDb(db);
		
		return professors;
	}
	
	@Override
	public Professor insert(Professor professor) {
		if (professor.getName() == null)
			throw new IllegalArgumentException("Argument 'name' was null, but a Professor must have a name.");

		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//get mapping for values
		ContentValues values = getContentValues(professor);
		
		//perform insert
		Log.i(TAG, "inserting Professor with name=" + professor.getName());
		long id = db.insert(ProfessorsTable.TABLE_PROFESSORS, null, values);
		
		//set the ID returned to the task
		professor.setId(id);
		
		//release connection
		closeDb(db);
		
		//return
		return professor;
	}

	@Override
	public boolean delete(Professor professor) {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//delete object
		String whereStatement = "_id = ?";
		String [] whereArgs = new String[]{String.valueOf(professor.getId())};
		Log.i(TAG, "deleting Professor with _id=" + professor.getId() 
				+ ". SQL WHERE clause is: " + whereStatement + ", " + professor.getId());
		int count = db.delete(ProfessorsTable.TABLE_PROFESSORS, whereStatement, whereArgs);
		
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
		Log.i(TAG, "deleting ALL Professors (this is not a 'DROP TABLE' statement)");
		int count = db.delete(ProfessorsTable.TABLE_PROFESSORS, whereClause, null);
		
		//release connection
		closeDb(db);
		
		//return
		return count;
	}
	
	@Override
	public boolean assignSubject(long professorId, long subjectId) {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//get TaughtSubjectManager
		TaughtSubjectManager manager = new TaughtSubjectManager(db);
		
		//insert assignment
		boolean result = manager.insertAssignment(subjectId, professorId);
		
		//release connection
		closeDb(db);
		
		//return
		return result;
	}
	
	@Override
	public boolean removeSubjectAssignment(long professorId, long subjectId) {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//get TaughtSubjectManager
		TaughtSubjectManager manager = new TaughtSubjectManager(db);
		
		//insert assignment
		boolean result = manager.deleteAssignment(subjectId, professorId);
		
		//release connection
		closeDb(db);
		
		//return
		return result;
	}

	@Override
	protected ContentValues getContentValues(Professor professor) {
		ContentValues values = new ContentValues();
		values.put(ProfessorsTable.NAME_COLUMN, professor.getName());
		if (professor.getEmailAddress() != null) {
			values.put(ProfessorsTable.EMAIL_COLUMN, professor.getEmailAddress());
		} else values.putNull(ProfessorsTable.EMAIL_COLUMN);
		if (professor.getOfficeAddress() != null) {
			values.put(ProfessorsTable.OFFICE_ADDRESS_COLUMN, professor.getOfficeAddress());
		} else {
			values.putNull(ProfessorsTable.OFFICE_ADDRESS_COLUMN);
		}
		if (professor.getTelephone() != null) {
			values.put(ProfessorsTable.TELEPHONE_COLUMN, professor.getTelephone());
		} else {
			values.putNull(ProfessorsTable.TELEPHONE_COLUMN);
		}
		return values;
	}

	@Override
	protected Professor map(Cursor cursor) {
		Professor professor = new Professor();
		//id
		professor.setId(cursor.getLong(0));
		//name, not null
		professor.setName(cursor.getString(1));
		//email
		if (!cursor.isNull(2)) professor.setEmailAddress(cursor.getString(2));
		else professor.setEmailAddress(null);
		//office address
		if (!cursor.isNull(3)) professor.setOfficeAddress(cursor.getString(3));
		else professor.setOfficeAddress(null);
		//telephone
		if (!cursor.isNull(4)) professor.setTelephone(cursor.getString(4));
		else professor.setTelephone(null);
		return professor;
	}

}
