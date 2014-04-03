package almartapps.studytodo.view.fragments;

import java.util.Arrays;
import java.util.List;

import almartapps.studytodo.R;
import almartapps.studytodo.view.adapters.TodayPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TodayFragment extends Fragment {
	
	private static final String TAG = "view.fragments.TodayFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.today_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.today_view_pager);
		List<String> pageTitles = Arrays.asList(getResources().getStringArray(R.array.today_view_pager_titles));
		viewPager.setAdapter(new TodayPagerAdapter(getChildFragmentManager(), pageTitles));
	}
	
	/*
	private Context context;
	private Map<Long,Subject> subjectsMap;
	private List<Task> todayTasks;
	private List<ScheduledClass> todayClasses;
	
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_list, container, false);
    }
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = getActivity();
		new FetchTodayDataTask().execute();
	}
    
    private class FetchTodayDataTask extends AsyncTask<Void, Void, Boolean> {

		private String exceptionMessage;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			//get DAOs
			SubjectDAO subjectDao = new SubjectDAOsqlite(context);
			TaskDAO taskDao = new TaskDAOsqlite(context);
			TxTodayScheduledClasses classesTransaction = new TxTodayScheduledClasses(context);
			
			Calendar calendar = Calendar.getInstance();
			List<Subject> subjectsList = null;
			//fetch data
			try {
				subjectsList = subjectDao.getAll();
				todayTasks = taskDao.getTasks(calendar.getTime());
				todayClasses = classesTransaction.getTodayScheduledClasses();
			} catch (Exception e) {
				exceptionMessage = e.getMessage();
				return true;
			}
			
			//build subjects map
			subjectsMap = new HashMap<Long, Subject>();
			for (Subject s : subjectsList) {
				subjectsMap.put(s.getId(), s);
			}
			
			//merge tasks & classes
			//TODO
			
			return false;
		}

		protected void onPostExecute(Boolean exceptionRaised) {
			if (exceptionRaised) {
				Log.e(TAG, exceptionMessage);
			} else {
				setView();
			}
		}
	}
    
    public void setView() {
		TaskAdapter taskAdapter = new TaskAdapter(context, todayTasks, subjectsMap);
		setListAdapter(taskAdapter);
	}
    */
}
