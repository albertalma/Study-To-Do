package almartapps.studytodo.view.activities;

import almartapps.studytodo.R;
import almartapps.studytodo.view.fragments.CourseFragment;
import almartapps.studytodo.view.fragments.TaskFragment;
import almartapps.studytodo.view.fragments.TodayFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainNavDrawerActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private String[] auxArray;
	private ListView mDrawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_nav_drawer);

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

		auxArray = new String[] { "Avui", "Tasques", "Assignatures" };
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.activity_list_item, android.R.id.text1,
				auxArray));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar_show_tasks, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			switch (item.getItemId()) {
			case R.id.action_new:
				startCreateTaskActiviy();
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
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

	private void startCreateTaskActiviy() {
		Intent createTaskIntent = new Intent(this, CreateTaskActivity.class);
		startActivity(createTaskIntent);
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

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		Log.d("NAV DRAWER", "position: " + position);
		Fragment fragment = null;
		switch (position) {
		case TODAY:
			fragment = new TodayFragment();
			break;
		case TASKS:
			fragment = new TaskFragment();
			break;
		case COURSES:
			fragment = new CourseFragment();
			break;
		}
		if (fragment != null) {
			Bundle args = new Bundle();
			args.putInt("position", position);
			fragment.setArguments(args);

			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			// Highlight the selected item, update the title, and close the
			// drawer
			mDrawerList.setItemChecked(position, true);
			setTitle(auxArray[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

}
