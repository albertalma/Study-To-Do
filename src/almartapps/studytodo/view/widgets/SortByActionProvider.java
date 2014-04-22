package almartapps.studytodo.view.widgets;

import java.util.Arrays;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.view.adapters.SortBySubmenuAdapter;
import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SortByActionProvider extends ActionProvider {

	private final Context context;
	
	public SortByActionProvider(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View onCreateActionView() {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.sort_by_action_submenu, null);
		List<String> titles = Arrays.asList(context.getResources().getStringArray(R.array.sort_by_menu_options));
		List<String> icons = Arrays.asList(context.getResources().getStringArray(R.array.sort_by_menu_icons));
		ListAdapter adapter = new SortBySubmenuAdapter(context, R.layout.sort_by_submenu_item, titles, icons);
		ListView menuList = (ListView) view.findViewById(R.id.sort_by_submenu_list);
		menuList.setAdapter(adapter);
		return view;
	}

}
