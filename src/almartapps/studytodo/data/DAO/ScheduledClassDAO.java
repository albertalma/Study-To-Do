package almartapps.studytodo.data.DAO;

import java.util.List;

import almartapps.studytodo.domain.model.ScheduledClass;

public interface ScheduledClassDAO extends GenericDAO<ScheduledClass> {

	public List<ScheduledClass> getScheduledClassesFromSubject(long subjectId);
	
}
