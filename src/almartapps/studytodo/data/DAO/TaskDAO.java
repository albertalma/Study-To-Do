package almartapps.studytodo.data.DAO;

import java.util.List;

import almartapps.studytodo.domain.model.Task;

public interface TaskDAO extends GenericDAO<Task> {
	
	public List<Task> getTasksFromSubject(long subjectId);
	
	public void updateTask(Task task);
	
}
