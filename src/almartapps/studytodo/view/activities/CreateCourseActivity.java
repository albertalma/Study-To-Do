package almartapps.studytodo.view.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import almartapps.studytodo.R;
import almartapps.studytodo.data.sqlite.CourseDAOsqlite;
import almartapps.studytodo.domain.model.Course;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class CreateCourseActivity extends ActionBarActivity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.course_create);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar_save, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_save:
			createCourse();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void createCourse() {
		new createCourseTask().execute();
	}

	private class createCourseTask extends AsyncTask<Void, Void, Boolean> {

		protected void onPostExecute(Boolean exceptionRaised) {
			EditText courseName = (EditText) findViewById(R.id.course_name_edittext);
			EditText startDateEditText = (EditText) findViewById(R.id.course_start_date_edittext);
			EditText endDateEditText = (EditText) findViewById(R.id.course_end_date_edittext);
			String name = courseName.getText().toString();
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = new SimpleDateFormat("dd-MM-yyyy")
						.parse(startDateEditText.getText().toString());
				endDate = new SimpleDateFormat("dd-MM-yyyy")
						.parse(endDateEditText.getText().toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Course course = new Course(name, startDate, endDate);
			// TODO
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}

}