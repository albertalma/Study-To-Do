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

	public static final String CREATE_TASKS_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TasksTable.TABLE_TASKS + " (" +
				TasksTable.ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				TasksTable.NAME_COLUMN + " TEXT NOT NULL, " +
				TasksTable.DESCRIPTION_COLUMN + " TEXT, " +
				TasksTable.DUE_DATE_COLUMN + " DATETIME, " +
				TasksTable.PRIORITY_COLUMN + " INTEGER NOT NULL, " +
				TasksTable.COMPLETED_COLUMN + " BOOLEAN NOT NULL, " +
				TasksTable.EVALUABLE_COLUMN + " BOOLEAN NOT NULL, " +
				TasksTable.PERCENTAGE_COLUMN + " INTEGER, " +
				TasksTable.GRADE_COLUMN + " REAL" +
			")";
	
}
