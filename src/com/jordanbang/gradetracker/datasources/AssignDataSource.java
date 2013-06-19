package com.jordanbang.gradetracker.datasources;

import java.util.ArrayList;
import java.util.List;

import com.jordanbang.gradetracker.helpers.AssignSQLiteHelper;
import com.jordanbang.gradetracker.models.Assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AssignDataSource {
	private SQLiteDatabase mDataBase;
	private AssignSQLiteHelper mDBHelper;
	
	class Columns {
		public static final int ID = 0;
		public static final int ASSIGNNAME =1;
		public static final int CLASSID = 2;
		public static final int MARK = 3;
		public static final int WORTH = 4;
	}
	
	public AssignDataSource(Context c){
		mDBHelper = new AssignSQLiteHelper(c);
	}
	
	public void open() throws SQLException{
		mDataBase = mDBHelper.getWritableDatabase();
	}
	
	public void close(){
		mDBHelper.close();
	}
	
	public Assignment createAssignment(String AssignName, int classId, double mark, double worth){
		ContentValues values = new ContentValues();
		values.put(AssignSQLiteHelper.CLM_ASSIGNNAME, AssignName);
		values.put(AssignSQLiteHelper.CLM_CLASSID, classId);
		values.put(AssignSQLiteHelper.CLM_MARK, mark);
		values.put(AssignSQLiteHelper.CLM_WORTH, worth);
		long insertId = mDataBase.insert(AssignSQLiteHelper.TABLE_ASSIGN, null, values);
		Cursor cursor = mDataBase.query(AssignSQLiteHelper.TABLE_ASSIGN, null, AssignSQLiteHelper.CLM_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Assignment newAssign = cursorToAssign(cursor);
		cursor.close();
		
		return newAssign;
	}
	
	public void deleteAssign(Assignment assignment){
		long id = assignment.getId();
		mDataBase.delete(AssignSQLiteHelper.TABLE_ASSIGN, AssignSQLiteHelper.CLM_ID + " = " + id, null);
	}
	
	private Assignment cursorToAssign(Cursor cursor){
		Assignment assignment = new Assignment();
		assignment.setId(cursor.getLong(Columns.ID));
		assignment.setAssignName(cursor.getString(Columns.ASSIGNNAME));
		assignment.setClassId(cursor.getLong(Columns.CLASSID));
		assignment.setMark(cursor.getDouble(Columns.MARK));
		assignment.setWorth(cursor.getDouble(Columns.WORTH));
		return assignment;
	}
	
	public List<Assignment> getAllAsignmentsforClass(int classId){
		List<Assignment> assigns = new ArrayList<Assignment>();
		Cursor cursor = null;
		cursor = mDataBase.query(AssignSQLiteHelper.TABLE_ASSIGN, null, AssignSQLiteHelper.CLM_CLASSID + " = '" +classId +"'", null, null, null, null);
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
