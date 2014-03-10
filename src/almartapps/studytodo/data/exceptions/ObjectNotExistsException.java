package almartapps.studytodo.data.exceptions;

public class ObjectNotExistsException extends Exception {

	private static final long serialVersionUID = 7195656659308006996L;
	
	public ObjectNotExistsException() {
		super();
	}

	public ObjectNotExistsException(String message) {
		super(message);
	}
	
}
