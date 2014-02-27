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
	 * @return the object that was inserted, with its ID set if it wasn't set
	 */
	public T insert(T object);
	
	/**
	 * Deletes an object of a certain class stored in the database.
	 * @param object the object to be deleted
	 * @return <code>true</code> if the object was deleted, <code>false</code> otherwise
	 */
	public boolean delete(T object);
	
	/**
	 * Deletes all objects of a certain class stored in the database.
	 * @return the number of objects deleted
	 */
	public int deleteAll();
}
