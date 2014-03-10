package almartapps.studytodo.view.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.CourseDAO;
import almartapps.studytodo.data.sqlite.CourseDAOsqlite;
import almartapps.studytodo.domain.model.Course;
import almartapps.studytodo.view.activities.CreateCourseActivity;
import almartapps.studytodo.view.adapters.CourseAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

public class CourseFragment extends ListFragment {
	
	private Context context;
	private List<Course> courses;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		new GetAllCoursesTask().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.courses_show, container, false);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		inflater.inflate(R.menu.action_bar_new, menu);
	}

	private class GetAllCoursesTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			CourseDAO courseDao = new CourseDAOsqlite(context);
			// TODO
			courses = courseDao.getAll();
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {

			} else {
				setView();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_new:
			startCreateCourseActiyity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void setView() {
		CourseAdapter courseAdapter = new CourseAdapter(context, courses);
		setListAdapter(courseAdapter);
	}

	private void startCreateCourseActiyity() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), CreateCourseActivity.class);
		startActivity(intent);
	}
}
