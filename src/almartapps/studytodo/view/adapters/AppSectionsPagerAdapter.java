package almartapps.studytodo.view.adapters;

import almartapps.studytodo.R;
import almartapps.studytodo.view.fragments.CourseFragment;
import almartapps.studytodo.view.fragments.TaskFragment;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
 * sections of the app.
 */
public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

    public AppSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return new TaskFragment();
            case 1:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return new CourseFragment();
        }
        //Ojo con esto habria que a�adir un default!!!
		return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position, Resources res) {
    	 switch (position) {
	         case 0:
	             return res.getString(R.string.tasks);
	         case 1:
	        	 return res.getString(R.string.courses);
    	 }
		return null;
    }
}