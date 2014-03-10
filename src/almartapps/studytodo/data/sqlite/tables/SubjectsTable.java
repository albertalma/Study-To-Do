package almartapps.studytodo.data.sqlite.tables;

public class SubjectsTable {

	/**
	 * Subjects table name
	 */
	public static final String TABLE_SUBJECTS = "subjects";
	
	/**
	 * Subjects table ID column (INTEGER, PK, AUTOINCREMENT)
	 */
	public static final String ID_COLUMN = "_id";
	
	/**
	 * Subjects table COURSE_KEY column (INTEGER, FK)
	 */
	public static final String COURSE_KEY_COLUMN = "course_id";
	
	/**
	 * Subjects table NAME column (TEXT, NOT NULL)
	 */
	public static final String NAME_COLUMN = "name";

	public static final String CREATE_SUBJECTS_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_SUBJECTS + " (" +
				ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				COURSE_KEY_COLUMN + " INTEGER, " +
				NAME_COLUMN + " TEXT NOT NULL, " +
				"FOREIGN KEY(" + COURSE_KEY_COLUMN + ") " +
						"REFERENCES " + CoursesTable.TABLE_COURSES + "(" + CoursesTable.ID_COLUMN + ")" +
			")";
	
	public static final String DROP_SUBJECTS_TABLE =
			"DROP TABLE IF EXISTS " + TABLE_SUBJECTS;
	
}
