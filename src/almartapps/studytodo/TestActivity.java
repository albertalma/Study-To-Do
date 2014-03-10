package almartapps.studytodo;

import java.util.ArrayList;
import java.util.List;

import almartapps.studytodo.view.utils.TextColorPicker;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TestActivity extends Activity {

	private List<Integer> colorList;
	private int colorIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		initColorList();
		setButtonListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
	
	private void initColorList() {
		colorList = new ArrayList<Integer>();
		TypedArray colorArray = getResources().obtainTypedArray(R.array.subject_light_greens);
		for (int i = 0; i < colorArray.length(); ++i) {
			int color = colorArray.getColor(i, Color.BLACK);
			colorList.add(Integer.valueOf(color));
		}
		colorArray.recycle();
		colorArray = getResources().obtainTypedArray(R.array.subject_oranges);
		for (int i = 0; i < colorArray.length(); ++i) {
			int color = colorArray.getColor(i, Color.BLACK);
			colorList.add(Integer.valueOf(color));
		}
		colorArray.recycle();
		colorArray = getResources().obtainTypedArray(R.array.subject_reds);
		for (int i = 0; i < colorArray.length(); ++i) {
			int color = colorArray.getColor(i, Color.BLACK);
			colorList.add(Integer.valueOf(color));
		}
		colorArray.recycle();
		colorIndex = 0;
	}
	
	private void setButtonListener() {
		Button button = (Button) findViewById(R.id.test_next_color_button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				changeColor();
			}
		});
	}
	
	private void changeColor() {
		//change layout background
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.test_layout);
		int backgroundColor = colorList.get(colorIndex).intValue();
		layout.setBackgroundColor(backgroundColor);
		
		//change color index
		++colorIndex;
		if (colorIndex >= colorList.size()) colorIndex = 0;
		
		//change text color
		int textColor = TextColorPicker.pickTextColorFromBackground(backgroundColor);
		TextView textView = (TextView) findViewById(R.id.test_textview);
		textView.setTextColor(textColor);
	}

}
