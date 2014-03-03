package almartapps.studytodo.activities;

import almartapps.studytodo.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateTaskActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_task);
		setSpinner();
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void setSpinner() {
		 String [] array_priority=new String[3];
	        array_priority[0]=getString(R.string.high);
	        array_priority[1]=getString(R.string.medium);
	        array_priority[2]=getString(R.string.low);
		Spinner s = (Spinner) findViewById(R.id.priority_spinner);
        ArrayAdapter adapter = new ArrayAdapter(this,
        android.R.layout.simple_spinner_item, array_priority);
        s.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar_save_tasks, menu);
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