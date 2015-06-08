package com.jordanbang.gradetracker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import com.jordanbang.gradetracker.R;
import com.jordanbang.gradetracker.fragments.ClassDetailsFragment;
import com.jordanbang.gradetracker.fragments.ClassListFragment;
import com.jordanbang.gradetracker.utility.PreferenceManagerUtility;

public class GradeTrackerMainActivity extends FragmentActivity {
	int DeleteAllClass = 1;
	private ClassListFragment mFragment;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classlist);
		
		if (savedInstanceState == null){
			FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
			mFragment = new ClassListFragment();
			trans.add(R.id.fragment_container, mFragment);
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
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Delete All Classes");
			alert.setMessage("Are you sure you want to delete all classes?");
			alert.setPositiveButton("Ok", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mFragment.deleteAllClasses();
				}
			});
			alert.setNegativeButton("Cancel", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			alert.show();
			break;
		}
		return true;
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
				mFragment.createclass(value);
				return;
			}
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				return;
			}
		});
		alert.show();
	}
	
	public void showClassDetails(int classId){
		FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
		ClassDetailsFragment fragment = new ClassDetailsFragment();
		trans.replace(R.id.fragment_container, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}
}
