package almartapps.studytodo.data.DAO;

import almartapps.studytodo.data.exceptions.TaskNotExistsException;
import almartapps.studytodo.domain.model.Task;

public interface TaskDAO extends GenericDAO<Task> {

	public Task getTask(long taskId) throws TaskNotExistsException;
	
	public void updateTask(Task task);
	
}
