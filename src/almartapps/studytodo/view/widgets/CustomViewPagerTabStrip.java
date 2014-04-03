package almartapps.studytodo.view.widgets;

import almartapps.studytodo.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.PagerTabStrip;
import android.util.AttributeSet;

public class CustomViewPagerTabStrip extends PagerTabStrip {

	public CustomViewPagerTabStrip(Context context, AttributeSet attributes) {
		super(context, attributes);
		final TypedArray styleArray = context.obtainStyledAttributes(attributes, R.styleable.CustomViewPagerTabStrip);
		setTabIndicatorColor(styleArray.getColor(R.styleable.CustomViewPagerTabStrip_indicatorColor, Color.BLACK));
		setTextColor(styleArray.getColor(R.styleable.CustomViewPagerTabStrip_titleColor, Color.BLACK));
		styleArray.recycle();
	}
	
}
