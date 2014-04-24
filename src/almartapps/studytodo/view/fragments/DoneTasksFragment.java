package almartapps.studytodo.view.fragments;

import java.util.ArrayList;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.exceptions.ObjectNotExistsException;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.view.activities.CreateTaskActivity;
import almartapps.studytodo.view.adapters.TaskAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

public class DoneTasksFragment extends ListFragment {

	private final String TAG = "DoneTasksFragment";
	
	private Context context;
	private List<Task> tasks;
	private Subject subject;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.show_list, container, false);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		inflater.inflate(R.menu.action_bar_new, menu);
		menu.findItem(R.id.action_new).setIcon(
	 			   new IconDrawable(getActivity(), IconValue.fa_plus)
	 			   .colorRes(R.color.white)
	 			   .actionBarSize());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		new GetAllTasksFromSubjectTask().execute(getArguments().getLong("subjectID"));
	}

	private class GetAllTasksFromSubjectTask extends AsyncTask<Long, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Long... params) {
			SubjectDAO subjectDao = new SubjectDAOsqlite(context);
			try {
				subject = subjectDao.get(params[0]);
			} catch (ObjectNotExistsException e) {
				exception = e.getMessage();
				return true;
			}
			TaskDAO taskDao = new TaskDAOsqlite(context);
			tasks = taskDao.getTasksFromSubject(params[0]);
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {
				Log.e(TAG, exception);
			} else {
				setView();
			}
		}
	}

	public void setView() {
		List<Task> toDoTasks = new ArrayList<Task>();
		for (Task t : tasks) {
			if (t.isCompleted())
				toDoTasks.add(t);
		}
		TaskAdapter taskAdapter = new TaskAdapter(context, toDoTasks, subject);
		setListAdapter(taskAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new:
			startCreateTaskActiyity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void startCreateTaskActiyity() {
		Intent intent = new Intent();
		intent.putExtra("subjectID", getArguments().getLong("subjectID"));
		intent.setClass(getActivity(), CreateTaskActivity.class);
		startActivity(intent);
	}

}
