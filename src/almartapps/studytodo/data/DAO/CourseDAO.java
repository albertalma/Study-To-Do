package almartapps.studytodo.data.DAO;

import java.util.Date;
import java.util.List;

import almartapps.studytodo.domain.model.Course;

public interface CourseDAO extends GenericDAO<Course> {

	public List<Course> getCourse(Date date);
	
}
