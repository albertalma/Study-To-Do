package almartapps.studytodo.view.adapters;

import java.util.List;

import almartapps.studytodo.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.IconTextView;
import android.widget.TextView;

public class SortBySubmenuAdapter extends ArrayAdapter<String> {

	private final Context context;
	private List<String> titles;
	private List<String> icons;
	
	public SortBySubmenuAdapter(Context context, int textViewResourceId, List<String> titles, List<String> icons) {
		super(context, textViewResourceId, titles);
		this.context = context;
		this.titles = titles;
		this.icons = icons;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.sort_by_submenu_item, parent, false);
		IconTextView icon = (IconTextView) rowView.findViewById(R.id.sort_by_submenu_item_icon);
		TextView title = (TextView) rowView.findViewById(R.id.sort_by_submenu_item_title);
		icon.setText(icons.get(position));
		title.setText(titles.get(position));
		return rowView;
	}
	
	
}
