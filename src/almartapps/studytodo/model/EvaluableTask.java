package almartapps.studytodo.model;

public class EvaluableTask extends Task {

	/**
	 * It is intended to be in the range [0-100], not allowing decimals.
	 */
	private int percentage;
	
	private float grade;

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}
	
}
