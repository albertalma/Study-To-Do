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
	
	private String place;
	
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
		place = null;
	}
	
	/**
	 * Initializes a Task. Name and task priority must not be null: an exception will
	 * be thrown otherwise. All those attributes marked as optional are allowed to be null.
	 * 
	 * @param name the name of the Task
	 * @param subjectId the {@link Subject} id of the Subject which the task belongs to
	 * @param description (optional) a short description of the Task
	 * @param dueDate (optional) the date for which the task is due
	 * @param priority the task priority. Must be of type {@link TaskPriority}
	 * @param isCompleted whether or not the task is completed
	 * @param isEvaluable whether or not the task is evaluable
	 * @param percentage the percentage that this task contributes to the overall subject grade. It is intended to be in the range [0,100]
	 * @param grade the grade assigned to this task. It is intended to be in the range [0.0,10.0]
	 * @throws IllegalArgumentException if either <code>name</code> or <code>priority</code> are <code>null</code>
	 */
	public Task(String name, long subjectId, String description, Date dueDate, String place, TaskPriority priority, 
			boolean isCompleted, boolean isEvaluable, int percentage, float grade) throws IllegalArgumentException {
		if (name != null) {
			this.name = name;
		} else throw new IllegalArgumentException("name must not be null");
		this.subjectId = subjectId;
		this.description = description;
		this.dueDate = dueDate;
		this.place = place;
		if (priority != null) {
			this.priority = priority;
		} else throw new IllegalArgumentException("priority must not be null");
		this.isCompleted = isCompleted;
		this.isEvaluable = isEvaluable;
		this.percentage = percentage;
		this.grade = grade;
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
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
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
