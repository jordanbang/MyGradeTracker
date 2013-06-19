package com.jordanbang.gradetracker.datasources;

import java.util.ArrayList;
import java.util.List;

import com.jordanbang.gradetracker.helpers.ClassSQLiteHelper;
import com.jordanbang.gradetracker.models.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ClassDataSource {
	private SQLiteDatabase mDataBase;
	private ClassSQLiteHelper mDBHelper;
//	private String[] mAllColumns = {ClassSQLiteHelper.COLUMN_ID, ClassSQLiteHelper.COLUMN_CLASSNAME};
	
	public ClassDataSource(Context c){
		mDBHelper = new ClassSQLiteHelper(c);
	}
	
	public void open() throws SQLException {
		mDataBase = mDBHelper.getWritableDatabase();
	}
	
	public void close() {
		mDBHelper.close();
	}
	
	public Class createClass(String classname){
		ContentValues values = new ContentValues();
		values.put(ClassSQLiteHelper.COLUMN_CLASSNAME, classname);
		long insertId = mDataBase.insert(ClassSQLiteHelper.TABLE_CLASSES, null, values);
		Cursor cursor = mDataBase.query(ClassSQLiteHelper.TABLE_CLASSES, null ,ClassSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Class newclass = cursorToClass(cursor);
		cursor.close();
		return newclass;
	}
	
	public void deleteClass(Class classes){
		long id = classes.getId();
		mDataBase.delete(ClassSQLiteHelper.TABLE_CLASSES, ClassSQLiteHelper.COLUMN_ID + " = " + id,null);
	}
	
	private Class cursorToClass(Cursor cursor){
		Class classes = new Class();
		classes.setId(cursor.getLong(0));
		classes.setClassName(cursor.getString(1));
		return classes;
	}
	
	public List<Class> getAllClasses(){
		List<Class> classes = new ArrayList<Class>();
		Cursor cursor = mDataBase.query(ClassSQLiteHelper.TABLE_CLASSES, null, null, null, null, null, "classname");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Class clas = cursorToClass(cursor);
			classes.add(clas);
			cursor.moveToNext();
		}
		cursor.close();
		return classes;
	}
	
	public void deleteAllClass(){
		Cursor cursor = mDataBase.query(ClassSQLiteHelper.TABLE_CLASSES, null, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			deleteClass(cursorToClass(cursor));
			cursor.moveToNext();
		}
		cursor.close();
	}
	
}