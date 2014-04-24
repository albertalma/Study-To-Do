package almartapps.studytodo.view.activities;

import java.util.Arrays;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.view.adapters.NavigationDrawerAdapter;
import almartapps.studytodo.view.fragments.CourseFragment;
import almartapps.studytodo.view.fragments.TasksFragment;
import almartapps.studytodo.view.fragments.TimetableFragment;
import almartapps.studytodo.view.fragments.TodayFragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainNavDrawerActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private List<String> navigationDrawerItems;
	private ListView mDrawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nav_drawer_main);
		
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle(mTitle);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(mDrawerTitle);
			}
		};

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setBackgroundResource(R.color.white);

		// Set the adapter for the list view
		navigationDrawerItems = getNavigationDrawerItems();
		NavigationDrawerAdapter navAdapter = new NavigationDrawerAdapter(this,
				navigationDrawerItems);
		mDrawerList.setAdapter(navAdapter);
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		putFragment(new TodayFragment(), 0);
	}

	private void putFragment(Fragment fragment, int position) {
		Bundle args = new Bundle();
		args.putInt("position", position);
		fragment.setArguments(args);

		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private List<String> getNavigationDrawerItems() {
		List<String> result = Arrays.asList(getResources().getStringArray(R.array.nav_drawer_titles)); 
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private static final int TODAY = 0;
	private static final int TASKS = 1;
	private static final int COURSES = 2;
	private static final int TIMETABLE = 3;

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		Log.d("NAV DRAWER", "position: " + position);
		Fragment fragment = null;
		switch (position) {
		case TODAY:
			fragment = new TodayFragment();
			break;
		case TASKS:
			fragment = new TasksFragment();
			break;
		case COURSES:
			fragment = new CourseFragment();
			break;
		case TIMETABLE:
			fragment = new TimetableFragment();
			break;
		}
		if (fragment != null) {
			putFragment(fragment, position);

			// Highlight the selected item, update the title, and close the
			// drawer
			if (mDrawerList != null && mDrawerLayout != null) {
				mDrawerList.setItemChecked(position, true);
				mDrawerLayout.closeDrawer(mDrawerList);

			}
			setTitle(navigationDrawerItems.get(position));
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

}
