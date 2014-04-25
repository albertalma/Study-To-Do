package almartapps.studytodo.view.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.DAO.TaskDAO.SortBy;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

public class TasksFragment extends ListFragment {
	
	private final String TAG = "TasksFragment";
	
	private Context context;
	private List<Task> tasks;
	private Map<Long,Subject> subjects;
	
	private SortBy sortBy = SortBy.date_desc;
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Task task = tasks.get(position);
		startShowTaskFragment(task);
	}

	private void startShowTaskFragment(Task task) {
		Fragment fragment = new TaskFragment();
		Bundle args = new Bundle();
		args.putLong("taskID", task.getId());
		fragment.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().addToBackStack("tasksFragment")
				.replace(R.id.content_frame, fragment).commit();
		/*
		 * fragmentManager.beginTransaction() .commit();
		 */
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_list, container, false);
    }
    
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		inflater.inflate(R.menu.action_bar_tasks, menu);
		MenuItem sortItem = menu.findItem(R.id.action_sort_by);
		sortItem.setIcon(
				new IconDrawable(getActivity(), IconValue.fa_sort_amount_desc)
				.colorRes(R.color.white)
				.actionBarSize());
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
	}
    
    @Override
    public void onResume() {
    	super.onResume();
    	ActionBarActivity activity = (ActionBarActivity) getActivity();
    	ActionBar actionBar = activity.getSupportActionBar();
    	actionBar.setTitle(R.string.tasks);
    	new GetAllTasksTask().execute();
    }
    
    private class GetAllTasksTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			SubjectDAO subjectDao = new SubjectDAOsqlite(context);
			List<Subject> subjectsList = subjectDao.getAll();
			subjects = new HashMap<Long, Subject>();
			for (Subject s : subjectsList) {
				subjects.put(s.getId(), s);
			}
			TaskDAO taskDao = new TaskDAOsqlite(context);
			try {
				tasks = taskDao.complexTasksQuery(TaskDAO.FLAG_SORT_BY,	-1, null, null, true, sortBy);
			} catch (Exception e) {
				exception = e.getMessage();
			}
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
			if (!t.isCompleted())
				toDoTasks.add(t);
		}
		tasks = toDoTasks;
		TaskAdapter taskAdapter = new TaskAdapter(context, tasks, subjects);
		setListAdapter(taskAdapter);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_new:
				startCreateTaskActiyity();
				return true;
			case R.id.action_sort_by_date_asc:
				sortBy = SortBy.date_asc;
				onSortChanged();
				return true;
			case R.id.action_sort_by_date_desc:
				sortBy = SortBy.date_desc;
				onSortChanged();
				return true;
			case R.id.action_sort_by_priority_asc:
				sortBy = SortBy.priority_asc;
				onSortChanged();
				return true;
			case R.id.action_sort_by_priority_desc:
				sortBy = SortBy.priority_desc;
				onSortChanged();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

    private void startCreateTaskActiyity() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), CreateTaskActivity.class);
		startActivity(intent);
	}
    
    private void onSortChanged() {
    	new GetAllTasksTask().execute();
    }

}
