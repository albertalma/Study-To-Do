package almartapps.studytodo.domain.controllers;

import java.util.Map;

import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Task;
import android.content.Context;

public class TasksController {

	private TaskDAO taskDAO;
	
	private static TasksController instance = null;
	
	private TasksController(Context context) {
		//TODO complete initialization
		taskDAO = new TaskDAOsqlite(context);
	}
	
	public static TasksController getInstance(Context context) {
		if (instance == null) instance = new TasksController(context);
		return instance;
	}
	
	/**
	 * Creates a new <code>Task</code> from the values in the map. Those values must
	 * conform to the fields in the {@link Task} class, being their keys
	 *  
	 * @param values
	 * @return
	 */
	public Task createTask(Map<String,Object> values) {
		//create new empty task
		Task task = new Task();
		
		//fill task with values in the map
		//TODO
		
		//insert task in the database
		task = taskDAO.insert(task);
		
		//return
		return task;
	}
	
}
