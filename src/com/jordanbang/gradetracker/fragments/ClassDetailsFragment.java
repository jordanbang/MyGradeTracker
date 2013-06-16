package com.jordanbang.gradetracker.fragments;

import java.util.ArrayList;
import java.util.List;

import com.jordanbang.gradetracker.AssignDataSource;
import com.jordanbang.gradetracker.AssignListAdapter;
import com.jordanbang.gradetracker.R;
import com.jordanbang.gradetracker.models.Assignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ClassDetailsFragment extends Fragment {
	private AssignDataSource mDataSource;
	private int classId;
	int editflag = 1;
	Assignment itemToDelete;
	List<Assignment> mValues;
	ListView mListView;
	TextView mAverageText;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_classdetails, null);
		
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		mDataSource = new AssignDataSource(getActivity().getBaseContext());
		mDataSource.open();
		mValues = mDataSource.getAllAsignmentsforClass(classId);
		if (mValues.size() == 0){
			addAssignment(0);
		} else {
			getCurrentAvg();
		}
		
		mListView = (ListView) v.findViewById(R.id.listView1);
		mListView.setAdapter(new AssignListAdapter((ArrayList<Assignment>)mValues, getActivity().getBaseContext()));
		mAverageText = (TextView) v.findViewById(R.id.average);
		registerForContextMenu(mListView);
		return v;
	}
	
	public void setClassId(int id){
		classId = id;
	}
	
	public void getCurrentAvg() {
		double totalworth = 0;
		for (int i = 0; i < mValues.size(); i++){
			totalworth = mValues.get(i).getWorth() + totalworth;
		}
		if (totalworth > 100)
			Toast.makeText(getActivity().getBaseContext(),"The total weights of all your items is more than 100%.", Toast.LENGTH_LONG).show();
		else if (totalworth < 100)
			Toast.makeText(getActivity().getBaseContext(), "The total weights of all your items is less than 100%.", Toast.LENGTH_LONG).show();
		
		double currentavg = 0;
		totalworth = 0;
		for (int i = 0; i < mValues.size(); i++) {
			currentavg = currentavg + mValues.get(i).getMark()*mValues.get(i).getWorth()/100;
			totalworth = totalworth + mValues.get(i).getWorth();
		}
		currentavg = currentavg/(totalworth*100);
		mAverageText.setText(String.format("%.2f", currentavg));
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listView1){
			AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
			LinearLayout childView = (LinearLayout) info.targetView;
			itemToDelete = mValues.get((Integer) childView.getTag());
			String[] menuItems = {"Edit", "Delete"};
			for (int i = 0; i < menuItems.length; i++){
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle().toString().equalsIgnoreCase("edit")){
			addAssignment(editflag);
			return true;
		} else if (item.getTitle().toString().equalsIgnoreCase("delete")){
			mDataSource.deleteAssign(itemToDelete);
			refreshlist();
			return true;
		}
		return true;
	}
	
	private void addAssignment(int flag) {
		LayoutInflater fac = LayoutInflater.from(getActivity().getBaseContext());
		final View textEntry = fac.inflate(R.layout.assign_entry, null);
		
		final EditText name = (EditText)textEntry.findViewById(R.id.name);
		final EditText worth = (EditText)textEntry.findViewById(R.id.worth);
		final EditText mark = (EditText)textEntry.findViewById(R.id.mark);
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity().getBaseContext());
		alert.setView(textEntry);
		if (flag == editflag)
		{
			name.setText(itemToDelete.getAssignName());
			worth.setText(Double.toString(itemToDelete.getWorth()));
			mark.setText(Double.toString(itemToDelete.getMark()));
			alert.setTitle("Edit " + itemToDelete.getAssignName());
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					double markValue;
					double worthValue;
					String nameValue;
					if (mark.getText().toString().equalsIgnoreCase(""))
						markValue = 0;
					else
						markValue = Double.parseDouble(mark.getText().toString());
					if (worth.getText().toString().equalsIgnoreCase(""))
						worthValue = 5;
					else
						worthValue = Double.parseDouble(worth.getText().toString());
					if(name.getText().toString().equalsIgnoreCase(""))
						nameValue = "Item";
					else
						nameValue = name.getText().toString();
					mDataSource.deleteAssign(itemToDelete);
					mDataSource.createAssignment(nameValue, classId, markValue, worthValue);
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
					double markValue;
					double worthValue;
					String in_name;
					
					if (!mark.getText().toString().equalsIgnoreCase("") && !worth.getText().toString().equalsIgnoreCase("") && !name.getText().toString().equalsIgnoreCase("")){
						if (mark.getText().toString().equalsIgnoreCase(""))
							markValue = 0;
						else
							markValue = Double.parseDouble(mark.getText().toString());
						if (worth.getText().toString().equalsIgnoreCase(""))
							worthValue = 5;
						else
							worthValue = Double.parseDouble(worth.getText().toString());
						if(name.getText().toString().equalsIgnoreCase(""))
							in_name = "Item";
						else
							in_name = name.getText().toString();
						mDataSource.createAssignment(in_name, classId, markValue, worthValue);
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
		AssignListAdapter adp = (AssignListAdapter) mListView.getAdapter();
		adp.clearAll();
		mValues = mDataSource.getAllAsignmentsforClass(classId);
		adp.addAll((ArrayList<Assignment>) mValues);
		adp.notifyDataSetChanged();
		getCurrentAvg();
	}
	
}