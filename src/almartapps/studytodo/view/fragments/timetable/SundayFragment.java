package almartapps.studytodo.view.fragments.timetable;

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
import almartapps.studytodo.view.utils.OnPostExecuteCallback;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class SundayFragment extends ListFragment {

	private static final String TAG = "view.fragments.TodayTasksFragment";
	
	private Context context;
	
	private List<Task> tasks;
	private Map<Long,Subject> subjectsMap;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.show_list, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		new FetchTodayTasksTask(new OnPostExecuteCallback() {
			@Override
			public void onPostExecute() {
				ListAdapter adapter = new TaskAdapter(context, tasks, subjectsMap);
				setListAdapter(adapter);
			}
		}).execute();
	}
		
	private class FetchTodayTasksTask extends AsyncTask<Void, Void, Boolean> {

		private String exceptionMessage;
		private OnPostExecuteCallback callback;
		
		public FetchTodayTasksTask(OnPostExecuteCallback callback) {
			this.callback = callback;
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			//get DAOs
			SubjectDAO subjectDao = new SubjectDAOsqlite(context);
			TaskDAO taskDao = new TaskDAOsqlite(context);
			
			Calendar calendar = Calendar.getInstance();
			List<Subject> subjectsList = null;
			//fetch data
			try {
				subjectsList = subjectDao.getAll();
				tasks = taskDao.getTasks(calendar.getTime());
			} catch (Exception e) {
				exceptionMessage = e.getMessage();
				return true;
			}
			
			//build subjects map
			subjectsMap = new HashMap<Long, Subject>();
			for (Subject subject : subjectsList) {
				subjectsMap.put(subject.getId(), subject);
			}
			
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {
				Log.e(TAG, exceptionMessage);
			}
			else {
				callback.onPostExecute();
			}
		}
	}
	    
}
