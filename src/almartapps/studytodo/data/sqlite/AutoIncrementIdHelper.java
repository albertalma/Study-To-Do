package almartapps.studytodo.data.sqlite;

import almartapps.studytodo.data.sqlite.utils.DBHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Only visible in this package, in order to hide it from other
 * abstraction layers.
 * 
 * Its use is intended to help DAO implementations to manage the
 * autoincremented IDs of their corresponding class mappings.
 */
class AutoIncrementIdHelper {
	
	private DBHelper dbHelper;
	
	public AutoIncrementIdHelper(Context context) {
		dbHelper = DBHelper.getInstance(context);
	}
	
	public long getNextAvailableId(String tableName) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//TODO acaba això, capullín!! XDD
		long id = 0;
		
		db.close();
		return id;
	}
	
}
