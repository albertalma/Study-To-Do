package almartapps.studytodo.data.sqlite.tables;

public class ScheduledClassesTable {

	/**
	 * ScheduledClasses table name
	 */
	public static final String TABLE_SCHEDULED_CLASS = "scheduled_class";
	
	/**
	 * ScheduledClasses table ID column (INTEGER, PK, AUTOINCREMENT)
	 */
	public static final String ID_COLUMN = "_id";
	
	/**
	 * ScheduledClasses table SUBJECT_KEY column (INTEGER, FK)
	 */
	public static final String SUBJECT_KEY_COLUMN = "subject_id";
	
	/**
	 * ScheduledClasses table WEEK_DAY column (INTEGER, NOT NULL)
	 */
	public static final String WEEK_DAY_COLUMN = "week_day";
	
	/**
	 * ScheduledClasses table START_TIME column (DATETIME [String formatted: "HH:mm"], NOT NULL)
	 */
	public static final String START_TIME_COLUMN = "start_time";
	
	/**
	 * ScheduledClasses table END_TIME column (DATETIME [String formatted: "HH:mm"], NOT NULL)
	 */
	public static final String END_TIME_COLUMN = "end_time";
	
	/**
	 * ScheduledClasses table PLACE column (TEXT)
	 */
	public static final String PLACE_COLUMN = "place";
	
	/**
	 * ScheduledClasses table TYPE column (INTEGER, NOT NULL)
	 */
	public static final String TYPE_COLUMN = "type";
	
	public static final String CREATE_SCHEDULED_CLASSES_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_SCHEDULED_CLASS + " (" +
				ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				SUBJECT_KEY_COLUMN + " INTEGER, " +
				WEEK_DAY_COLUMN + " INTEGER NOT NULL, " +
				START_TIME_COLUMN + " DATETIME NOT NULL, " + 
				END_TIME_COLUMN + " DATETIME NOT NULL, " +
				PLACE_COLUMN + " TEXT, " +
				TYPE_COLUMN + " INTEGER NOT NULL, " +
				"FOREIGN KEY(" + SUBJECT_KEY_COLUMN + ") " +
					"REFERENCES " + SubjectsTable.TABLE_SUBJECTS + "(" + SubjectsTable.ID_COLUMN + ")" +
			")";
	
	public static final String DROP_SCHEDULED_CLASSES_TABLE =
			"DROP TABLE IF EXISTS " + TABLE_SCHEDULED_CLASS;
}
