package almartapps.studytodo.view.fragments;

import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Course;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.view.activities.CreateCourseActivity;
import almartapps.studytodo.view.activities.CreateTaskActivity;
import almartapps.studytodo.view.adapters.CourseAdapter;
import almartapps.studytodo.view.adapters.TaskAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class TaskFragment extends ListFragment {
	
	private Context context;
	private List<Task> tasks;
	
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_list, container, false);
    }
    
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		inflater.inflate(R.menu.action_bar_new, menu);
	}
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		new GetAllTasksTask().execute();
	}
    
    private class GetAllTasksTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			TaskDAO taskDao = new TaskDAOsqlite(context);
			tasks = taskDao.getAll();
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
		TaskAdapter taskAdapter = new TaskAdapter(context, tasks);
		setListAdapter(taskAdapter);
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
		startActivity(intent);
	}

}
