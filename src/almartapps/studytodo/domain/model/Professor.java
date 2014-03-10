package almartapps.studytodo.domain.model;

import java.util.List;

public class Professor {
	
	private String name;
	
	private String emailAddress;
	
	private String officeAddress;
	
	private String telephone;
	
	private List<Subject> taughtSubjects;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Subject> getTaughtSubjects() {
		return taughtSubjects;
	}

	protected void setTaughtSubjects(List<Subject> teachedSubjects) {
		this.taughtSubjects = teachedSubjects;
	}
	
	protected void addTaughtSubject(Subject subject) {
		taughtSubjects.add(subject);
	}
	
}
