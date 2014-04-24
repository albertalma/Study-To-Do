package almartapps.studytodo.view.fragments;

import java.util.Calendar;
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

public class TodayClassesFragment extends ListFragment {

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
		new GetTodayClassTask().execute();
	}
	
	 private class GetTodayClassTask extends AsyncTask<Void, Void, Boolean> {

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
					Calendar calendar = Calendar.getInstance();
					int day = calendar.get(Calendar.DAY_OF_WEEK);
					WeekDay weekDay = null;
					switch (day) {
					case Calendar.SUNDAY:
						weekDay = WeekDay.sunday;
						break;
					case Calendar.MONDAY:
						weekDay = WeekDay.monday;
						break;
					case Calendar.TUESDAY:
						weekDay = WeekDay.tuesday;
						break;
					case Calendar.WEDNESDAY:
						weekDay = WeekDay.wednesday;
						break;
					case Calendar.THURSDAY:
						weekDay = WeekDay.thursday;
						break;
					case Calendar.FRIDAY:
						weekDay = WeekDay.friday;
						break;
					case Calendar.SATURDAY:
						weekDay	= WeekDay.saturday;	
						break;
					}
				try {
					scheduledClassList = scheduledClassDAO.getScheduledClasses(weekDay, subjectIds);
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
