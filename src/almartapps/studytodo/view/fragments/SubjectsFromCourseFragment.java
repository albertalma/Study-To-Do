package almartapps.studytodo.view.fragments;

import java.util.List;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
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

public class SubjectsFromCourseFragment extends ListFragment {
	
	private final String TAG = "SubjectsFromCourseFragment";
	
	private Context context;
	private List<Subject> subjects;
	
	private Long courseID;
	private String courseName;
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Subject subject = subjects.get(position);
		startShowSubjectFragment(subject);
	}
	
	private void startShowSubjectFragment(Subject subject) {
		Fragment fragment = new SubjectFragment();
		Bundle args = new Bundle();
		args.putLong("subjectID", subject.getId());
		args.putString("subjectName", subject.getName());
		fragment.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
        	.addToBackStack("subjectFragment")
        	.replace(R.id.content_frame, fragment)
        	.commit();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		getActivity().setTitle(context.getString(R.string.subjects));
		courseID = getArguments().getLong("courseID");
		courseName = getArguments().getString("courseName");
		new GetAllSubjectsFromCourseTask().execute(courseID);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		ActionBarActivity activity = (ActionBarActivity) getActivity();
    	ActionBar actionBar = activity.getSupportActionBar();
    	actionBar.setTitle(courseName);
		new GetAllSubjectsFromCourseTask().execute(courseID);
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
	 			   new IconDrawable(getActivity(), IconValue.fa_plus)
	 			   .colorRes(R.color.white)
	 			   .actionBarSize());
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
				Log.e(TAG, exception);
			} else {
				setView();
			}
		}
	}
	
	public void setView() {
		SubjectAdapter subjectAdapter = new SubjectAdapter(context, subjects);
		setListAdapter(subjectAdapter);
	}

	
	//Calls to Action Bar
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


	private void startCreateSubjectActiyity() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), CreateSubjectActivity.class);
		intent.putExtra("courseID",courseID);
		startActivity(intent);
	}
}
