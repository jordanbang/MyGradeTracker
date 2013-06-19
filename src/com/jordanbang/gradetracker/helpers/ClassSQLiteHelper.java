package com.jordanbang.gradetracker.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClassSQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "gradetrackerclasses.db";
	private static final int DATABASE_VERSION = 2;
	public static final String TABLE_CLASSES = "class_name";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CLASSNAME = "classname";
	
	String CLASS_DATABASE_CREATE = "create table class_name(" + COLUMN_ID + 
			" integer primary key autoincrement, " + COLUMN_CLASSNAME + " text not null);";
	
	public ClassSQLiteHelper(Context c){
		super (c, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database){
		database.execSQL(CLASS_DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
}
