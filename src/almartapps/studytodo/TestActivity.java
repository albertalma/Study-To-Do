package almartapps.studytodo;

import java.util.List;

import almartapps.studytodo.data.DAO.TaskDAO;
import almartapps.studytodo.data.sqlite.TaskDAOsqlite;
import almartapps.studytodo.model.Task;
import almartapps.studytodo.model.TaskPriority;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends Activity {
	
	private static final String TAG = "TestActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		setButtonListener();
	}
	
	private void setButtonListener() {
		Button button = (Button) findViewById(R.id.test_run_button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				runTest();
			}
		});
	}
	
	private String [] tasksName = {"task 1", "task 2", "task 3"};
	private String [] tasksDescription = {"task1description", "task2description", "task3description"};
	
	private void runTest() {
		TaskDAO dao = new TaskDAOsqlite(this);
		
		Task task1 = new Task(tasksName[0], tasksDescription[0]);
		task1.setPriority(TaskPriority.low);
		Task task2 = new Task(tasksName[1], tasksDescription[1]);
		task2.setPriority(TaskPriority.medium);
		Task task3 = new Task(tasksName[2], tasksDescription[2]);
		task3.setPriority(TaskPriority.high);
		
		//first inserts
		dao.insert(task1);
		dao.insert(task2);
		dao.insert(task3);
		
		//get all
		List<Task> tasks = dao.getAll();
		
		//dump tasks
		for (Task task : tasks) {
			dumpTask(task);
		}
		
		//delete task 1
		dao.delete(task1);
		
		//get all
		tasks = dao.getAll();
		
		//dump tasks
		for (Task task : tasks) {
			dumpTask(task);
		}
	}
	
	private void dumpTask(Task task) {
		Log.i(TAG, "DUMP TASK");
		Log.i(TAG, "task.name=" + task.getName());
		Log.i(TAG, "task.description=" + task.getDescription());
		Log.i(TAG, "task.priority=" + task.getPriority());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}
