package almartapps.studytodo.data.sqlite;

import java.util.List;

import almartapps.studytodo.data.sqlite.tables.TasksTable;
import almartapps.studytodo.data.sqlite.utils.MappingUtils;
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
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		//TODO perform query
		
		//map rows to tasks
		//TODO map rows to tasks
		
		//release connection
		db.close();
		return null;
	}

	@Override
	public void insert(Task task) {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//build a map with column names as keys and values
		ContentValues values = new ContentValues();
		values.put(TasksTable.NAME_COLUMN, task.getName()); //not null
		
		if (task.getDescription() != null) {
			values.put(TasksTable.DESCRIPTION_COLUMN, task.getDescription());
		} else {
			values.putNull(TasksTable.DESCRIPTION_COLUMN);
		}
		if (task.getDueDate() != null) {
			values.put(TasksTable.DUE_DATE_COLUMN, MappingUtils.formatDateToSQL(task.getDueDate()));
		} else {
			values.putNull(TasksTable.DUE_DATE_COLUMN);
		}
		values.put(TasksTable.PRIORITY_COLUMN, MappingUtils.mapPriorityToSQL(task.getPriority())); //not null
		values.put(TasksTable.COMPLETED_COLUMN, task.isCompleted()); //not null
		values.put(TasksTable.EVALUABLE_COLUMN, task.isEvaluable()); //not null
		if (task.isEvaluable()) {
			values.put(TasksTable.PERCENTAGE_COLUMN, task.getPercentage());
			if (task.isCompleted()) {
				values.put(TasksTable.GRADE_COLUMN, task.getGrade());
			}
			else {
				values.putNull(TasksTable.GRADE_COLUMN);
			}
		}
		else { //not evaluable -> no percentage and no grade
			values.putNull(TasksTable.PERCENTAGE_COLUMN);
			values.putNull(TasksTable.GRADE_COLUMN);
		}
		
		//perform insert
		db.insert(TasksTable.TABLE_TASKS, null, values);
		
		//release connection
		db.close();
	}

	@Override
	public void delete(Task object) {
		// TODO Auto-generated method stub
		
	}

}
