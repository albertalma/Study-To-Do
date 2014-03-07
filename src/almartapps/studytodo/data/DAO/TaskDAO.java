package almartapps.studytodo.data.DAO;

import almartapps.studytodo.domain.model.Task;

public interface TaskDAO extends GenericDAO<Task> {
	
	public void updateTask(Task task);
	
}
