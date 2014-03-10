package almartapps.studytodo.domain.model;

import java.util.Date;

/**
 * Base class for academic tasks
 * 
 * @author necavit
 *
 */

public class Task {
	
	private long id;
	
	private String name;
	
	private String description;
	
	private Date dueDate;
	
	private boolean isCompleted;
	
	private TaskPriority priority;
	
	private long subjectId;
	
	private boolean isEvaluable;
	
	/**
	 * It is intended to be in the range [0-100], not allowing decimals.
	 */
	private int percentage;
	
	private float grade;
	
	public Task() {
		id = -1;
		name = null;
		description = null;
		dueDate = null;
		isCompleted = false;
		priority = TaskPriority.low;
		isEvaluable = false;
		percentage = 0;
		grade = 0.0f;
	}

	public long getId() {
		return id;
	}
	
	/**
	 * WARNING! The ID field of this class is just to help mapping to the database. It must never
	 * be set anywhere in the application logic, as it is an autoincrement value, filled by
	 * the database itself.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
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

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
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
