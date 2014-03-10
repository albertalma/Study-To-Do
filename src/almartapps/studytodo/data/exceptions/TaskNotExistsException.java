package almartapps.studytodo.data.exceptions;

public class TaskNotExistsException extends ObjectNotExistsException {

	private static final long serialVersionUID = 8141947248632853704L;

	public TaskNotExistsException() {
		super();
	}
	
	public TaskNotExistsException(String message) {
		super(message);
	}
	
}
