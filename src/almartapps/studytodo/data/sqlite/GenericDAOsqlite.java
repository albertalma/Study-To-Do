package almartapps.studytodo.data.sqlite;

import almartapps.studytodo.data.DAO.GenericDAO;
import android.content.Context;

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
	
}
