package almartapps.studytodo.view.fragments.dialogs;

import java.util.Calendar;
import java.util.Locale;

import almartapps.studytodo.view.activities.CreateTaskActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TaskDatePickerDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		return new DatePickerDialog(getActivity(), (CreateTaskActivity)getActivity(), year, monthOfYear, dayOfMonth);
	}
	
}
