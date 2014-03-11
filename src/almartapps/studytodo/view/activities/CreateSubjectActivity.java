package almartapps.studytodo.view.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.CourseDAO;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.sqlite.CourseDAOsqlite;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.domain.model.Course;
import almartapps.studytodo.domain.model.Subject;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

public class CreateSubjectActivity extends ActionBarActivity {

	private Context context;
	private ActionBarActivity createSubjectActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		createSubjectActivity = this;
		setContentView(R.layout.subject_create);
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
			createSubject();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void createSubject() {
		new CreateSubjectTask().execute();
	}

	private class CreateSubjectTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			EditText courseName = (EditText) findViewById(R.id.subject_name_edittext);
			Intent intent = getIntent();
			Long courseID = intent.getLongExtra("courseID",0);
			String name = courseName.getText().toString();
			Subject subject = new Subject(name, courseID);
			SubjectDAO subjectDao = new SubjectDAOsqlite(context);
			subjectDao.insert(subject);
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {

			} else {
				createSubjectActivity.finish();
			}
		}
	}

}