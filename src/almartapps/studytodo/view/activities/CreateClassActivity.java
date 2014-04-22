package almartapps.studytodo.view.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.domain.model.TaskPriority;
import almartapps.studytodo.view.fragments.dialogs.HourPickerDialog;
import almartapps.studytodo.view.fragments.dialogs.TaskDatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
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
import android.widget.TimePicker;

public class CreateClassActivity extends ActionBarActivity implements OnTimeSetListener {

	private final String TAG = "view.activities.CreateTaskActivity";

	private CreateClassActivity createTaskActivity;

	private List<Subject> subjects;
	private Context context;

	private Date selectedDate;
	
	private EditText hourEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createTaskActivity = this;
		selectedDate = null;
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setTitle(R.string.new_class);

		new GetAllSubjectsTask().execute();
	}

	private void setView() {
		setContentView(R.layout.class_create);
		setSubjectSpinner();
		setDaySpinner();
		setDateFromEditTextListener();
		setDateToEditTextListener();
	}

	private void setDaySpinner() {
		String[] arrayPriority = getResources().getStringArray(
				R.array.timetable_view_pager_titles);
		Spinner spinner = (Spinner) findViewById(R.id.day_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayPriority);
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
			if (subjID != null && subjID == subjects.get(i).getId())
				position = i;
			arraySubjects[i] = subjects.get(i).getName();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arraySubjects);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		s.setSelection(position);
	}

	private void setDateFromEditTextListener() {
		final EditText dateEditText = (EditText) findViewById(R.id.from_date_editText);
		dateEditText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hourEditText = dateEditText;
				showDatePickerDialog();
			}
		});
	}
	
	private void setDateToEditTextListener() {
		final EditText dateEditText = (EditText) findViewById(R.id.to_date_editText);
		dateEditText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hourEditText = dateEditText;
				showDatePickerDialog();
			}
		});
	}

	private void showDatePickerDialog() {
		DialogFragment hourDialog = new HourPickerDialog();
		hourDialog.show(getSupportFragmentManager(), TAG);
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
		menu.findItem(R.id.action_save).setIcon(
				new IconDrawable(this, IconValue.fa_check).colorRes(
						R.color.white).actionBarSize());
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
			switch (spinPriority.getSelectedItemPosition()) {
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
			if (percentageString.equals(""))
				percentageString = "0";
			int percentage = Integer.valueOf(percentageString);
			if (percentage != 0)
				isEvaluable = true;
			int grade = 0;
			// Task(String title, long subjectId, String description, Date
			// dueDate, TaskPriority priority, boolean isCompleted, boolean
			// isEvaluable, int percentage, float grade)
			// FIXME Create a Task with the 'place' field correctly set!! It is
			// now "Barad Dûr" ... Sauron's home, sweet home.
			Task task = new Task(title, subjectId, description, dueDate,
					"Barad Dûr", taskPriority, isCompleted, isEvaluable,
					percentage, grade);
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

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		selectedDate = calendar.getTime();

		// set edittext content
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm",
				Locale.getDefault());
		hourEditText.setText(formatter.format(selectedDate));
	}
}