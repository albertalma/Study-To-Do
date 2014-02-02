package almartapps.studytodo.data.db4o;

import almartapps.studytodo.data.DAO.GenericDAO;

public abstract class GenericDAOdb4o<T> implements GenericDAO<T> {
	
	public String fooDatabaseConnection;
	
	public GenericDAOdb4o() {
		fooDatabaseConnection = new String();
	}
	
}
