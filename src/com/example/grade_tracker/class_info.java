package com.example.grade_tracker;

import java.util.ArrayList;
import java.util.List;

import big.bang.grade_tracker.R;
import big.bang.grade_tracker.R.id;
import big.bang.grade_tracker.R.id_class_info;
import big.bang.grade_tracker.R.id_menu_class;
import big.bang.grade_tracker.R.layout;
import big.bang.grade_tracker.R.menu;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

public class class_info extends BaseActivity {
	private AssignDataSource datasource;
	String classname;
	int editflag = 1;
	assign itemtodelete;
	List<assign> values;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_info);
		classname = Globals.selected_class;
		this.setTitle(classname);
		
		//getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		datasource = new AssignDataSource(this);
		datasource.open();
		values = datasource.getAllAsignmentsforClass(classname);
		if (values.size() == 0){
			addAssignment(0);
		}
		else{
			getCurrentAvg();
		}
		zListViewfId(R.id_class_info.listView1).setAdapter(new AssignListAdapter((ArrayList<assign>) values, this));
		registerForContextMenu(zListViewfId(R.id_class_info.listView1));
		
		
		
	}
	
	public void getCurrentAvg(){
		double totalworth=0;
		for (int i = 0; i< values.size(); i++){
			totalworth = values.get(i).getWorth()+totalworth;
		}
		if (totalworth>100){
			zMakeToast("The total weights of all your items is more than 100%.", 1);
		}
		else if(totalworth<100){
			zMakeToast("The total weights of all your items is less than 100%.", 1);
		}
		
		double currentavg=0;
		totalworth = 0;
		for (int i = 0; i<values.size(); i++){
			currentavg = currentavg + values.get(i).getMark()*values.get(i).getWorth()/100;
			totalworth = totalworth+values.get(i).getWorth();
		}
		currentavg = currentavg/totalworth*100;
		zSetTextView(R.id_class_info.average, String.format("%.2f",currentavg)+"%");
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		if(v.getId()==R.id_class_info.listView1){
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			LinearLayout childview = (LinearLayout)info.targetView;
			itemtodelete = values.get((Integer) childview.getTag());
			String[] menuItems = {"Edit", "Delete"};
			for (int i = 0; i< menuItems.length; i++){
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
			
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		if(item.getTitle().toString().equalsIgnoreCase("Edit")){
			addAssignment(editflag);
			return true;
		}
		else{
			datasource.deleteAssign(itemtodelete);
			refreshlist();
			return true;
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_class_info, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				zFrmShowClear(GradeTrackerMainActivity.class);
				return true;
			case R.id_menu_class.menu_edit:
				editAssignment();
				return true;
			case R.id_menu_class.quick_add:
				addAssignment(0);
				return true;
			default:
				return super.onOptionsItemSelected(item);	
		}
	}
	
	//Need to changed edit to actual edit instead of delete and add
	
	private void addAssignment(int flag) {
		LayoutInflater fac = LayoutInflater.from(this);
		final View textEntry = fac.inflate(R.layout.assign_entry, null);
		
		final EditText name = (EditText)textEntry.findViewById(R.id.name);
		final EditText worth = (EditText)textEntry.findViewById(R.id.worth);
		final EditText mark = (EditText)textEntry.findViewById(R.id.mark);
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setView(textEntry);
		if (flag==1)
		{
			name.setText(itemtodelete.getAssignName());
			worth.setText(Double.toString(itemtodelete.getWorth()));
			mark.setText(Double.toString(itemtodelete.getMark()));
			
			alert.setTitle("Edit " + itemtodelete.getAssignName());
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					double in_mark;
					double in_worth;
					String in_name;
					
					if (mark.getText().toString().equalsIgnoreCase(""))
						in_mark = 0;
					else
						in_mark = Double.parseDouble(mark.getText().toString());
					
					if (worth.getText().toString().equalsIgnoreCase(""))
						in_worth = 5;
					else
						in_worth = Double.parseDouble(worth.getText().toString());
					
					if(name.getText().toString().equalsIgnoreCase(""))
						in_name = "Item";
					else
						in_name = name.getText().toString();
					
					datasource.deleteAssign(itemtodelete);
					datasource.createAssignment(in_name, classname, in_mark, in_worth);
					refreshlist();
				}
			});
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
				}
				
			});
		}else{
			alert.setTitle("Add a New Item to this Class");
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					double in_mark;
					double in_worth;
					String in_name;
					
					if (mark.getText().toString().equalsIgnoreCase("") & worth.getText().toString().equalsIgnoreCase("") & name.getText().toString().equalsIgnoreCase("")){
						
					}else{
						if (mark.getText().toString().equalsIgnoreCase(""))
							in_mark = 0;
						else
							in_mark = Double.parseDouble(mark.getText().toString());
						
						if (worth.getText().toString().equalsIgnoreCase(""))
							in_worth = 5;
						else
							in_worth = Double.parseDouble(worth.getText().toString());
						
						if(name.getText().toString().equalsIgnoreCase(""))
							in_name = "Item";
						else
							in_name = name.getText().toString();
						
						datasource.createAssignment(in_name, classname, in_mark, in_worth);
						refreshlist();
					}
				}
			});
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
				}
				
			});
		}
		
		
		
		
		
		
		
		alert.show();
	}
	
	public void refreshlist(){
		AssignListAdapter adp = (AssignListAdapter) this.zListViewfId(R.id_class_info.listView1).getAdapter();
		adp.clearAll();
		values = datasource.getAllAsignmentsforClass(classname);
		adp.addAll((ArrayList<assign>) values);
		adp.notifyDataSetChanged();
		getCurrentAvg();
	}

	public void editAssignment(){
		
	}
}
