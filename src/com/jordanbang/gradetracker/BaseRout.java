package com.jordanbang.gradetracker;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;

public final class BaseRout 
{
	static boolean reslValue = false;
	public static SharedPreferences settings;	
	public static SharedPreferences.Editor editor ;
	static ProgressDialog dialog;
	static Activity mainActivity;

    public static String CurrentDateTime()
    {
    	return android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString(); 
    }
	
    public static void Init(Activity curact, String PREFS_NAME)
    {
    	mainActivity = curact;
    	//curact.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    	settings = curact.getSharedPreferences(PREFS_NAME, 0);
    	editor = settings.edit();
    }
    
    public static String DefValue(Object aVal)
    {
    	if (aVal == null)
    		return "";
    	return aVal.toString();
    }
    
	public static String getVar(String VarName)
	{
			return getVar(VarName, "");
	}
	
	public static String getVar(String VarName, String DefValue)
	{
		return settings.getString(VarName, DefValue);
	}

	public static void setVar(String VarName, String nValue)
	{
	    editor.putString(VarName, nValue);
	    editor.commit();    
	}
	
	public static void setVar(String VarName, CharSequence nValue)
	{
	    editor.putString(VarName, nValue.toString());
	    editor.commit();    
	}
	
    public static boolean eMailValidation(String emailstring)
    {
        Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();

    }
	
    
	public static boolean MessageBox(Context curact, String Title, String Message, OnClickListener mOkAction, OnClickListener mCancelAction)
	{
		new AlertDialog.Builder(curact) 
		.setTitle(Title) 
		.setMessage(Message) 
		.setPositiveButton("OK", mOkAction) 
		.setNegativeButton("cancel", mCancelAction) 
		.show(); 
		return reslValue;
	}

	
	public static boolean MessageBox(Context curact, String Title, String Message, OnClickListener mOkAction)
	{
		new AlertDialog.Builder(curact) 
		.setTitle(Title) 
		.setMessage(Message) 
		.setPositiveButton("OK", mOkAction) 
		.show(); 
		return reslValue;
	}
	
	public static boolean MessageBox(Context curact, String Title, String Message)
	{
		new AlertDialog.Builder(curact) 
		.setTitle(Title) 
		.setMessage(Message) 
		.setPositiveButton("OK", null)
		.show(); 
		return reslValue;
	}
	
	
	public static void showBusyCursor(Context curact, Boolean $show)
	{
		showBusyCursor(curact, $show, null);
	}
	
	
	public static void showBusyCursor(Context curact, Boolean $show, String txtToDisplay)
	{
		if ($show == true)
		{
			dialog = new ProgressDialog(curact); 
			if (txtToDisplay != null)
				dialog.setMessage(txtToDisplay); 
			dialog.setIndeterminate(true); 
			dialog.setCancelable(false); 
			dialog.show();	
		}
		else
		{
			if (dialog != null)
				dialog.cancel();
			dialog = null;
		}
	}

}


