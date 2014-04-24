package almartapps.studytodo.view.fragments.timetable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import almartapps.studytodo.R;
import almartapps.studytodo.data.DAO.ScheduledClassDAO;
import almartapps.studytodo.data.DAO.SubjectDAO;
import almartapps.studytodo.data.sqlite.ScheduledClassDAOsqlite;
import almartapps.studytodo.data.sqlite.SubjectDAOsqlite;
import almartapps.studytodo.domain.model.ScheduledClass;
import almartapps.studytodo.domain.model.Subject;
import almartapps.studytodo.domain.model.WeekDay;
import almartapps.studytodo.view.adapters.ScheduledClassAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WednesdayFragment extends ListFragment {

	private Context context;

	private List<ScheduledClass> scheduledClassList;
	private Map<Long,Subject> subjects;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.show_list, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public void onResume() {
		super.onResume();
		new GetAllWednesdayClassTask().execute();
	}
	
	 private class GetAllWednesdayClassTask extends AsyncTask<Void, Void, Boolean> {

			private String exception;

			@Override
			protected Boolean doInBackground(Void... arg0) {
				SubjectDAO subjectDao = new SubjectDAOsqlite(context);
				List<Subject> subjectsList = subjectDao.getAll();
				subjects = new HashMap<Long, Subject>();
				Set<Long> subjectIds = new HashSet<Long>();
				for (Subject s : subjectsList) {
					subjects.put(s.getId(), s);
					subjectIds.add(s.getId());
				}
				
				ScheduledClassDAO scheduledClassDAO = new ScheduledClassDAOsqlite(context);
				try {
					scheduledClassList = scheduledClassDAO.getScheduledClasses(WeekDay.wednesday, subjectIds);
				} catch (Exception e) {
					exception = e.getMessage();
				}
				return false;
			}

			protected void onPostExecute(Boolean exceptionRaised) {
				if (exceptionRaised) {

				} else {
					setView();
				}
			}
		}
	    
	    public void setView() {
			ScheduledClassAdapter scheduledClassAdapter = new ScheduledClassAdapter(context, scheduledClassList, subjects);
			setListAdapter(scheduledClassAdapter);
		}
	    
}
