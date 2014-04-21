package almartapps.studytodo.view.fragments;

import java.util.Arrays;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.view.adapters.TimetablePagerAdapter;
import almartapps.studytodo.view.adapters.TodayPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimetableFragment extends Fragment {
	private static final String TAG = "view.fragments.TodayFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.today_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.today_view_pager);
		List<String> pageTitles = Arrays.asList(getResources().getStringArray(R.array.timetable_view_pager_titles));
		viewPager.setAdapter(new TimetablePagerAdapter(getChildFragmentManager(), pageTitles));
		viewPager.setBackgroundResource(R.color.dark_background_blue);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(false);
	}
}
