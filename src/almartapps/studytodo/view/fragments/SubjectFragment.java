package almartapps.studytodo.view.fragments;

import java.util.Arrays;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.view.adapters.AppSectionsPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SubjectFragment extends Fragment {

	private long subjectId;
	private String subjectName;
	
	public long getSubjectId() {
		return subjectId;
	}
	
	public String getSubjectName() {
		return subjectName;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		subjectId = getArguments().getLong("subjectID");
		subjectName = getArguments().getString("subjectName");
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		List<String> pageTitles = Arrays.asList(getResources().getStringArray(R.array.subject_view_pager_titles));
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.subject_view_pager);
		viewPager.setAdapter(new AppSectionsPagerAdapter(getChildFragmentManager(), pageTitles));
		viewPager.setBackgroundResource(R.color.dark_background_blue);
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
		return inflater.inflate(R.layout.subject_fragment, container, false);
	}
	
	/*
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
	}*/
}
