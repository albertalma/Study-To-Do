package almartapps.studytodo.view.adapters;

import java.util.List;
import java.util.Map;

import almartapps.studytodo.R;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import android.content.Context;
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
		TextView task_name_txt = (TextView) rowView.findViewById(R.id.task_name);
		TextView task_time_txt = (TextView) rowView
				.findViewById(R.id.task_time);
		TextView task_priority_txt = (TextView) rowView
				.findViewById(R.id.task_priority);
		TextView task_percentage_txt = (TextView) rowView
				.findViewById(R.id.task_percentage);
		
		Task task = getItem(position);
		Long subjectId = task.getSubjectId();
		if (subjects != null) subject= subjects.get(subjectId);
		assig_name_txt.setText(subject.getName());
		task_name_txt.setText(task.getName());
		task_time_txt.setText(task.getDueDate().toString());
		task_priority_txt.setText(task.getPriority().name());
		task_percentage_txt.setText(String.valueOf(task.getPercentage()));
		
		RelativeLayout rLayout = (RelativeLayout) rowView.findViewById(R.id.task_relative_layout);
		rLayout.setBackgroundColor(subject.getColor());
		return rowView;
	}
}
