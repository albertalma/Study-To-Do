package almartapps.studytodo.domain.model;


public class Professor {
	
	public Professor() {}
	
	public Professor(String name, String emailAddress, String officeAddress,
			String telephone) {
		super();
		this.name = name;
		this.emailAddress = emailAddress;
		this.officeAddress = officeAddress;
		this.telephone = telephone;
	}

	private long id;
	
	private String name;
	
	private String emailAddress;
	
	private String officeAddress;
	
	private String telephone;
	
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
	
}
