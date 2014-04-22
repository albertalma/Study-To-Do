package almartapps.studytodo.view.adapters;

import java.util.List;
import java.util.Map;

import almartapps.studytodo.R;
import almartapps.studytodo.domain.model.ScheduledClass;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClassAdapter extends ArrayAdapter<ScheduledClass> {
	private final Context context;
	private Map<Long, Subject> subjects;

	public ClassAdapter(Context context, List<ScheduledClass> items, Map<Long, Subject> subjects) {
		super(context, R.layout.timetable_item, items);
		this.context = context;
		this.subjects = subjects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.timetable_item, parent, false);

		TextView name = (TextView) rowView
				.findViewById(R.id.subject_name_textview);
		TextView startTime = (TextView) rowView
				.findViewById(R.id.start_time);
		TextView endTime = (TextView) rowView
				.findViewById(R.id.end_time);

		ScheduledClass scheduledClass = getItem(position);
		Long subjectId = scheduledClass.getSubjectId();
		Subject subject = subjects.get(subjectId);
		name.setText(subject.getName());
		View color = (View) rowView.findViewById(R.id.subject_color_view);
		color.setBackgroundColor(subject.getColor());
		startTime.setText(scheduledClass.getStartTime().toString());
		endTime.setText(scheduledClass.getEndTime().toString());

		return rowView;
	}
}
