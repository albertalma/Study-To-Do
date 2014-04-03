package almartapps.studytodo.data.DAO;

import java.util.List;
import java.util.Set;

import almartapps.studytodo.domain.model.ScheduledClass;
import almartapps.studytodo.domain.model.WeekDay;

public interface ScheduledClassDAO extends GenericDAO<ScheduledClass> {
	
	public List<ScheduledClass> getScheduledClassesFromSubject(long subjectId);

	public List<ScheduledClass> getScheduledClasses(WeekDay weekDay, Set<Long> subjectsIds);
	
}
