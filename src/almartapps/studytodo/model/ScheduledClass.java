package almartapps.studytodo.model;

public class ScheduledClass {

	private WeekDay weekDay;
	
	private Time startTime;
	
	private Time endTime;
	
	private Subject subject;

	public WeekDay getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(WeekDay weekDay) {
		this.weekDay = weekDay;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Subject getSubject() {
		return subject;
	}

	protected void setSubject(Subject subject) {
		this.subject = subject;
	}
	
}
