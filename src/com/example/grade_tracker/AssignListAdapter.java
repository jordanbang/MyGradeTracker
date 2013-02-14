package com.example.grade_tracker;

import java.util.ArrayList;

import big.bang.grade_tracker.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AssignListAdapter extends BaseAdapter {
	private ArrayList data;
	Context context;
	
	AssignListAdapter(ArrayList data_n, Context c){
		data = data_n;
		context = c;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void clearAll(){
		if(!data.isEmpty())
			data.clear();
	}
	
	public void addAll(ArrayList values){
		data = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if (v== null)
		{
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.assign_list_view, null);
		}
		v.setTag(position);
		
		assign assignment = (assign) data.get(position);
		((TextView)v.findViewById(R.id_assign_list.assign_name)).setText(assignment.getAssignName());
		((TextView)v.findViewById(R.id_assign_list.assign_mark)).setText(String.format("%.2f", assignment.getMark())+"%");
		((TextView)v.findViewById(R.id_assign_list.assign_worth)).setText(String.format("%.2f", assignment.getWorth())+"%");
		
		return v;
	}
	
}
