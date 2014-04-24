package almartapps.studytodo.view.adapters;

import java.util.List;
import java.util.Map;

import almartapps.studytodo.R;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import almartapps.studytodo.view.utils.TextColorPicker;
import almartapps.studytodo.view.utils.Utils;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.IconTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Task> {
	private final Context context;
	private Map<Long, Subject> subjects;
	private Subject subject;

	public TaskAdapter(Context context, List<Task> items, Map<Long, Subject> subjects) {
		super(context, R.layout.task_item, items);
		this.context = context;
		this.subjects = subjects;
	}

	public TaskAdapter(Context context, List<Task> items, Subject subject) {
		super(context, R.layout.task_item, items);
		this.context = context;
		this.subject = subject;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.task_item, parent, false);

		TextView assig_name_txt = (TextView) rowView
				.findViewById(R.id.task_assig);
		TextView task_name_txt = (TextView) rowView
				.findViewById(R.id.task_name);
		TextView task_time_txt = (TextView) rowView
				.findViewById(R.id.task_time);
		TextView task_place_txt = (TextView) rowView
				.findViewById(R.id.task_place);
		TextView task_priority_txt = (TextView) rowView
				.findViewById(R.id.task_priority);
		TextView task_percentage_txt = (TextView) rowView
				.findViewById(R.id.task_percentage);
		
		TextView taskGradeTextView = (TextView) rowView
				.findViewById(R.id.task_grade);
		
		TextView gradeIcon = (TextView) rowView.findViewById(R.id.task_grade_icon);
		TextView completedIcon = (TextView) rowView.findViewById(R.id.task_completed);

		Task task = getItem(position);
		Long subjectId = task.getSubjectId();
		if (subjects != null)
			subject = subjects.get(subjectId);
		assig_name_txt.setText(subject.getName());
		assig_name_txt.setTextColor(TextColorPicker.pickTextColorFromBackground(subject.getColor()));
		if (task.getName().length() < 15) {
			task_name_txt.setText(task.getName());
		} else {
			task_name_txt.setText(task.getName().substring(0, Math.min(task.getName().length(), 15)) + "...");
		}
		task_time_txt.setText(Utils.getPrettyDate(task.getDueDate()));
		task_priority_txt.setText(task.getPriority().name());
		if (task.isEvaluable()) {
			task_percentage_txt.setText(String.valueOf(task.getPercentage()));
			taskGradeTextView.setText(String.valueOf(task.getGrade()));
		} else {
			task_percentage_txt.setText(" - ");
			taskGradeTextView.setVisibility(View.GONE);
			gradeIcon.setVisibility(View.GONE);
		}
		task_place_txt.setText(task.getPlace());

		if (task.isCompleted()) {
			completedIcon.setText(R.string.completed_icon_yes);
		} else {
			completedIcon.setText(R.string.completed_icon_no);
		}
		
		// setColor
		RelativeLayout rLayout = (RelativeLayout) rowView
				.findViewById(R.id.task_relative_layout);
		LayerDrawable bgDrawable = (LayerDrawable) rLayout.getBackground();
		final GradientDrawable shape = (GradientDrawable) bgDrawable
				.findDrawableByLayerId(R.id.subject_color);
		shape.setColor(subject.getColor());
		
		return rowView;
	}
}
