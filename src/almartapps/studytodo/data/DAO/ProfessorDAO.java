package almartapps.studytodo.data.DAO;

import almartapps.studytodo.domain.model.Professor;

public interface ProfessorDAO extends GenericDAO<Professor> {

	public boolean assignSubject(long professorId, long subjectId);
	
	public boolean removeSubjectAssignment(long professorId, long subjectId);
	
}
