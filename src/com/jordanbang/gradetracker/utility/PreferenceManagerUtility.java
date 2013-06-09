package com.jordanbang.gradetracker.utility;

import android.app.Activity;
import android.content.SharedPreferences;

public class PreferenceManagerUtility {

	private static SharedPreferences settings;
	private static SharedPreferences.Editor editor;
	
	
	/**
	 * Initialize the {@link SharedPreferences} for the app.  Needs to be done at the start up of the app.
	 * 
	 * @param activity
	 *            	The {@link Activity} that is calling the initialization.  Should be the main activity.
	 * @param name
	 * 				The name of the shared preference manager.  Should be unique to the app.
	 */
	
	public static void Init(Activity activity, String name){
		settings = activity.getSharedPreferences(name, 0);
		editor = settings.edit();
	}
	
	
	/**
	 * Get a string from the {@link SharedPreferences}.
	 * @param name
	 * 				The name of the variable stored in the shared preferences.
	 * @return
	 * 				The string value for the stored variable.  If no string is found, will return "" by default.
	 */
	public static String getString(String name){
		return settings.getString(name, "");
	}
	
	
	/**
	 * Set a string value in the {@link SharedPreferences}.
	 * @param name
	 * 				The name of the variable to be stored.
	 * 
	 * @param value
	 * 				The string value to be stored.
	 */
	public static void setString(String name, String value){
		editor.putString(name, value);
		editor.commit();
	}
}
