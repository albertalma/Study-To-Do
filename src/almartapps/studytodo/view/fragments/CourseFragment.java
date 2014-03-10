package almartapps.studytodo.view.fragments;

import almartapps.studytodo.R;
import almartapps.studytodo.view.activities.CreateCourseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class CourseFragment extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.courses_show, container, false);
    }
    

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		inflater.inflate(R.menu.action_bar_new, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_new:
			startCreateCourseActiyity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void startCreateCourseActiyity() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), CreateCourseActivity.class);
		startActivity(intent);
	}
}
