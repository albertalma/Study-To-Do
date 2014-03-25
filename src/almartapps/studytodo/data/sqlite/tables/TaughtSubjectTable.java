package almartapps.studytodo.data.sqlite.tables;

public class TaughtSubjectTable {
	
	/**
	 * TaughtSubject table name
	 */
	public static final String TABLE_TAUGH_SUBJECT = "taught_subject";
	
	/**
	 * TaughtSubject table SUBJECT_KEY_COLUMN (INTEGER, PK, FK)
	 */
	public static final String SUBJECT_KEY_COLUMN = "subject_id";
	
	/**
	 * TaughtSubject table PROFESSOR_KEY_COLUMN (INTEGER, PK, FK)
	 */
	public static final String PROFESSOR_KEY_COLUMN = "professor_id";
	
	public static final String CREATE_TAUGHT_SUBJECT_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_TAUGH_SUBJECT + " (" +
				SUBJECT_KEY_COLUMN + " INTEGER, " +
				PROFESSOR_KEY_COLUMN + " INTEGER, " +
				"PRIMARY KEY (" + 
					SUBJECT_KEY_COLUMN + ", " +
					PROFESSOR_KEY_COLUMN + "), " +
				"FOREIGN KEY (" +
					SUBJECT_KEY_COLUMN + ") REFERENCES " + SubjectsTable.TABLE_SUBJECTS + ", " +
				"FOREIGN KEY (" +
					PROFESSOR_KEY_COLUMN + ") REFERENCES " + ProfessorsTable.TABLE_PROFESSORS +
			")";
	
	public static final String DROP_TAUGHT_SUBJECT_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_TAUGH_SUBJECT;
	
}
