package com.jordanbang.gradetracker;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ClassDataSource {
	private SQLiteDatabase database;
	private ClassesSQLiteHelper dbHelper;
	private String[] allColumns = {ClassesSQLiteHelper.COLUMN_ID, ClassesSQLiteHelper.COLUMN_CLASSNAME};
	
	public ClassDataSource(Context c){
		dbHelper = new ClassesSQLiteHelper(c);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public class_database createClass(String classname){
		ContentValues values = new ContentValues();
		values.put(ClassesSQLiteHelper.COLUMN_CLASSNAME, classname);
		long insertId = database.insert(ClassesSQLiteHelper.TABLE_CLASSES, null, values);
		Cursor cursor = database.query(ClassesSQLiteHelper.TABLE_CLASSES, null ,ClassesSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		class_database newclass = cursorToClass(cursor);
		cursor.close();
		return newclass;
	}
	
	public void deleteClass(class_database classes){
		long id = classes.getId();
		database.delete(ClassesSQLiteHelper.TABLE_CLASSES, ClassesSQLiteHelper.COLUMN_ID + " = " + id,null);
	}
	
	private class_database cursorToClass(Cursor cursor){
		class_database classes = new class_database();
		classes.setId(cursor.getLong(0));
		classes.setClassName(cursor.getString(1));
		return classes;
	}
	
	public List<class_database> getAllClasses(){
		List<class_database> classes = new ArrayList<class_database>();
		Cursor cursor = database.query(ClassesSQLiteHelper.TABLE_CLASSES, null, null, null, null, null, "classname");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			class_database clas = cursorToClass(cursor);
			classes.add(clas);
			cursor.moveToNext();
		}
		cursor.close();
		return classes;
	}
	
	public void deleteAllClass(){
		Cursor cursor = database.query(ClassesSQLiteHelper.TABLE_CLASSES, null, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			deleteClass(cursorToClass(cursor));
			cursor.moveToNext();
		}
		cursor.close();
	}
	
}