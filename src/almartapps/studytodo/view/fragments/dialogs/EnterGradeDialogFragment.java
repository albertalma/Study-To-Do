package almartapps.studytodo.view.fragments.dialogs;

import almartapps.studytodo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;

public class EnterGradeDialogFragment extends DialogFragment {

	public interface EnterGradeDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog, String grade);
	}

	EnterGradeDialogListener ddListener;

	public void setListener(EnterGradeDialogListener listener) {
		ddListener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.enter_grade);
		final EditText input = new EditText(getActivity());
		input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		input.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				try {
					float val = Float.parseFloat(s.toString());
					if (val > 10.0) {
						input.setText("10");
					} else if (val < 0.0) {
						input.setText("0");
					}
				} catch (NumberFormatException ex) {
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}
		});
		builder.setView(input);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				ddListener.onDialogPositiveClick(EnterGradeDialogFragment.this,
						value);
			}
		});

		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
		return builder.create();
	}

}