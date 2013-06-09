package com.jordanbang.gradetracker.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import big.bang.grade_tracker.R;

import com.jordanbang.gradetracker.BaseActivity;
import com.jordanbang.gradetracker.BaseRout;
import com.jordanbang.gradetracker.ClassDataSource;
import com.jordanbang.gradetracker.class_database;
import com.jordanbang.gradetracker.class_info;
import com.jordanbang.gradetracker.fragments.ClassListFragment;
import com.jordanbang.gradetracker.utility.PreferenceManagerUtility;

public class GradeTrackerMainActivity extends FragmentActivity {
	private ClassDataSource datasource;
	int DeleteAllClass = 1;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classlist);
		
		if (savedInstanceState == null){
			FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
			trans.add(R.id.fragment_container, new ClassListFragment());
			trans.commit();
		}
		
		PreferenceManagerUtility.Init(this, "MyGradeTracker");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_grade_tracker, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int tm = item.getItemId();
		switch(tm){
		case R.id_menu.menu_add:
			getClassName("Add a Class", "Enter the name of the class:");
			break;
		case R.id_menu.menu_deleteallclass:
			MessageBox(DeleteAllClass, "Delete All Classes", "Are you sure you want to delete all your classes?", true, true);
			break;
		}
		return true;
	}
	
	public void OnClickOK(int mesgNum, DialogInterface dialog, int which) 
	{
		datasource.deleteAllClass();
		ArrayAdapter<class_database> adapter = (ArrayAdapter<class_database>) this.zListViewfId(R.id_main.listView).getAdapter();
		adapter.clear();
		adapter.notifyDataSetChanged();
	}
	
	public void OnClickCancel(int mesgNum, DialogInterface dialog, int which){
		
	}
	
	public void getClassName(String title, String msg){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(title);
		alert.setMessage(msg);
		final EditText input = new EditText(this);
		alert.setView(input);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String value = input.getText().toString();
				createclass(value);
				return;
			}
		});
		
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				return;
			}
		});
		
		alert.show();
	}
	
	public void createclass(String name){
		
		ArrayAdapter<class_database> adapter = (ArrayAdapter<class_database>) this.zListViewfId(R.id_main.listView).getAdapter();
		class_database classes = datasource.createClass(name);
		adapter.clear();
		List<class_database> values = datasource.getAllClasses();
		adapter.addAll(values);
		adapter.notifyDataSetChanged();
	}
}
