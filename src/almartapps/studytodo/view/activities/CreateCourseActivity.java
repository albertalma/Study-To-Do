package almartapps.studytodo.view.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.CourseDAO;
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
import android.widget.DatePicker;
import android.widget.EditText;

public class CreateCourseActivity extends ActionBarActivity {

	private Context context;
	private ActionBarActivity createCourseActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		createCourseActivity = this;
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
		new CreateCourseTask().execute();
	}

	private class CreateCourseTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		private String getStringdateFromDatePicker(DatePicker datepickDatePicker) {
			int day = datepickDatePicker.getDayOfMonth();
			int month = datepickDatePicker.getMonth();
			int year = datepickDatePicker.getYear();
			return day + "-" + month + "-" + year;
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			EditText courseName = (EditText) findViewById(R.id.course_name_edittext);
			DatePicker startDatepickDatePicker = (DatePicker) findViewById(R.id.course_start_date_datePicker);
			DatePicker endDatepickDatePicker = (DatePicker) findViewById(R.id.course_end_date_datePicker);
			String name = courseName.getText().toString();

			String startDateString = getStringdateFromDatePicker(startDatepickDatePicker);
			String endDateString = getStringdateFromDatePicker(endDatepickDatePicker);

			Date startDate = null;
			Date endDate = null;
			try {
				startDate = new SimpleDateFormat("dd-MM-yyyy")
						.parse(startDateString);
				endDate = new SimpleDateFormat("dd-MM-yyyy")
						.parse(endDateString);
			} catch (ParseException e) {
				exception = e.getMessage();
				return true;
			}
			Course course = new Course(name, startDate, endDate);
			// TODO
			CourseDAO courseDao = new CourseDAOsqlite(context);
			courseDao.insert(course);
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {

			} else {
				createCourseActivity.finish();
			}
		}
	}

}