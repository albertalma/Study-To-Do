package almartapps.studytodo.data.sqlite.tables;

public class CoursesTable {
	
	/**
	 * Courses table name
	 */
	public static final String TABLE_COURSES = "courses";
	
	/**
	 * Courses table ID column (INTEGER, PK, AUTOINCREMENT)
	 */
	public static final String ID_COLUMN = "_id";
	
	/**
	 * Courses table NAME column (TEXT, NOT NULL)
	 */
	public static final String NAME_COLUMN = "name";
	
	/**
	 * Courses table START_DATE column (DATETIME)
	 */
	public static final String START_DATE_COLUMN = "start_date";
	
	/**
	 * Courses table END_DATE column (DATETIME)
	 */
	public static final String END_DATE_COLUMN = "end_date";
	
	public static final String CREATE_COURSES_TABLE = 
			"CREATE TABLE IF EXISTS " + TABLE_COURSES + " (" +
				ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				NAME_COLUMN + " TEXT NOT NULL, " +
				START_DATE_COLUMN + " DATETIME, " +
				END_DATE_COLUMN + " DATETIME" +
			")";
	
	public static final String DROP_COURSES_TABLE =
			"DROP TABLE IF EXISTS " + TABLE_COURSES;
	
}
