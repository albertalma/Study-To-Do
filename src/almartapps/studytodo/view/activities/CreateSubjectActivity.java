package almartapps.studytodo.view.activities;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.domain.model.Subject;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.larswerkman.holocolorpicker.ColorPicker;

public class CreateSubjectActivity extends ActionBarActivity {

	private Context context;
	private ActionBarActivity createSubjectActivity;
	private int color;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		createSubjectActivity = this;
		color = getResources().getColor(R.color.dark_blue);
		setContentView(R.layout.subject_create);
		setColorPickerListener();
		ActionBar actionBar = getSupportActionBar();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ColorPickerActivity.PICK_COLOR_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			color = data.getIntExtra(
					ColorPickerActivity.PICKED_COLOR_EXTRA, Color.BLACK);
			View colorView = (View) findViewById(R.id.subject_color_view);
			colorView.setBackgroundColor(color);
		}
	}

	private void setColorPickerListener() {
		View colorView = (View) findViewById(R.id.subject_color_view);
		colorView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startColorPickerActivity();

			}
		});

	}

	protected void startColorPickerActivity() {
		Intent intent = new Intent(this, ColorPickerActivity.class);
		startActivityForResult(intent, ColorPickerActivity.PICK_COLOR_REQUEST_CODE);
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
			Long courseID = intent.getLongExtra("courseID", 0);
			String name = courseName.getText().toString();
			Subject subject = new Subject(name, color, courseID);

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