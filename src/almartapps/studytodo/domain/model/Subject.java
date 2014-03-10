package almartapps.studytodo.domain.model;


public class Subject {

	private long id;
	
	private String name;
	
	private long courseId;
	
	public Subject() {
		this.name = "";
	}
	
	/**
	 * Initializes a Subject. Name must not be a <code>null</code> value.
	 * 
	 * @param name the name of the Subject
	 * @param courseId the id of the {@link Course} that this Subject belongs to
	 * @throws IllegalArgumentException if the <code>name</code> is <code>null</code>
	 */
	public Subject(String name, long courseId) {
		if (name != null) {
			this.name = name;
		} else throw new IllegalArgumentException("name must not be null");
		this.courseId = courseId;
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

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	
}