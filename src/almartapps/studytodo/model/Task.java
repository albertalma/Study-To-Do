package almartapps.studytodo.model;

import java.util.Date;

/**
 * Base class for academic tasks
 * 
 * @author necavit
 *
 */

public class Task {
	
	private String name;
	
	private String description;
	
	private Date dueDate;
	
	private boolean isCompleted;
	
	private TaskPriority priority;
	
	private Subject subject;
	
	private boolean isEvaluable;
	
	/**
	 * It is intended to be in the range [0-100], not allowing decimals.
	 */
	private int percentage;
	
	private float grade;
	
	public Task() {
		name = null;
		description = null;
		dueDate = null;
		isCompleted = false;
		priority = TaskPriority.low;
		isEvaluable = false;
		percentage = 0;
		grade = 0.0f;
	}
	
	public Task(String name) {
		this.name = name;
		description = null;
		dueDate = null;
		isCompleted = false;
		priority = TaskPriority.low;
		isEvaluable = false;
		percentage = 0;
		grade = 0.0f;
	}
	
	public Task(String name, String description) {
		this.name = name;
		this.description = description;
		dueDate = null;
		isCompleted = false;
		priority = TaskPriority.low;
		isEvaluable = false;
		percentage = 0;
		grade = 0.0f;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public Subject getSubject() {
		return subject;
	}

	protected void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public boolean isEvaluable() {
		return isEvaluable;
	}
	
	public void setEvaluable(boolean isEvaluable) {
		this.isEvaluable = isEvaluable;
	}
	
	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}
	
}
