package almartapps.studytodo.data.sqlite;

import java.text.ParseException;
import java.util.List;

import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.exceptions.TaskNotExistsException;
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
	public Task get(long taskId) throws TaskNotExistsException {
		//get connection
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//perform query
		String queryStatement = 
				"SELECT * FROM " + TasksTable.TABLE_TASKS + 
				" WHERE " + TasksTable.ID_COLUMN + " = ?";
		String [] selectionArgs = new String[]{String.valueOf(taskId)}; 
		Cursor cursor = db.rawQuery(queryStatement, selectionArgs);
		
		//check result
		if (cursor.getCount() != 1) 
			throw new TaskNotExistsException("No Task exists with ID=" + taskId);
		
		//map result
		cursor.moveToFirst();
		Task task = map(cursor);
		
		//release connection
		db.close();
		
		return task;
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
		cursor.moveToFirst();
		List<Task> tasks = mapAll(cursor);
		
		//release connection
		db.close();
		
		return tasks;
	}

	@Override
	public Task insert(Task task) throws IllegalArgumentException {
		if (task.getName() == null)
			throw new IllegalArgumentException("Argument 'name' was null, but a Task must have a name.");
		if (task.getPriority() == null) 
			throw new IllegalArgumentException("Argument 'priority' was null, but a Task must have a priority.");
		
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//get mapping for values
		ContentValues values = getContentValues(task);
		
		//perform insert
		Log.i(TAG, "inserting task with name=" + task.getName());
		long id = db.insert(TasksTable.TABLE_TASKS, null, values);
		
		//set the ID returned to the task
		task.setId(id);
		
		//release connection
		db.close();
		
		//return
		return task;
	}
	
	@Override
	public void updateTask(Task task) {
		//get connection
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//get mapping for values
		ContentValues values = getContentValues(task);
		
		//update query
		String whereClause = "_id = ?";
		String [] whereArgs = new String[]{String.valueOf(task.getId())};
		Log.i(TAG, "updating task with _id=" + task.getId());
		db.update(TasksTable.TABLE_TASKS, values, whereClause, whereArgs);
		
		//release connection
		db.close();
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
		Log.i(TAG, "deleting ALL tasks (this is not a 'DROP TABLE' statement)");
		int count = db.delete(TasksTable.TABLE_TASKS, whereClause, null);
		
		//release connection
		db.close();
		
		//return
		return count;
	}
	
	@Override
	protected ContentValues getContentValues(Task task) {
		//build a map with column names as keys and values
		ContentValues values = new ContentValues();
		
		values.put(TasksTable.SUBJECT_KEY_COLUMN, task.getSubjectId());
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
		
		return values;
	}
	
	@Override
	protected Task map(Cursor cursor) {
		Task task = new Task();
		//id, autoincrement
		task.setId(cursor.getLong(0));
		//subject_id
		task.setSubjectId(cursor.getLong(1));
		//name, not null
		task.setName(cursor.getString(2));
		//description
		if (!cursor.isNull(3)) task.setDescription(cursor.getString(2));
		else task.setDescription(null);
		//due date
		if (!cursor.isNull(4)) {
			try {
				task.setDueDate(MappingUtils.parseSQLDate(cursor.getString(4)));
			} catch (ParseException e) {
				Log.e(TAG, e.getMessage());
			}
		}
		else task.setDueDate(null);
		//priority, not null
		try {
			task.setPriority(MappingUtils.parseSQLPriority(cursor.getInt(5)));
		} catch (SQLiteParseException e) {
			Log.e(TAG, e.getMessage());
		}
		//completed, not null
		task.setCompleted(cursor.getInt(6) > 0);
		//evaluable, not null
		task.setEvaluable(cursor.getInt(7) > 0);
		if (task.isEvaluable()) {
			//percentage
			if (!cursor.isNull(8)) task.setPercentage(cursor.getInt(8));
			//grade
			if (!cursor.isNull(9)) task.setGrade(cursor.getFloat(9));
		}
		return task;
	}

}
