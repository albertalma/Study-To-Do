package almartapps.studytodo.view.fragments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.exceptions.ObjectNotExistsException;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.view.fragments.dialogs.EnterGradeDialogFragment;
import almartapps.studytodo.view.fragments.dialogs.EnterGradeDialogFragment.EnterGradeDialogListener;
import almartapps.studytodo.view.utils.Utils;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.IconTextView;
import android.widget.TextView;

public class TaskFragment extends Fragment implements EnterGradeDialogListener {

	private final String TAG = "TaskFragment";

	private Context context;
	private Task task;
	private Map<Long, Subject> subjects;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.task_item_expanded, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(false);
		context = getActivity();
	}

	@Override
	public void onResume() {
		super.onResume();
		new GetTaskTask().execute();
	}

	private class GetTaskTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			SubjectDAO subjectDao = new SubjectDAOsqlite(context);
			List<Subject> subjectsList = subjectDao.getAll();
			subjects = new HashMap<Long, Subject>();
			for (Subject s : subjectsList) {
				subjects.put(s.getId(), s);
			}
			TaskDAO taskDao = new TaskDAOsqlite(context);
			try {
				task = taskDao.get(getArguments().getLong("taskID"));
			} catch (ObjectNotExistsException e) {
				exception = e.getMessage();
			}
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {
				Log.e(TAG, exception);
			} else {
				setView();
			}
		}
	}

	public void setView() {
		ActionBarActivity activity = (ActionBarActivity) getActivity();
		ActionBar actionBar = activity.getSupportActionBar();
		Subject subject = subjects.get(task.getSubjectId());
		actionBar.setTitle(subject.getName());

		TextView title = (TextView) getView().findViewById(R.id.title);
		TextView mark = (TextView) getView().findViewById(R.id.nota);
		TextView date = (TextView) getView().findViewById(R.id.date);
		TextView place = (TextView) getView().findViewById(R.id.place);
		TextView description = (TextView) getView().findViewById(
				R.id.description);
		TextView percent = (TextView) getView().findViewById(R.id.percent);
		CheckBox completed = (CheckBox) getView().findViewById(R.id.completed);
		title.setText(task.getName());
		if (!task.isEvaluable()) {
			mark.setVisibility(View.GONE);
			IconTextView trophy = (IconTextView) getView().findViewById(
					R.id.icon_nota);
			trophy.setVisibility(View.GONE);
		} else
			mark.setText(String.valueOf(task.getGrade()));
		date.setText(Utils.getPrettyDate(task.getDueDate()));
		place.setText(task.getPlace());
		if (task.getDescription() != null
				&& (!task.getDescription().equals(""))) {
			description.setText(task.getDescription());
			description.setTextColor(Color.BLACK);
		}
		completed.setChecked(task.isCompleted());

		setIsCompletedListener();
	}

	private void setIsCompletedListener() {
		CheckBox completed = (CheckBox) getView().findViewById(R.id.completed);
		completed.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked && task.isEvaluable()) {
					showMarkDialog();
				} else if (isChecked) {
					setTaskFinished(true);
				} else {
					setTaskFinished(false);
				}
			}
		});

	}

	protected void showMarkDialog() {
		EnterGradeDialogFragment dialog = new EnterGradeDialogFragment();
		dialog.setListener(this);
		dialog.show(getFragmentManager(), "EnterGradeDialogFragment");
	}

	protected void setTaskFinished(boolean isCompleted) {
		task.setCompleted(isCompleted);
		new SetTaskTask().execute();
	}

	private class SetTaskTask extends AsyncTask<Void, Void, Boolean> {

		private String exception;

		@Override
		protected Boolean doInBackground(Void... arg0) {

			TaskDAO taskDao = new TaskDAOsqlite(context);
			taskDao.updateTask(task);
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			setView();
		}
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String grade) {
		float gradeFloat = Float.parseFloat(grade);
		task.setGrade(gradeFloat);
		setTaskFinished(true);
	}

}
