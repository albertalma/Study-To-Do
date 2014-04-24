package almartapps.studytodo.view.fragments;

import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.ProfessorDAO;
import almartapps.studytodo.data.sqlite.ProfessorDAOsqlite;
import almartapps.studytodo.domain.model.Professor;
import almartapps.studytodo.view.activities.CreateProfessorActivity;
import almartapps.studytodo.view.adapters.ProfessorAdapter;
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

public class ProfessorsFromSubjectFragment extends ListFragment {

	private final String TAG = "ProfessorsFromSubjectFragment";
	
	private Context context;
	private List<Professor> professors;
	private long subjectId;
	private String subjectName;

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
	 			   new IconDrawable(getActivity(), IconValue.fa_user)
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
		subjectId = ((SubjectFragment)getParentFragment()).getSubjectId();
		subjectName = ((SubjectFragment)getParentFragment()).getSubjectName();
		new GetAllProfessorsFromSubjectTask(subjectId).execute();
	}

	private class GetAllProfessorsFromSubjectTask extends AsyncTask<Long, Void, Boolean> {

		private String exception;
		private long subjectId;
		
		public GetAllProfessorsFromSubjectTask(long subjectId) {
			this.subjectId = subjectId;
		}
		
		@Override
		protected Boolean doInBackground(Long... params) {
			ProfessorDAO professorDao = new ProfessorDAOsqlite(context);
			professors = professorDao.getProfessorsFromSubject(subjectId);
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
		ProfessorAdapter professorAdapter = new ProfessorAdapter(context, professors);
		setListAdapter(professorAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new:
			startCreateProfessorActiyity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void startCreateProfessorActiyity() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), CreateProfessorActivity.class);
		intent.putExtra(CreateProfessorActivity.EXTRA_SUBJECT_ID, subjectId);
		intent.putExtra(CreateProfessorActivity.EXTRA_SUBJECT_NAME, subjectName);
		startActivity(intent);
	}

}
