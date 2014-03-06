package almartapps.studytodo.data.DAO;

import almartapps.studytodo.data.exceptions.SubjectNotExistsException;
import almartapps.studytodo.domain.model.Subject;

public interface SubjectDAO extends GenericDAO<Subject> {

	public Subject getSubject(long subjectId) throws SubjectNotExistsException;
	
}
