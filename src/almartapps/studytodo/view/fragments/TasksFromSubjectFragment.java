package almartapps.studytodo.view.fragments;

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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class TasksFromSubjectFragment extends Fragment {

	private Context context;
	private List<Task> tasks;
	private Subject subject;

	private FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.contenido);
		String tab = getResources().getString(R.string.to_do);
		mTabHost.addTab(mTabHost.newTabSpec(tab).setIndicator(tab),
				ToDoTasksFragment.class, null);
		tab = getResources().getString(R.string.done);
		mTabHost.addTab(mTabHost.newTabSpec(tab).setIndicator(tab),
				DoneTasksFragment.class, null);
		tab = getResources().getString(R.string.teachers);
		mTabHost.addTab(mTabHost.newTabSpec(tab).setIndicator(tab),
				TeachersFromSubjectFragment.class, null);
		return mTabHost;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		inflater.inflate(R.menu.action_bar_new, menu);
	}

	private void createTabs() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		createTabs();
		// new
		// GetAllTasksFromSubjectTask().execute(getArguments().getLong("subjectID"));
	}

	private class GetAllTasksFromSubjectTask extends
			AsyncTask<Long, Void, Boolean> {

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

			} else {
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
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
		intent.setClass(getActivity(), CreateTaskActivity.class);
		intent.putExtra("subjectID", subject.getId());
		startActivity(intent);
	}

}
