package com.example.grade_tracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClassesSQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "gradetracker_classes.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_CLASSES = "class_name";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CLASSNAME = "classname";
	
	String CLASS_DATABASE_CREATE = "create table class_name(_id integer primary key autoincrement, classname text not null);";
	
	public ClassesSQLiteHelper(Context c){
		super (c, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database){
		database.execSQL(CLASS_DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
