package almartapps.studytodo.view.activities;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

import almartapps.studytodo.R;
import almartapps.studytodo.data.sqlite.ProfessorDAOsqlite;
import almartapps.studytodo.domain.model.Professor;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class CreateProfessorActivity extends ActionBarActivity {

	private final String TAG = "CreateProfessorActivity";
	
	public static final String EXTRA_SUBJECT_ID = "extra_subject_id";
	public static final String EXTRA_SUBJECT_NAME = "extra_subject_name";
	
	private Context context;
	private ActionBarActivity createProfessorActivity;
	
	private long subjectId;
	private String subjectName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		createProfessorActivity = this;
		setContentView(R.layout.professor_create);
		
		subjectId = getIntent().getLongExtra(EXTRA_SUBJECT_ID, 0);
		subjectName = getIntent().getStringExtra(EXTRA_SUBJECT_NAME);
		
		TextView subjectTextView = (TextView) findViewById(R.id.professor_subject_textview);
		subjectTextView.setText(subjectName);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setTitle(R.string.new_teacher);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar_save, menu);
		menu.findItem(R.id.action_save).setIcon(
				new IconDrawable(this, IconValue.fa_check)
	 			   .colorRes(R.color.white)
	 			   .actionBarSize());
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_save:
			createProfessor();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void createProfessor() {
		EditText profName = (EditText) findViewById(R.id.professor_name_edittext);
		EditText profMail = (EditText) findViewById(R.id.professor_email_edittext);
		EditText profOffice = (EditText) findViewById(R.id.professor_office_edittext);
		EditText profPhone = (EditText) findViewById(R.id.professor_phone_edittext);
		
		String name = profName.getText().toString();
		String email= profMail.getText().toString();
		String office= profOffice.getText().toString();
		String phone= profPhone.getText().toString();
		
		Professor professor = new Professor(name, email, office, phone);
		
		new CreateProfessorTask(professor, subjectId).execute();
	}

	private class CreateProfessorTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;
		private Professor professor;
		private long subjectId;
		
		public CreateProfessorTask(Professor professor, long subjectId) {
			this.professor = professor;
			this.subjectId = subjectId;
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			ProfessorDAOsqlite professorDao = new ProfessorDAOsqlite(context);
			professor = professorDao.insert(professor);
			professorDao.assignSubject(professor.getId(), subjectId);
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {
				Log.e(TAG, exception);
			} else {
				createProfessorActivity.finish();
			}
		}
	}

}