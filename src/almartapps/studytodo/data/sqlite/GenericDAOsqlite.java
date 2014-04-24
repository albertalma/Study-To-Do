package almartapps.studytodo.data.sqlite;

import java.util.ArrayList;
import java.util.List;

import almartapps.studytodo.data.DAO.GenericDAO;
import almartapps.studytodo.data.sqlite.utils.DBHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class GenericDAOsqlite<T> implements GenericDAO<T> {
	
	/**
	 * This instance of DBHelper will be used by concrete DAOs to get
	 * readable or writable database connections to which SQL queries
	 * will be executed.
	 */
	protected DBHelper dbHelper;
	
	/**
	 * All DAOs should call this super constructor, in order to get the
	 * dbHelper correctly set up.
	 * 
	 * @param context context needed to create and initialize the database
	 */
	protected GenericDAOsqlite(Context context) {
		dbHelper = DBHelper.getInstance(context);
	}
	
	/**
	 * Builds a map (a {@link ContentValues} object) with the attributes of an 
	 * object to be passed to DB methods such as <code>insert</code> or <code>update</code>. 
	 * The keys of the map are the column names of the table corresponding to 
	 * this object's class and the values are those of this particular object.
	 *  
	 * @param object the object from which the map is built
	 * @return a <code>ContentValues</code> map with the attributes of the object
	 */
	protected abstract ContentValues getContentValues(T object);
	
	/**
	 * Maps a row pointed by a {@link Cursor} to an object of a certain class.
	 * Please note that this Cursor must be valid (pointing to a row, initialized).
	 * 
	 * @param cursor the row pointing Cursor object 
	 * @return the mapped object
	 */
	protected abstract T map(Cursor cursor);
	
	/**
	 * Maps all the rows that are accessible from a {@link Cursor} to a list of
	 * objects of a certain class.
	 * Please note that this Cursor must be pointing to the first row of the result set.
	 *  
	 * @param cursor the cursor pointing to the result set retrieved by the DB
	 * @return a list of objects correctly mapped
	 */
	protected List<T> mapAll(Cursor cursor) {
		List<T> objects = new ArrayList<T>();
		if (!cursor.isFirst()) cursor.moveToFirst();
		while (cursor.getCount() > 0 && !cursor.isAfterLast()) {
			objects.add(map(cursor));
			cursor.moveToNext();
		}
		return objects;
	}
	
	protected void closeDb(SQLiteDatabase db) {
		if (db != null && db.isOpen()) {
			db.close();
		}
	}
	
}
