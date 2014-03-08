package almartapps.studytodo.data.DAO;

import java.util.List;

import almartapps.studytodo.domain.model.Subject;

public interface SubjectDAO extends GenericDAO<Subject> {
	
	public List<Subject> getSubjectsFromCourse(long courseId);
	
}
