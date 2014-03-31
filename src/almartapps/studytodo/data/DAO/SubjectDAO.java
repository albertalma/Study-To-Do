package almartapps.studytodo.data.DAO;

import java.util.List;

import almartapps.studytodo.domain.model.Subject;

public interface SubjectDAO extends GenericDAO<Subject> {
	
	public List<Subject> getSubjectsFromCourse(long courseId);
	
	public List<Subject> getSubjectsFromProfessor(long professorId);
	
	public boolean assignProfessor(long subjectId, long professorId);
	
	public boolean removeProfessorAssignment(long subjectId, long professorId);
	
}
