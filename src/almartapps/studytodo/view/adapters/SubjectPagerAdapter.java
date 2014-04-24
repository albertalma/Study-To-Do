package almartapps.studytodo.view.adapters;

import java.util.List;

import almartapps.studytodo.view.fragments.DoneTasksFragment;
import almartapps.studytodo.view.fragments.ProfessorsFromSubjectFragment;
import almartapps.studytodo.view.fragments.ToDoTasksFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
 * sections of the app.
 */
public class SubjectPagerAdapter extends FragmentPagerAdapter {
		
	static final int NUM_ITEMS = 3;
	private List<String> pageTitles;
	
    public SubjectPagerAdapter(FragmentManager fm, List<String> pageTitles) {
        super(fm);
        this.pageTitles = pageTitles;
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
            default:
            	return new ToDoTasksFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
    	return pageTitles.get(position); 
    }
    
    
	
}