package almartapps.studytodo.view.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.domain.model.TaskPriority;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateTaskActivity extends ActionBarActivity {
	
	private CreateTaskActivity createTaskActivity;

	private List<Subject> subjects;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createTaskActivity = this;
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		new GetAllSubjectsTask().execute();

	}

	private void setView() {
		setContentView(R.layout.task_create);
		setSpinner();
		setSubjectSpinner();
	}

	private void setSpinner() {
		String[] array_priority = new String[3];
		array_priority[0] = getString(R.string.high);
		array_priority[1] = getString(R.string.medium);
		array_priority[2] = getString(R.string.low);
		Spinner s = (Spinner) findViewById(R.id.priority_spinner);
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, array_priority);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
	}

	private void setSubjectSpinner() {
		Spinner s = (Spinner) findViewById(R.id.subject_spinner);
		String[] array_subjects = new String[subjects.size()];
		for (int i = 0; i < subjects.size(); ++i) {
			array_subjects[i] = subjects.get(i).getName();
		}
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, array_subjects);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
	}

	private class GetAllSubjectsTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... params) {
			SubjectDAO subjectDAO = new SubjectDAOsqlite(context);
			subjects = subjectDAO.getAll();
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {
			} else {
				setView();
			}
		}
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
			createTask();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void createTask() {
		new CreateTaskTask().execute();
	}
	
	private class CreateTaskTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			EditText taskTitle = (EditText) findViewById(R.id.title_editText);
			String title = taskTitle.getText().toString();
			Spinner spinSubject = (Spinner) findViewById(R.id.subject_spinner);
			int subjectPosition = spinSubject.getSelectedItemPosition();
			long subjectId = subjects.get(subjectPosition).getId();
			EditText descriptionEditText = (EditText) findViewById(R.id.description_editText);
			String description = descriptionEditText.getText().toString();
			EditText dueDateEditText = (EditText) findViewById(R.id.date_editText);
			Spinner spinPriority = (Spinner) findViewById(R.id.priority_spinner);
			EditText percentageEditText = (EditText) findViewById(R.id.percentage_editText);
			Date dueDate = null;
			try {
				dueDate = new SimpleDateFormat("dd-MM-yyyy")
						.parse(dueDateEditText.getText().toString());
			} catch (ParseException e) {
				exception = e.getMessage();
				return true;
			}
			TaskPriority taskPriority = null;
			switch(spinPriority.getSelectedItemPosition()){
			case 0:
				taskPriority = taskPriority.low;
				break;
			case 1:
				taskPriority = taskPriority.medium;
				break;
			case 2:
				taskPriority = taskPriority.high;
				break;
			}
			boolean isCompleted = false;
			boolean isEvaluable = false;
			String percentageString = percentageEditText.getText().toString();
			if (percentageString.equals("")) percentageString="0";
			int percentage = Integer.valueOf(percentageString);
			if (percentage!= 0) isEvaluable = true;
			int grade = 0;
			//Task(String title, long subjectId, String description, Date dueDate, TaskPriority priority, boolean isCompleted, boolean isEvaluable, int percentage, float grade)
			Task task = new Task(title, subjectId, description, dueDate, taskPriority, isCompleted, isEvaluable, percentage, grade);
			TaskDAO taskDao = new TaskDAOsqlite(context);
			taskDao.insert(task);
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {

			} else {
				createTaskActivity.finish();
			}
		}
	}

}