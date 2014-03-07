package almartapps.studytodo.data.exceptions;

public class SubjectNotExistsException extends ObjectNotExistsException {

	private static final long serialVersionUID = -6489586849135339338L;

	public SubjectNotExistsException() {
		super();
	}
	
	public SubjectNotExistsException(String message) {
		super(message);
	}
	
}
