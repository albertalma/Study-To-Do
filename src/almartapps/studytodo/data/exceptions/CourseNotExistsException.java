package almartapps.studytodo.data.exceptions;

public class CourseNotExistsException extends ObjectNotExistsException {

	private static final long serialVersionUID = -5155755857821795121L;
	
	public CourseNotExistsException() {
		super();
	}

	public CourseNotExistsException(String message) {
		super(message);
	}
	
}
