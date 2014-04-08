package almartapps.studytodo.view.adapters;

import almartapps.studytodo.R;
import almartapps.studytodo.view.fragments.DoneTasksFragment;
import almartapps.studytodo.view.fragments.ProfessorsFromSubjectFragment;
import almartapps.studytodo.view.fragments.ToDoTasksFragment;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
 * sections of the app.
 */
public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
	
	SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
	
	static final int NUM_ITEMS = 3;
	
    public AppSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new ToDoTasksFragment();
            case 1:
                return new DoneTasksFragment();
            case 2:
                return new ProfessorsFromSubjectFragment();
        }
        //Ojo con esto habria que a�adir un default!!!
		return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    public CharSequence getPageTitle(int position, Resources res) {
    	 switch (position) {
	         case 0:
	             return res.getString(R.string.subject_tab_to_do);
	         case 1:
	        	 return res.getString(R.string.subject_tab_done);
	         case 2: 
	        	 return res.getString(R.string.subject_tab_teachers);
    	 }
		return null;
    }
	
	@Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}