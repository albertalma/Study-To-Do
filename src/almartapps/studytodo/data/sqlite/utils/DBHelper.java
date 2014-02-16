package almartapps.studytodo.data.sqlite.utils;

import almartapps.studytodo.data.sqlite.tables.TasksTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "data.sqlite.DBHelper";
	
	/**
	 * Database identifier: should not be changed... unless you want it.
	 */
	private static final String DATABASE_NAME = "StudyToDoSQLiteDB";
	
	/**
	 * WARNING!! README!!
	 * 
	 * Database version number: it is used to keep track of the schema changes
	 * on the database. You cannot change the schema of the DB if this version
	 * number is not changed too.
	 * 
	 * As of date 13/Feb/2014, version number is "0". Please change this comment
	 * when needed!
	 */
	private static final int DATABASE_VERSION = 0;
	
	private static DBHelper instance;
	
	private DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DBHelper getInstance(Context context) {
		if (instance == null) instance = new DBHelper(context);
		return instance;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		initializeDatabase(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                 + newVersion + ", which will destroy all old data");
         dropDatabase(db);
         onCreate(db);
	}
	
	/**
	 * Initializes the schema of the database, creating the tables if needed.
	 * 
	 * @param db the database instance to be initialized
	 */
	private void initializeDatabase(SQLiteDatabase db) {
		db.execSQL(TasksTable.CREATE_TASKS_TABLE);
		//TODO execute tables' CREATE statements
		// WARNING! remember to do it in the right order!
	}
	
	/**
	 * Drops the whole database schema. Used when upgrading the database
	 * to a new version.
	 * 
	 * @param db the database instance to be cleared
	 */
	private void dropDatabase(SQLiteDatabase db) {
		db.execSQL(TasksTable.DROP_TASKS_TABLE);
		//TODO execute tables' DROP statements
	}

}
