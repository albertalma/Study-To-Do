package almartapps.studytodo.domain.model;

public class ScheduledClass {

	private WeekDay weekDay;
	
	private Time startTime;
	
	private Time endTime;
	
	private String place;
	
	private ClassType type;
	
	private long subjectId;

	public ScheduledClass(WeekDay weekDay, Time startTime, Time endTime, String place, ClassType type, long subjectId) {
		this.weekDay = weekDay;
		this.startTime = startTime;
		this.endTime = endTime;
		this.place = place;
		this.type = type;
		this.subjectId = subjectId;
	}
	
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
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public ClassType getType() {
		return type;
	}

	public void setType(ClassType type) {
		this.type = type;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	
}
