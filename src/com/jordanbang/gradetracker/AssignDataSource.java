package com.jordanbang.gradetracker;

import java.util.ArrayList;
import java.util.List;

import com.jordanbang.gradetracker.models.Assignment;

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
	
	public Assignment createAssignment(String AssignName, String ClassName, double mark, double worth){
		ContentValues values = new ContentValues();
		values.put(AssignSQLiteHelper.CLM_ASSIGNNAME, AssignName);
		values.put(AssignSQLiteHelper.CLM_CLASSNAME, ClassName);
		values.put(AssignSQLiteHelper.CLM_MARK, mark);
		values.put(AssignSQLiteHelper.CLM_WORTH, worth);
		long insertId = database.insert(AssignSQLiteHelper.TABLE_ASSIGN, null, values);
		Cursor cursor = database.query(AssignSQLiteHelper.TABLE_ASSIGN, null, AssignSQLiteHelper.CLM_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Assignment newAssign = cursorToAssign(cursor);
		cursor.close();
		
		return newAssign;
	}
	
	public void deleteAssign(Assignment assignment){
		long id = assignment.getId();
		database.delete(AssignSQLiteHelper.TABLE_ASSIGN, AssignSQLiteHelper.CLM_ID + " = " + id, null);
	}
	
	public void editAssign(String AssignName, String ClassName, double mark, double worth, Assignment assignment){
		
	}
	
	private Assignment cursorToAssign(Cursor cursor){
		Assignment assignment = new Assignment();
		assignment.setId(cursor.getLong(0));
		assignment.setAssignName(cursor.getString(1));
		assignment.setClassName(cursor.getString(2));
		assignment.setMark(cursor.getDouble(3));
		assignment.setWorth(cursor.getDouble(4));
		
		return assignment;
	}
	
	public List<Assignment> getAllAsignmentsforClass(String classname){
		List<Assignment> assigns = new ArrayList<Assignment>();
		Cursor cursor = null;
		cursor = database.query(AssignSQLiteHelper.TABLE_ASSIGN, null, AssignSQLiteHelper.CLM_CLASSNAME + " = '" +classname +"'", null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Assignment assignment = cursorToAssign(cursor);
			assigns.add(assignment);
			cursor.moveToNext();
		}
		cursor.close();
		return assigns;
	}
}
