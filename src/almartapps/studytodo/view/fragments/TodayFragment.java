package almartapps.studytodo.view.fragments;

import java.util.Arrays;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.view.activities.CreateTaskActivity;
import almartapps.studytodo.view.adapters.TodayPagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class TodayFragment extends Fragment {
	
	private static final String TAG = "view.fragments.TodayFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.today_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.today_view_pager);
		List<String> pageTitles = Arrays.asList(getResources().getStringArray(R.array.today_view_pager_titles));
		viewPager.setAdapter(new TodayPagerAdapter(getChildFragmentManager(), pageTitles));
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		inflater.inflate(R.menu.action_bar_new, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
