package almartapps.studytodo.domain.model;

import java.util.List;

public class Subject {

	private long id;
	
	private String name;
	
	private List<Professor> professors;
	
	private long courseId;
	
	public Subject() {
		this.name = "";
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Professor> getProfessors() {
		return professors;
	}

	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}
	
	public void addProfessor(Professor professor) {
		professors.add(professor);
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	
}