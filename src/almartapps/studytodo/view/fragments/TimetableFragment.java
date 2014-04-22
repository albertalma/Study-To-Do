package almartapps.studytodo.view.fragments;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

import almartapps.studytodo.R;
import almartapps.studytodo.view.activities.CreateClassActivity;
import almartapps.studytodo.view.activities.CreateTaskActivity;
import almartapps.studytodo.view.adapters.TimetablePagerAdapter;
import almartapps.studytodo.view.adapters.TodayPagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class TimetableFragment extends Fragment {
	private static final String TAG = "view.fragments.TodayFragment";

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		
		inflater.inflate(R.menu.action_bar_new, menu);
		menu.findItem(R.id.action_new).setIcon(
				new IconDrawable(getActivity(), IconValue.fa_plus).colorRes(
						R.color.white).actionBarSize());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_new:
				startCreateClassActiyity();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

    private void startCreateClassActiyity() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), CreateClassActivity.class);
		startActivity(intent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.today_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewPager viewPager = (ViewPager) view
				.findViewById(R.id.today_view_pager);
		List<String> pageTitles = Arrays.asList(getResources().getStringArray(
				R.array.timetable_view_pager_titles));
		viewPager.setAdapter(new TimetablePagerAdapter(
				getChildFragmentManager(), pageTitles));
		viewPager.setBackgroundResource(R.color.dark_background_blue);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onStart() {
		super.onStart();
		setDayOfTheWeek();
	}

	private void setDayOfTheWeek() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		ViewPager viewPager = (ViewPager) getView().findViewById(
				R.id.today_view_pager);
		switch (day) {
		case Calendar.SUNDAY:
			viewPager.setCurrentItem(6);
			break;
		case Calendar.MONDAY:
			viewPager.setCurrentItem(0);
			break;
		case Calendar.TUESDAY:
			viewPager.setCurrentItem(1);
			break;
		case Calendar.WEDNESDAY:
			viewPager.setCurrentItem(2);
			break;
		case Calendar.THURSDAY:
			viewPager.setCurrentItem(3);
			break;
		case Calendar.FRIDAY:
			viewPager.setCurrentItem(4);
			break;
		case Calendar.SATURDAY:
			viewPager.setCurrentItem(5);
			break;
		}
	}
}
