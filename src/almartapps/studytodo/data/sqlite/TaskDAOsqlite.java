package almartapps.studytodo.data.sqlite;

import java.util.List;

import almartapps.studytodo.data.sqlite.tables.TasksTable;
import almartapps.studytodo.model.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TaskDAOsqlite extends GenericDAOsqlite<Task> {
	
	protected TaskDAOsqlite(Context context) {
		super(context);
	}

	@Override
	public List<Task> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Task task) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//build a map with column names as keys and values
		ContentValues values = new ContentValues();
		values.put(TasksTable.NAME_COLUMN, task.getName());
		values.put(TasksTable.DESCRIPTION_COLUMN, task.getDescription());
		values.put(TasksTable.DUE_DATE_COLUMN, MappingUtils.formatDateToSQL(task.getDueDate()));
		values.put(TasksTable.PRIORITY_COLUMN, MappingUtils.mapPriorityToSQL(task.getPriority()));
		values.put(TasksTable.COMPLETED_COLUMN, task.isCompleted());
		//TODO handle subclasses!!
		db.insert(TasksTable.TABLE_TASKS, null, values);
		db.close();
	}

	@Override
	public void delete(Task object) {
		// TODO Auto-generated method stub
		
	}

}
