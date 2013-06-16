package com.jordanbang.gradetracker.fragments;

import java.util.List;

import com.jordanbang.gradetracker.ClassDataSource;
import com.jordanbang.gradetracker.R;
import com.jordanbang.gradetracker.class_database;
import com.jordanbang.gradetracker.BaseActivity.Globals;
import com.jordanbang.gradetracker.activity.GradeTrackerMainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ClassListFragment extends Fragment {
	private ClassDataSource mDataSource;
	ListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_classlist, container, false);
		mListView = (ListView) v.findViewById(R.id.classlist);
		
		mDataSource = new ClassDataSource(getActivity().getBaseContext());
		mDataSource.open();
		
		List<class_database> values = mDataSource.getAllClasses();
		if (values.size() ==0)
			((GradeTrackerMainActivity) getActivity()).getClassName("Add Your First Class",  "To get started, add your first class. ");
		
		ArrayAdapter<class_database> adapter = new ArrayAdapter<class_database>(getActivity().getBaseContext(), R.layout.class_list_view, values);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//need to start a new fragment with info from the clicked items
			}
		});
		
		return v;
	}

	@SuppressWarnings("unchecked")
	public void deleteAllClasses() {
		mDataSource.deleteAllClass();
		ArrayAdapter<class_database> adapter = (ArrayAdapter<class_database>) mListView.getAdapter();
		adapter.clear();
		adapter.notifyDataSetChanged();
	}
	
	@SuppressWarnings("unchecked")
	public void createclass(String name){
		ArrayAdapter<class_database> adapter = (ArrayAdapter<class_database>) mListView.getAdapter();
		mDataSource.createClass(name);
		adapter.clear();
		List<class_database> values = mDataSource.getAllClasses();
		adapter.addAll(values);
		adapter.notifyDataSetChanged();
	}
}
