package almartapps.studytodo.data.DAO;

import java.util.Date;
import java.util.List;

import almartapps.studytodo.domain.model.Task;

public interface TaskDAO extends GenericDAO<Task> {
	
	public static int FLAG_SORT_BY = 1;
	
	public enum SortBy {
		date, priority
	}
	
	public List<Task> getTasks(Date date);
	
	public List<Task> getTasks(Date lowerDate, Date upperDate);
	
	public List<Task> getTasksFromSubject(long subjectId);
	
	public void updateTask(Task task);
	
}
