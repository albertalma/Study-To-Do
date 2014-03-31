package almartapps.studytodo.view.fragments;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.view.adapters.TaskAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TodayFragment extends ListFragment {
	
	private Context context;
	private Map<Long,Subject> subjects;
	private List<Task> tasks;
	
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_list, container, false);
    }
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		new GetAllTodayTasksTask().execute();
	}
    
    private class GetAllTodayTasksTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			SubjectDAO subjectDao = new SubjectDAOsqlite(context);
			List<Subject> subjectsList = subjectDao.getAll();
			subjects = new HashMap<Long, Subject>();
			for (Subject s : subjectsList) {
				subjects.put(s.getId(), s);
			}
			Calendar c = Calendar.getInstance(); 
			TaskDAO taskDao = new TaskDAOsqlite(context);
			tasks = taskDao.getTasks(c.getTime());
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {

			} else {
				setView();
			}
		}
	}
    
    public void setView() {
		TaskAdapter taskAdapter = new TaskAdapter(context, tasks, subjects);
		setListAdapter(taskAdapter);
	}
    
}
