package com.example.grade_tracker;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.example.grade_tracker.BaseActivity;
import com.example.grade_tracker.BaseRout;

public class GradeTrackerMainActivity extends BaseActivity {
	private ClassDataSource datasource;
	int DeleteAllClass = 1;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grade_tracker);
		BaseRout.Init(this, "grade_tracker");
		
		datasource = new ClassDataSource(this);
		datasource.open();
		List<class_database> values = datasource.getAllClasses();
		
		if (values.size()==0){
			getClassName("Add Your First Class", "To get started, add your first class:");
		}
		ArrayAdapter<class_database> adapter = new ArrayAdapter<class_database>(this, R.layout.class_list_view, values);
		this.zListViewfId(R.id_main.listView).setAdapter(adapter);
		this.zListViewfId(R.id_main.listView).setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int arg2,
					long arg3) {
				
				Globals.selected_class = (String) ((TextView) view).getText();
				zFrmShow(class_info.class);
			}
			
		
		});
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
