package almartapps.studytodo.view.adapters;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.domain.utils.GradeCalculator;
import almartapps.studytodo.view.utils.OnPostExecuteCallback;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class SubjectAdapter extends ArrayAdapter<Subject> {
	private final Context context;
	private TextView grade;

	public SubjectAdapter(Context context, List<Subject> subjects) {
		super(context, R.layout.course_item, subjects);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.subject_item, parent, false);

		TextView name = (TextView) rowView
				.findViewById(R.id.subject_name_textview);
		grade = (TextView) rowView.findViewById(R.id.subject_grade_textview);

		Subject subject = getItem(position);
		name.setText(subject.getName());

		new GetTaskFromSubjectTask().execute(subject);
		View color = (View) rowView.findViewById(R.id.subject_color_view);
		color.setBackgroundColor(subject.getColor());
		return rowView;
	}

	private class GetTaskFromSubjectTask extends
			AsyncTask<Subject, Void, List<Task>> {

		private String exceptionMessage;

		@Override
		protected List<Task> doInBackground(Subject... arg0) {
			// get DAOs
			TaskDAO taskDao = new TaskDAOsqlite(context);
			return taskDao.getTasksFromSubject(arg0[0].getId());

		}

		protected void onPostExecute(List<Task> tasks) {
			GradeCalculator gradeCalculator = new GradeCalculator();
			Log.d("SubjectAdapter", "grade: " + gradeCalculator.calculateSubjectGrade(tasks));
			grade.setText("" + gradeCalculator.calculateSubjectGrade(tasks));
		}
	}
}
