package almartapps.studytodo.view.adapters;

import java.util.List;

import almartapps.studytodo.view.fragments.TodayClassesFragment;
import almartapps.studytodo.view.fragments.TodayTasksFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TodayPagerAdapter extends FragmentPagerAdapter {

	private final int PAGE_COUNT = 2;
	
	private List<String> pageTitles;
	
	public TodayPagerAdapter(FragmentManager fm, List<String> pageTitles) {
		super(fm);
		this.pageTitles = pageTitles;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0: //tasks
			Fragment tasksFragment = new TodayTasksFragment();
			return tasksFragment;
		case 1: //classes
			Fragment classesFragment = new TodayClassesFragment();
			return classesFragment;
		}
		return null;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return pageTitles.get(position);
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

}
