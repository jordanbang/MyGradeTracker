package com.jordanbang.gradetracker.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AssignSQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "gradetrackerassignments.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_ASSIGN = "assignments";
	public static final String CLM_ID = "_id";
	public static final String CLM_ASSIGNNAME = "assignment_name";
	public static final String CLM_CLASSID = "class";
	public static final String CLM_MARK = "mark";
	public static final String CLM_WORTH = "worth";
	
	
	
	String ASSIGN_DATABASE_CREATE = "create table " + TABLE_ASSIGN + "("
			+ CLM_ID + " integer primary key autoincrement, " 
			+ CLM_ASSIGNNAME+ " text not null, "
			+ CLM_CLASSID + " integer not null, "
			+ CLM_MARK + " double, "
			+ CLM_WORTH + " double not null);";
	
	public AssignSQLiteHelper(Context c){
		super (c, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database){
		database.execSQL(ASSIGN_DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
