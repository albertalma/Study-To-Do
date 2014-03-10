package almartapps.studytodo.view.adapters;

import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.domain.model.Course;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CourseAdapter extends ArrayAdapter<Course> {
	private final Context context;

	public CourseAdapter(Context context, List<Course> navigationDrawerItems) {
		super(context, R.layout.course_item, navigationDrawerItems);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.nav_drawer_item, parent, false);

		TextView name = (TextView) rowView
				.findViewById(R.id.course_name_textview);
		TextView start = (TextView) rowView.findViewById(R.id.course_start_date_textview);
		TextView end = (TextView) rowView
				.findViewById(R.id.course_end_date_textview);
		
		Course course = getItem(position);
		name.setText(course.getName());
		start.setText(course.getStartDate().toString());
		end.setText(course.getEndDate().toString());
		return rowView;
	}
}
