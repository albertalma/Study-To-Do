package almartapps.studytodo.data.DAO;

import java.util.List;

import almartapps.studytodo.domain.model.Professor;

public interface ProfessorDAO extends GenericDAO<Professor> {

	public List<Professor> getProfessorsFromSubject(long subjectId);
	
	public boolean assignSubject(long professorId, long subjectId);
	
	public boolean removeSubjectAssignment(long professorId, long subjectId);
	
}
