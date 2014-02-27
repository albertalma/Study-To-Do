package almartapps.studytodo.data.sqlite;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.tables.TasksTable;
import almartapps.studytodo.data.sqlite.utils.MappingUtils;
import almartapps.studytodo.data.sqlite.utils.SQLiteParseException;
import almartapps.studytodo.domain.model.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TaskDAOsqlite extends GenericDAOsqlite<Task> implements TaskDAO {
	
	private static final String TAG = "data.sqlite.TaskDAOsqlite";
	
	public TaskDAOsqlite(Context context) {
		super(context);
	}

	@Override
	public List<Task> getAll() {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = "SELECT * FROM " + TasksTable.TABLE_TASKS;
		Log.i(TAG, "getting all tasks stored. SQL statement is: " + queryStatement);
		Cursor cursor = db.rawQuery(queryStatement, new String[0]);
		
		//map rows to tasks
		List<Task> tasks = mapTasks(cursor);
		
		//release connection
		db.close();
		
		return tasks;
	}

	@Override
	public Task insert(Task task) {
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
		Log.i(TAG, "inserting task with name=" + task.getName() 
				+ " and description=" + (task.getDescription() != null ? task.getDescription() : "")
				+ " and priority=" + task.getPriority()
				+ ". No SQL statement");
		long id = db.insert(TasksTable.TABLE_TASKS, null, values);
		
		//set the ID returned to the task
		task.setId(id);
		
		//release connection
		db.close();
		
		//return
		return task;
	}

	@Override
	public boolean delete(Task task) {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//delete object
		String whereStatement = "_id = ?";
		String [] whereArgs = new String[]{String.valueOf(task.getId())};
		Log.i(TAG, "deleting task with _id=" + task.getId() 
				+ ". SQL WHERE clause is: " + whereStatement + ", " + task.getId());
		int count = db.delete(TasksTable.TABLE_TASKS, whereStatement, whereArgs);
		
		//release connection
		db.close();
		
		//return
		return count > 0;
	}
	
	/**
	 * WARNING! This method will delete ALL the tasks in the table! Be careful
	 * when using it!
	 * @return the number of Tasks deleted (i.e. the number of rows deleted). 
	 */
	@Override
	public int deleteAll() {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//delete all objects
		String whereClause = "1"; //this is the value we must pass to the delete() method to
						//delete all rows and still get the count for it
		int count = db.delete(TasksTable.TABLE_TASKS, whereClause, null);
		
		//release connection
		db.close();
		
		//return
		return count;
	}
	
	private List<Task> mapTasks(final Cursor cursor) {
		List<Task> tasks = new ArrayList<Task>();
		cursor.moveToFirst();
		while (cursor.getCount() > 0 && !cursor.isAfterLast()) {
			tasks.add(mapTask(cursor));
			cursor.moveToNext();
		}
		return tasks;
	}
	
	private Task mapTask(final Cursor cursor) {
		Task task = new Task();
		//id, autoincrement
		task.setId(cursor.getInt(0));
		//name, not null
		task.setName(cursor.getString(1));
		//description
		if (!cursor.isNull(2)) task.setDescription(cursor.getString(2));
		else task.setDescription(null);
		//due date
		if (!cursor.isNull(3)) {
			try {
				task.setDueDate(MappingUtils.parseSQLDate(cursor.getString(3)));
			} catch (ParseException e) {
				Log.e(TAG, e.getMessage());
			}
		}
		else task.setDueDate(null);
		//priority, not null
		try {
			task.setPriority(MappingUtils.parseSQLPriority(cursor.getInt(4)));
		} catch (SQLiteParseException e) {
			Log.e(TAG, e.getMessage());
		}
		//completed, not null
		task.setCompleted(cursor.getInt(5) > 0);
		//evaluable, not null
		task.setEvaluable(cursor.getInt(6) > 0);
		if (task.isEvaluable()) {
			//percentage
			if (!cursor.isNull(7)) task.setPercentage(cursor.getInt(7));
			//grade
			if (!cursor.isNull(8)) task.setGrade(cursor.getFloat(8));
		}
		return task;
	}

}
