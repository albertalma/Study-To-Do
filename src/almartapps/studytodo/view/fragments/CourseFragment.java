package almartapps.studytodo.view.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

public class CourseFragment extends ListFragment {

	private Context context;
	private List<Course> courses;

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Course course = courses.get(position);
		startShowSubjectFragment(course);
	}

	private void startShowSubjectFragment(Course course) {
		Fragment fragment = new SubjectFragment();
		Bundle args = new Bundle();
		args.putLong("courseID", course.getId());
		fragment.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().addToBackStack("courseFragment")
				.replace(R.id.content_frame, fragment).commit();
		/*
		 * fragmentManager.beginTransaction() .commit();
		 */
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		new GetAllCoursesTask().execute();
	}

	@Override
	public void onResume() {
		super.onResume();
		new GetAllCoursesTask().execute();
	}

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
				new IconDrawable(getActivity(), IconValue.fa_plus).colorRes(
						R.color.white).actionBarSize());
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
				Log.d("Course Fragment", "size: " + courses.size());
				for (Course c : courses) {
					Log.d("Course Fragment", c.getName());
				}
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
