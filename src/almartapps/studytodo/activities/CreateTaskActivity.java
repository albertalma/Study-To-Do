package almartapps.studytodo.activities;

import almartapps.studytodo.R;
import almartapps.studytodo.R.id;
import almartapps.studytodo.R.layout;
import almartapps.studytodo.R.menu;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CreateTaskActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_task);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
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
	    switch (item.getItemId()) {
	        case R.id.action_new:
	        	startCreateTaskActiviy();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void startCreateTaskActiviy() {
		Intent createTaskIntent = new Intent(this, CreateTaskActivity.class);
		startActivity(createTaskIntent);
	}

}