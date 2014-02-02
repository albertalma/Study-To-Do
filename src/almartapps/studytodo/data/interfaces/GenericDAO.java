package almartapps.studytodo.data.interfaces;

import java.util.List;

public interface GenericDAO<T> {
	
	public List<T> getAll();
	
	public void store(T object);
	
	public void delete(T object);
	
}
