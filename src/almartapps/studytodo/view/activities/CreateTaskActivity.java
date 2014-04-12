package almartapps.studytodo.view.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.domain.model.TaskPriority;
import almartapps.studytodo.view.fragments.dialogs.TaskDatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateTaskActivity extends ActionBarActivity implements OnDateSetListener {
	
	private final String TAG = "view.activities.CreateTaskActivity";
	
	private CreateTaskActivity createTaskActivity;

	private List<Subject> subjects;
	private Context context;
	
	private Date selectedDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createTaskActivity = this;
		selectedDate = null;
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		new GetAllSubjectsTask().execute();

	}

	private void setView() {
		setContentView(R.layout.task_create);
		setSpinner();
		setSubjectSpinner();
		setDateEditTextListener();
	}

	private void setSpinner() {
		String[] arrayPriority = getResources().getStringArray(R.array.priority_spinner_array);
		Spinner spinner = (Spinner) findViewById(R.id.priority_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayPriority);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	private void setSubjectSpinner() {
		Spinner s = (Spinner) findViewById(R.id.subject_spinner);
		Intent subjectIntent = getIntent();
		Long subjID = null;
		if (subjectIntent != null) {
			subjID = subjectIntent.getLongExtra("subjectID", 0);
		}
		String[] arraySubjects = new String[subjects.size()];
		int position = 0;
		for (int i = 0; i < subjects.size(); ++i) {
			if (subjID != null && subjID == subjects.get(i).getId()) position = i;
			arraySubjects[i] = subjects.get(i).getName();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySubjects);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		s.setSelection(position);
	}
	
	private void setDateEditTextListener() {
		EditText dateEditText = (EditText) findViewById(R.id.date_editText);
		dateEditText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}
		});
	}
	
	private void showDatePickerDialog() {
		DialogFragment dateDialog = new TaskDatePickerDialog();
		dateDialog.show(getSupportFragmentManager(), TAG);
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		//set selected date
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, monthOfYear);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		selectedDate = calendar.getTime();
		
		//set edittext content
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		EditText dateEditText = (EditText) findViewById(R.id.date_editText);
		dateEditText.setText(formatter.format(selectedDate));
	}

	private class GetAllSubjectsTask extends AsyncTask<Void, Void, Boolean> {

		private String exceptionMessage;

		@Override
		protected Boolean doInBackground(Void... params) {
			SubjectDAO subjectDAO = new SubjectDAOsqlite(context);
			try {
				subjects = subjectDAO.getAll();
			} catch (Exception e) {
				exceptionMessage = e.getMessage();
			}
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {
				Log.e(TAG, exceptionMessage);
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

		private String exceptionMessage;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			EditText taskTitle = (EditText) findViewById(R.id.title_editText);
			String title = taskTitle.getText().toString();
			Spinner spinSubject = (Spinner) findViewById(R.id.subject_spinner);
			int subjectPosition = spinSubject.getSelectedItemPosition();
			long subjectId = subjects.get(subjectPosition).getId();
			EditText descriptionEditText = (EditText) findViewById(R.id.description_editText);
			String description = descriptionEditText.getText().toString();
			
			Spinner spinPriority = (Spinner) findViewById(R.id.priority_spinner);
			EditText percentageEditText = (EditText) findViewById(R.id.percentage_editText);
			Date dueDate = selectedDate;
			
			TaskPriority taskPriority = null;
			switch(spinPriority.getSelectedItemPosition()){
			case 0:
				taskPriority = TaskPriority.high;
				break;
			case 1:
				taskPriority = TaskPriority.medium;
				break;
			case 2:
				taskPriority = TaskPriority.low;
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
			//FIXME Create a Task with the 'place' field correctly set!! It is now "Barad Dûr" ... Sauron's home, sweet home.
			Task task = new Task(title, subjectId, description, dueDate, "Barad Dûr", taskPriority, isCompleted, isEvaluable, percentage, grade);
			TaskDAO taskDao = new TaskDAOsqlite(context);
			taskDao.insert(task);
			return false;
		}

		@Override
		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {
				Log.e(TAG, exceptionMessage);
			} else {
				createTaskActivity.finish();
			}
		}
	}

}