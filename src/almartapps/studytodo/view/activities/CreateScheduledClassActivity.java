package almartapps.studytodo.view.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.ScheduledClassDAO;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.ScheduledClassDAOsqlite;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.ClassType;
import almartapps.studytodo.domain.model.ScheduledClass;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.domain.model.TaskPriority;
import almartapps.studytodo.domain.model.Time;
import almartapps.studytodo.domain.model.WeekDay;
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

public class CreateScheduledClassActivity extends ActionBarActivity implements OnTimeSetListener {

	private final String TAG = "view.activities.CreateTaskActivity";

	private CreateScheduledClassActivity createTaskActivity;

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
		setContentView(R.layout.scheduledclass_create);
		setClasstypeSpinner();
		setSubjectSpinner();
		setDaySpinner();
		setDateFromEditTextListener();
		setDateToEditTextListener();
	}
	

	private void setClasstypeSpinner() {
		String[] arrayPriority = getResources().getStringArray(
				R.array.classtype_spinner_array);
		Spinner spinner = (Spinner) findViewById(R.id.classtype_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayPriority);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
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
			createScheduledClass();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void createScheduledClass() {
		new CreateScheduledClassTask().execute();
	}

	private class CreateScheduledClassTask extends AsyncTask<Void, Void, Boolean> {

		private String exceptionMessage;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			//weekDay
			WeekDay weekDay = null;
			Spinner spinDay = (Spinner) findViewById(R.id.day_spinner);
			switch (spinDay.getSelectedItemPosition()) {
			case 0:
				weekDay = WeekDay.monday;
				break;
			case 1:
				weekDay = WeekDay.tuesday;
				break;
			case 2:
				weekDay = WeekDay.wednesday;
				break;
			case 3:
				weekDay = WeekDay.thursday;
				break;
			case 4:
				weekDay = WeekDay.friday;
				break;
			case 5:
				weekDay = WeekDay.saturday;
				break;
			case 6:
				weekDay = WeekDay.sunday;
				break;
			}
			
			//startTime
			EditText startTimeEditText = (EditText) findViewById(R.id.from_date_editText);
			String startTimeString = startTimeEditText.getText().toString();
			String[] time = startTimeString.split ( ":" );
			int hour = Integer.parseInt ( time[0].trim() );
			int min = Integer.parseInt ( time[1].trim() );
			Time startTime = new Time(hour, min);
			
			//endTime
			EditText endTimeEditText = (EditText) findViewById(R.id.to_date_editText);
			String endTimeString = endTimeEditText.getText().toString();
			time = endTimeString.split ( ":" );
			hour = Integer.parseInt ( time[0].trim() );
			min = Integer.parseInt ( time[1].trim() );
			Time endTime = new Time(hour, min);
			
			//place
			
			EditText placeEditText = (EditText) findViewById(R.id.place_editText);
			String place = placeEditText.getText().toString();
			
			//classtype
			ClassType type = null;
			Spinner spinType = (Spinner) findViewById(R.id.classtype_spinner);
			switch (spinType.getSelectedItemPosition()) {
			case 0:
				type = ClassType.theory;
				break;
			case 1:
				type = ClassType.laboratory;
				break;
			case 2:
				type = ClassType.practice;
				break;
			case 3:
				type = ClassType.problems;
				break;
			}
			
			//subjectId
			Spinner spinSubject = (Spinner) findViewById(R.id.subject_spinner);
			int subjectPosition = spinSubject.getSelectedItemPosition();
			long subjectId = subjects.get(subjectPosition).getId();
			
			//ScheduledClass Creation
			ScheduledClass scheduledClass = new ScheduledClass(weekDay, startTime, endTime, place, type, subjectId);
			ScheduledClassDAO scheduledClassDAO = new ScheduledClassDAOsqlite(context);
			scheduledClassDAO.insert(scheduledClass);
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