package almartapps.studytodo.view.fragments.dialogs;

import java.util.Arrays;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.view.adapters.SortBySubmenuAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SortByAlertDialog extends DialogFragment {
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getParentFragment().getActivity());
		
		LayoutInflater inflater = getParentFragment().getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.sort_by_action_submenu, null);
		List<String> titles = Arrays.asList(getParentFragment().getActivity().getResources().getStringArray(R.array.sort_by_menu_options));
		List<String> icons = Arrays.asList(getParentFragment().getActivity().getResources().getStringArray(R.array.sort_by_menu_icons));
		ListAdapter adapter = new SortBySubmenuAdapter(getParentFragment().getActivity(), R.layout.sort_by_submenu_item, titles, icons);
		ListView list = (ListView) view.findViewById(R.id.sort_by_submenu_list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
			}
		});
		builder.setView(view);
		
		return builder.create();
    }
	
}
