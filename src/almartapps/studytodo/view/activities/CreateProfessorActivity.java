package almartapps.studytodo.view.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.CourseDAO;
import almartapps.studytodo.data.sqlite.CourseDAOsqlite;
import almartapps.studytodo.data.sqlite.ProfessorDAOsqlite;
import almartapps.studytodo.domain.model.Course;
import almartapps.studytodo.domain.model.Professor;
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

public class CreateProfessorActivity extends ActionBarActivity {

	private Context context;
	private ActionBarActivity createProfessorActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		createProfessorActivity = this;
		setContentView(R.layout.professor_create);

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
		new CreateProfessorTask().execute();
	}

	private class CreateProfessorTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			EditText profName = (EditText) findViewById(R.id.professor_name_edittext);
			EditText profMail = (EditText) findViewById(R.id.professor_email_edittext);
			EditText profOffice = (EditText) findViewById(R.id.professor_office_edittext);
			EditText profPhone = (EditText) findViewById(R.id.professor_phone_edittext);
			
			String name = profName.getText().toString();
			String email= profMail.getText().toString();
			String office= profOffice.getText().toString();
			String phone= profPhone.getText().toString();
			Professor professor = new Professor(name, email, office, phone);
			ProfessorDAOsqlite professorDao = new ProfessorDAOsqlite(context);
			professorDao.insert(professor);
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {

			} else {
				createProfessorActivity.finish();
			}
		}
	}

}