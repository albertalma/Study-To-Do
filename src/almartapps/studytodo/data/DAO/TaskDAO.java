package almartapps.studytodo.data.DAO;

import java.util.Date;
import java.util.List;

import almartapps.studytodo.domain.model.Task;

public interface TaskDAO extends GenericDAO<Task> {
	
	public int FLAG_SELECT_FROM_SUBJECT = 0x0001;
	public int FLAG_SELECT_DATE_RANGE   = 0x0010;
	public int FLAG_SELECT_COMPLETED    = 0x0100;
	public int FLAG_SORT_BY             = 0x1000;
	
	public enum SortBy {
		date_asc, date_desc, priority_asc, priority_desc
	}
	
	public List<Task> getTasks(Date date);
	
	public List<Task> getTasks(Date lowerDate, Date upperDate);
	
	public List<Task> getTasksFromSubject(long subjectId);
	
	public List<Task> complexTasksQuery(int flags, long subjectId, Date lowerDate, Date upperDate, boolean isCompleted, SortBy sortBy);
	
	public void updateTask(Task task);
	
}
