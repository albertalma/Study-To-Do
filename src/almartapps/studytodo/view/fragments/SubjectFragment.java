package almartapps.studytodo.view.fragments;

import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.domain.model.Course;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.view.activities.CreateSubjectActivity;
import almartapps.studytodo.view.adapters.SubjectAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SubjectFragment extends ListFragment {
	
	private Context context;
	private List<Subject> subjects;
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Subject subject = subjects.get(position);
		//startShowSubjectFragment(subject);
	}
	
	private void startShowSubjectFragment(Course course) {
		Fragment fragment = new SubjectFragment();
		Bundle args = new Bundle();
		args.putLong("courseID", course.getId());
		fragment.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		new GetAllSubjectsFromCourseTask().execute(getArguments().getLong("courseID"));
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
	}

	private class GetAllSubjectsFromCourseTask extends AsyncTask<Long, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Long... params) {
			SubjectDAO subjectDAO = new SubjectDAOsqlite(context);
			subjects = subjectDAO.getSubjectsFromCourse((Long) params[0]);
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
			startCreateSubjectActiyity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void setView() {
		SubjectAdapter subjectAdapter = new SubjectAdapter(context, subjects);
		setListAdapter(subjectAdapter);
	}

	private void startCreateSubjectActiyity() {
		Intent intent = new Intent();
		intent.putExtra("courseID", getArguments().getLong("courseID"));
		intent.setClass(getActivity(), CreateSubjectActivity.class);
		startActivity(intent);
	}
}
