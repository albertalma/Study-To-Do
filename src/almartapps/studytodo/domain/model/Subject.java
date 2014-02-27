package almartapps.studytodo.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Subject {

	private String name;
	
	private List<Task> tasks;
	
	private List<Professor> professors;
	
	private List<ScheduledClass> scheduledClasses;
	
	private Course course;
	
	public Subject() {
		this.name = "";
		this.tasks = new ArrayList<Task>();
	}
	
	public Subject(String name) {
		this.name = name;
		this.tasks = new ArrayList<Task>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(Task task) {
		tasks.add(task);
	}

	public List<Professor> getProfessors() {
		return professors;
	}

	protected void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}
	
	protected void addProfessor(Professor professor) {
		professors.add(professor);
	}

	public List<ScheduledClass> getScheduledClasses() {
		return scheduledClasses;
	}

	protected void setScheduledClasses(List<ScheduledClass> scheduledClasses) {
		this.scheduledClasses = scheduledClasses;
	}
	
	protected void addScheduledClass(ScheduledClass scheduledClass) {
		scheduledClasses.add(scheduledClass);
	}

	public Course getCourse() {
		return course;
	}

	protected void setCourse(Course course) {
		this.course = course;
	}
	
	/**
	 * Calculates the current grade of the subject adding the grades of the 
	 * tasks belonging to this subject, weighted accordingly to their contribution (percentage).
	 * 
	 * @return the current grade of the subject, in the range [0,10.0]
	 */
	public float getGrade() {
		float grade = 0.0f;
		for (Task task : tasks) {
			if (task.isCompleted()) { //do not add non-completed tasks!
				if (task.isEvaluable()) {
					grade += task.getGrade() * task.getPercentage();
				}
			}
		}
		return grade;
	}
	
}