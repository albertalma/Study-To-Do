package almartapps.studytodo.view.fragments;

import java.util.ArrayList;
import java.util.List;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.ProfessorDAO;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.exceptions.ObjectNotExistsException;
import almartapps.studytodo.data.sqlite.ProfessorDAOsqlite;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Professor;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.view.activities.CreateProfessorActivity;
import almartapps.studytodo.view.activities.CreateTaskActivity;
import almartapps.studytodo.view.adapters.ProfessorAdapter;
import almartapps.studytodo.view.adapters.TaskAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ProfessorsFromSubjectFragment extends ListFragment {

	private Context context;
	private List<Professor> professors;
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
	 			   new IconDrawable(getActivity(), IconValue.fa_user)
	 			   .colorRes(R.color.white)
	 			   .actionBarSize());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		new GetAllTasksFromSubjectTask().execute(getArguments().getLong(
				"subjectID"));
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
			ProfessorDAO professorDao = new ProfessorDAOsqlite(context);
			//TO DO only for a subject
			//professors = professorDao.getFromSubject((Long) params[0]);
			professors = professorDao.getAll();
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
		ProfessorAdapter professorAdapter = new ProfessorAdapter(context, professors);
		setListAdapter(professorAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
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
		startActivity(intent);
	}

}
