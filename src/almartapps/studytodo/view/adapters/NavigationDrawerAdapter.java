package almartapps.studytodo.view.adapters;

import java.util.List;

import almartapps.studytodo.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.IconTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigationDrawerAdapter extends ArrayAdapter<String> {
	private final Context context;

	public NavigationDrawerAdapter(Context context,
			List<String> navigationDrawerItems) {
		super(context, R.layout.nav_drawer_item, navigationDrawerItems);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater
				.inflate(R.layout.nav_drawer_item, parent, false);

		TextView navAction = (TextView) rowView
				.findViewById(R.id.text_drawer_name);

		IconTextView icon = (IconTextView) rowView
				.findViewById(R.id.image_drawer_icon);
		switch (position) {
		case 0:
			icon.setText("{fa-pencil-square-o}");
			break;
		case 1:
			icon.setText("{fa-tasks}");
			break;
		case 2:
			icon.setText("{fa-tags}");
			break;
		case 3:
			icon.setText("{fa-calendar}");
			break;
		}
		String name = getItem(position);
		navAction.setText(name);
		return rowView;
	}
}
