package almartapps.studytodo.data.sqlite;

import almartapps.studytodo.data.sqlite.tables.TaughtSubjectTable;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class TaughtSubjectManager {

	private SQLiteDatabase db;
	
	@SuppressWarnings("unused")
	private TaughtSubjectManager() { }
	
	public TaughtSubjectManager(SQLiteDatabase db) {
		this.db = db;
	}
	
	public boolean insertAssignment(long subjectId, long professorId) {
		ContentValues values = mapValues(subjectId, professorId);
		long count = db.insert(TaughtSubjectTable.TABLE_TAUGH_SUBJECT, null, values);
		return count > 1;
	}
	
	public boolean deleteAssignment(long subjectId, long professorId) {
		String whereClause = TaughtSubjectTable.SUBJECT_KEY_COLUMN + " = ? AND " + TaughtSubjectTable.PROFESSOR_KEY_COLUMN + " = ?"; 
		String [] whereArgs = new String[]{String.valueOf(subjectId), String.valueOf(professorId)};
		int count = db.delete(TaughtSubjectTable.TABLE_TAUGH_SUBJECT, whereClause, whereArgs);
		return count > 0;
	}
	
	private ContentValues mapValues(long subjectId, long professorId) {
		ContentValues values = new ContentValues();
		values.put(TaughtSubjectTable.SUBJECT_KEY_COLUMN, subjectId);
		values.put(TaughtSubjectTable.PROFESSOR_KEY_COLUMN, professorId);
		return values;
	}
	
}
