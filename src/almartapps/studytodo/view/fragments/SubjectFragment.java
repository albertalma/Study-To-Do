package almartapps.studytodo.view.fragments;

import almartapps.studytodo.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SubjectFragment extends Fragment {

	private FragmentTabHost mTabHost;
	private long subjectId;
	private String subjectName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		subjectId = getArguments().getLong("subjectID");
		subjectName = getArguments().getString("subjectName");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		ActionBarActivity activity = (ActionBarActivity) getActivity();
    	ActionBar actionBar = activity.getSupportActionBar();
    	actionBar.setTitle(subjectName);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.contenido);
		
		Bundle args = new Bundle();
		args.putLong("subjectID", subjectId);
		args.putString("subjectName", subjectName);
		
		String tabIndicator = getResources().getString(R.string.subject_tab_to_do);
		mTabHost.addTab(mTabHost.newTabSpec(tabIndicator).setIndicator(tabIndicator), ToDoTasksFragment.class, args);
		
		tabIndicator = getResources().getString(R.string.subject_tab_done);
		mTabHost.addTab(mTabHost.newTabSpec(tabIndicator).setIndicator(tabIndicator), DoneTasksFragment.class, args);
		
		tabIndicator = getResources().getString(R.string.subject_tab_teachers);
		mTabHost.addTab(mTabHost.newTabSpec(tabIndicator).setIndicator(tabIndicator), ProfessorsFromSubjectFragment.class, args);
		
		return mTabHost;
	}
}
