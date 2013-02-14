package com.example.grade_tracker;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AssignDataSource {
	private SQLiteDatabase database;
	private AssignSQLiteHelper dbHelper;
	
	public AssignDataSource(Context c){
		dbHelper = new AssignSQLiteHelper(c);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public assign createAssignment(String AssignName, String ClassName, double mark, double worth){
		ContentValues values = new ContentValues();
		values.put(AssignSQLiteHelper.CLM_ASSIGNNAME, AssignName);
		values.put(AssignSQLiteHelper.CLM_CLASSNAME, ClassName);
		values.put(AssignSQLiteHelper.CLM_MARK, mark);
		values.put(AssignSQLiteHelper.CLM_WORTH, worth);
		long insertId = database.insert(AssignSQLiteHelper.TABLE_ASSIGN, null, values);
		Cursor cursor = database.query(AssignSQLiteHelper.TABLE_ASSIGN, null, AssignSQLiteHelper.CLM_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		assign newAssign = cursorToAssign(cursor);
		cursor.close();
		
		return newAssign;
	}
	
	public void deleteAssign(assign assignment){
		long id = assignment.getId();
		database.delete(AssignSQLiteHelper.TABLE_ASSIGN, AssignSQLiteHelper.CLM_ID + " = " + id, null);
	}
	
	public void editAssign(String AssignName, String ClassName, double mark, double worth, assign assignment){
		
	}
	
	private assign cursorToAssign(Cursor cursor){
		assign assignment = new assign();
		assignment.setId(cursor.getLong(0));
		assignment.setAssignName(cursor.getString(1));
		assignment.setClassName(cursor.getString(2));
		assignment.setMark(cursor.getDouble(3));
		assignment.setWorth(cursor.getDouble(4));
		
		return assignment;
	}
	
	public List<assign> getAllAsignmentsforClass(String classname){
		List<assign> assigns = new ArrayList<assign>();
		Cursor cursor = null;
		cursor = database.query(AssignSQLiteHelper.TABLE_ASSIGN, null, AssignSQLiteHelper.CLM_CLASSNAME + " = '" +classname +"'", null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			assign assignment = cursorToAssign(cursor);
			assigns.add(assignment);
			cursor.moveToNext();
		}
		cursor.close();
		return assigns;
	}
}
