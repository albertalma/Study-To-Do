package almartapps.studytodo.view.adapters;

import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.domain.model.Subject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SubjectAdapter extends ArrayAdapter<Subject> {
	private final Context context;

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
		TextView grade = (TextView) rowView
				.findViewById(R.id.subject_grade_textview);

		Subject subject = getItem(position);
		name.setText(subject.getName());
		grade.setText("0");
		View color = (View) rowView.findViewById(R.id.subject_color_view);
		color.setBackgroundColor(subject.getColor());
		return rowView;
	}
}
