package almartapps.studytodo.view.adapters;

import java.util.List;
import java.util.Map;

import org.ocpsoft.prettytime.PrettyTime;

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

		Task task = getItem(position);
		Long subjectId = task.getSubjectId();
		if (subjects != null)
			subject = subjects.get(subjectId);
		assig_name_txt.setText(subject.getName());
		assig_name_txt.setTextColor(TextColorPicker.pickTextColorFromBackground(subject.getColor()));
		task_name_txt.setText(task.getName());
		task_time_txt.setText(Utils.getPrettyDate(task.getDueDate()));
		task_priority_txt.setText(task.getPriority().name());
		task_percentage_txt.setText(String.valueOf(task.getPercentage()));
		task_place_txt.setText(task.getPlace());

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
