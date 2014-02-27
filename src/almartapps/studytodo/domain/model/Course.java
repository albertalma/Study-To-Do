package almartapps.studytodo.domain.model;

import java.util.Date;
import java.util.List;

public class Course {

	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	private List<Subject> subjects;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	protected void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	
	protected void addSubject(Subject subject) {
		subjects.add(subject);
	}

	public float getAverageGrade() {
		float averageGrade = 0.0f;
		int n = 0;
		for (Subject subject : subjects) {
			averageGrade += subject.getGrade();
			++n;
		}
		averageGrade /= (float)n;
		return averageGrade;
	}
	
}
