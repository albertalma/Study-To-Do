package almartapps.studytodo.domain.utils;

import java.util.List;

import almartapps.studytodo.domain.model.Task;

public class GradeCalculator {

	/**
	 * Calculates the grade of a {@link Subject}. Only <b>completed</b> and  
	 * evaluable tasks are taken into account for this calculation.
	 * <p>
	 * The grade is calculated as the sum of: task.grade * (task.percentage / 100) 
	 * 
	 * @param subjectTasks a list of all the Subject's tasks
	 * @return a decimal grade
	 */
	public static float calculateSubjectGrade(final List<Task> subjectTasks) {
		float grade = 0.0f;
		for (final Task task : subjectTasks) {
			if (task.isEvaluable() && task.isCompleted()) {
				grade += task.getGrade() * ( ((float) task.getPercentage()) / 100.0 );
			}
		}
		return grade;
	}
	
}
