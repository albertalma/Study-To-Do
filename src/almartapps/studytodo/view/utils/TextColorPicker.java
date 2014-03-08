package almartapps.studytodo.view.utils;

import android.graphics.Color;


public class TextColorPicker {

	private static double THRESHOLD = 0.5;
	
	/**
	 * Calculates the right text color given an arbitrary background
	 * color. Text color will be either {@link Color.BLACK} or {@link Color.WHITE}.
	 *  
	 * @param backgroundColor the background color where the text will be displayed
	 * @return the optimal text color for the given background
	 */
	public static int pickTextColorFromBackground(int backgroundColor) {
		//gamma is the perceived luminance of the color
		double gamma = 1.0 - (0.299 * ((double)Color.red(backgroundColor)) 
							+ 0.587 * ((double)Color.green(backgroundColor)) 
							+ 0.114 * ((double)Color.blue(backgroundColor))) / 255.0;
		
        if (gamma < THRESHOLD)
        	return Color.BLACK;
        else
        	return Color.WHITE;
	}
	
}
