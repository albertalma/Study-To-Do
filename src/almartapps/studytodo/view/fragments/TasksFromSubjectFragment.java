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
		Bundle args = new Bundle();
		args.putLong("subjectID", getArguments().getLong("subjectID"));
		mTabHost.addTab(mTabHost.newTabSpec(tab).setIndicator(tab),
				ToDoTasksFragment.class, args);
		tab = getResources().getString(R.string.done);
		mTabHost.addTab(mTabHost.newTabSpec(tab).setIndicator(tab),
				DoneTasksFragment.class, args);
		tab = getResources().getString(R.string.teachers);
		mTabHost.addTab(mTabHost.newTabSpec(tab).setIndicator(tab),
				ProfessorsFromSubjectFragment.class, args);
		return mTabHost;
	}
}
