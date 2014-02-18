package almartapps.studytodo.data.sqlite.tables;

public final class TasksTable {
	
	/**
	 * Tasks table name
	 */
	public static final String TABLE_TASKS = "tasks";
	
	/**
	 * Tasks table ID column (INTEGER, PK, AUTOINCREMENT)
	 */
	public static final String ID_COLUMN = "_id";
	
	/**
	 * Tasks table NAME column (TEXT, NOT NULL)
	 */
	public static final String NAME_COLUMN = "name";

	/**
	 * Tasks table DESCRIPTION column (TEXT)
	 */
	public static final String DESCRIPTION_COLUMN = "description";
	
	/**
	 * Tasks table DUE DATE column (DATETIME [String formatted: "yyyy-MM-dd HH:mm:ss"])
	 */
	public static final String DUE_DATE_COLUMN = "due_date";
	
	/**
	 * Tasks table PRIORITY column (INTEGER, NOT NULL)
	 */
	public static final String PRIORITY_COLUMN = "priority";
	
	/**
	 * Tasks table COMPLETED column (BOOLEAN, NOT NULL)
	 */
	public static final String COMPLETED_COLUMN = "completed";
	
	/**
	 * Tasks table EVALUABLE column (BOOLEA, NOT NULL)
	 */
	public static final String EVALUABLE_COLUMN = "evaluable";
	
	/**
	 * Tasks table PERCENTAGE column (INTEGER)
	 */
	public static final String PERCENTAGE_COLUMN = "percentage";
	
	/**
	 * Tasks table GRADE column (REAL)
	 */
	public static final String GRADE_COLUMN = "grade";
	
	/**
	 * Tasks table ALL columns. Utility structure to help reverse mapping from Cursors to Objects.
	 */
	public static final String [] ALL_COLUMNS = {ID_COLUMN, NAME_COLUMN, 
				DESCRIPTION_COLUMN, DUE_DATE_COLUMN, PRIORITY_COLUMN, COMPLETED_COLUMN, 
				EVALUABLE_COLUMN, PERCENTAGE_COLUMN, GRADE_COLUMN};
	
	public static final String CREATE_TASKS_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " (" +
				ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				NAME_COLUMN + " TEXT NOT NULL, " +
				DESCRIPTION_COLUMN + " TEXT, " +
				DUE_DATE_COLUMN + " DATETIME, " +
				PRIORITY_COLUMN + " INTEGER NOT NULL, " +
				COMPLETED_COLUMN + " BOOLEAN NOT NULL, " +
				EVALUABLE_COLUMN + " BOOLEAN NOT NULL, " +
				PERCENTAGE_COLUMN + " INTEGER, " +
				GRADE_COLUMN + " REAL" +
			")";
	
	public static final String DROP_TASKS_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_TASKS;
	
}
