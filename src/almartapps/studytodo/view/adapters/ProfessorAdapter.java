package almartapps.studytodo.view.adapters;

import java.util.List;
import java.util.Map;

import almartapps.studytodo.R;
import almartapps.studytodo.domain.model.Professor;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.Task;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProfessorAdapter extends ArrayAdapter<Professor> {
	private final Context context;


	public ProfessorAdapter(Context context, List<Professor> items) {
		super(context, R.layout.professor_item, items);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.professor_item, parent, false);

		TextView name = (TextView) rowView
				.findViewById(R.id.professor_name_textview);
		TextView email = (TextView) rowView
				.findViewById(R.id.professor_email_textview);
		TextView office = (TextView) rowView
				.findViewById(R.id.professor_office_textview);
		TextView phone = (TextView) rowView
				.findViewById(R.id.professor_phone_textview);

		Professor professor = getItem(position);
		name.setText(professor.getName());
		email.setText(professor.getEmailAddress());
		office.setText(professor.getOfficeAddress());
		phone.setText(professor.getTelephone());
	
		return rowView;
	}
}
