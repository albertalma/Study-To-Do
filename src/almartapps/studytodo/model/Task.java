package almartapps.studytodo.model;

import java.util.Date;

/**
 * Base class for academic tasks
 * 
 * @author necavit
 *
 */

public class Task {
	
	public String name;
	
	public String description;
	
	public Date dueDate;
	
	public boolean isCompleted;
	
	public TaskPriority priority;
	
	public Task() {
		name = null;
		description = null;
		dueDate = null;
		isCompleted = false;
		priority = TaskPriority.low;
	}
	
	public Task(String name) {
		this.name = name;
	}
	
	public Task(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
