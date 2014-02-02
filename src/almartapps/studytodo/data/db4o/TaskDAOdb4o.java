package almartapps.studytodo.data.db4o;

import java.util.List;

import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.model.Task;

public class TaskDAOdb4o extends GenericDAOdb4o<Task> implements TaskDAO {

	@Override
	public List<Task> getAll() {
		// TODO Auto-generated method stub
		boolean fooVariable = fooDatabaseConnection.isEmpty();
		if (fooVariable) fooVariable = false;
		return null;
	}

	@Override
	public void store(Task task) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Task task) {
		// TODO Auto-generated method stub
	}

}
