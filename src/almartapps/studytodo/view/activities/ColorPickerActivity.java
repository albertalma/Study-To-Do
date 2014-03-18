package almartapps.studytodo.view.activities;

import almartapps.studytodo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SVBar;

public class ColorPickerActivity extends Activity {

	public static final int PICK_COLOR_REQUEST_CODE = 1;
	
	public static final String PICKED_COLOR_EXTRA = "picked_color";
	
	private ColorPicker colorPicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_pick);
		initializeView();
	}
	
	private void initializeView() {
		initializeColorPicker();
		initializePickButton();
	}
	
	private void initializeColorPicker() {
		colorPicker = (ColorPicker) findViewById(R.id.color_picker);
		
		colorPicker.setShowOldCenterColor(false);
		
		SVBar svBar = (SVBar) findViewById(R.id.saturation_value_bar);
		colorPicker.addSVBar(svBar);
	}
	
	private void initializePickButton() {
		Button pickButton = (Button) findViewById(R.id.color_picker_button);
		pickButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pickColorAndReturn();
			}
		});
	}
	
	private void pickColorAndReturn() {
		int color = colorPicker.getColor();
		Intent returnIntent = new Intent();
		returnIntent.putExtra(PICKED_COLOR_EXTRA, color);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
	
}
