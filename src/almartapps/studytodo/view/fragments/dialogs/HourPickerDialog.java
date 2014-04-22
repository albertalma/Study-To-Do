package almartapps.studytodo.view.fragments.dialogs;

import java.util.Calendar;
import java.util.Locale;

import almartapps.studytodo.view.activities.CreateClassActivity;
import almartapps.studytodo.view.activities.CreateTaskActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

public class HourPickerDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		 final Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        int minute = c.get(Calendar.MINUTE);
		
	        return new TimePickerDialog(getActivity(), (CreateClassActivity)getActivity(), hour, minute,
	                DateFormat.is24HourFormat(getActivity()));
	}
	
}
