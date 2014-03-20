package almartapps.studytodo.data.sqlite.tables;


public class ProfessorsTable {

	/**
	 * Professors table name
	 */
	public static final String TABLE_PROFESSORS = "professors";
	
	/**
	 * Professors table ID column (INTEGER, PK, AUTOINCREMENT)
	 */
	public static final String ID_COLUMN = "_id";
	
	/**
	 * Professors table NAME column (TEXT, NOT NULL)
	 */
	public static final String NAME_COLUMN = "name";
	
	/**
	 * Professors table EMAIL column (TEXT)
	 */
	public static final String EMAIL_COLUMN = "email";
	
	/**
	 * Professors table OFFICE_COLUMN column (TEXT)
	 */
	public static final String OFFICE_ADDRESS_COLUMN  = "office";
	
	/**
	 * Professors table TELEPHONE column (TEXT)
	 */
	public static final String TELEPHONE_COLUMN = "telephone";
	
	public static final String CREATE_PROFESSORS_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_PROFESSORS + " (" +
				ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				NAME_COLUMN + " TEXT NOT NULL, " + 
				EMAIL_COLUMN + " TEXT, " +
				OFFICE_ADDRESS_COLUMN + " TEXT, " + 
				TELEPHONE_COLUMN + " TEXT" +
			")";
	
	public static final String DROP_PROFESSORS_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE_PROFESSORS;
}
