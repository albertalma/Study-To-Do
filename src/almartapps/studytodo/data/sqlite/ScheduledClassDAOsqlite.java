package almartapps.studytodo.data.sqlite;

import java.util.List;

import almartapps.studytodo.data.DAO.ScheduledClassDAO;
import almartapps.studytodo.data.exceptions.ObjectNotExistsException;
import almartapps.studytodo.domain.model.ScheduledClass;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ScheduledClassDAOsqlite extends GenericDAOsqlite<ScheduledClass> implements ScheduledClassDAO {

	public ScheduledClassDAOsqlite(Context context) {
		super(context);
	}

	@Override
	public ScheduledClass get(long classId) throws ObjectNotExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScheduledClass> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScheduledClass insert(ScheduledClass scheduledClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(ScheduledClass scheduledClass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected ContentValues getContentValues(ScheduledClass scheduledClass) {
		ContentValues values = new ContentValues();
		//TODO
		return values;
	}

	@Override
	protected ScheduledClass map(Cursor cursor) {
		// TODO Auto-generated method stub
		return null;
	}

}
