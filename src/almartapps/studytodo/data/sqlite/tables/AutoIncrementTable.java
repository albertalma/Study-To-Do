package almartapps.studytodo.data.sqlite.tables;

public class AutoIncrementTable {

	/**
	 * Autoincrement table name 
	 */
	public static final String TABLE_AUTOINCREMENT = "autoincrement";
	
	/**
	 * Autoincrement table Task's ID column
	 */
	public static final String TASK_ID_COLUMN = "task";
	
	/**
	 * Create table statement (if not exists)
	 */
	public static final String CREATE_AUTOINCREMENT_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_AUTOINCREMENT + " (" +
				TASK_ID_COLUMN + " INTEGER NOT NULL" +
			")";
	
	/**
	 * Drop table statement
	 */
	public static final String DROP_AUTOINCREMENT_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_AUTOINCREMENT;
}
