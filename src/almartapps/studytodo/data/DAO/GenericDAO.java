package almartapps.studytodo.data.DAO;

import java.util.List;

public interface GenericDAO<T> {
	
	/**
	 * Retrieve all objects of a certain class.
	 * @return a list of all the objects of the type currently stored
	 */
	public List<T> getAll();
	
	/**
	 * Inserts an object of a certain class in the database.
	 * @param object the object to be inserted
	 */
	public void insert(T object);
	
	/**
	 * Deletes an object of a certain class stored in the database.
	 * @param object the object to be deleted
	 */
	public void delete(T object);
	
}
