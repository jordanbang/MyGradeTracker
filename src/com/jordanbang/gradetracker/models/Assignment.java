package com.jordanbang.gradetracker.models;

public class Assignment {
	private long mId;
	private String mAssignmentName;
	private String mClassName;
	private long mClassId;
	private double mMark;
	private double mWorth;
	
	public long getId(){
		return mId;
	}
	
	public long getClassId(){
		return mClassId;
	}
	
	public double getMark(){
		return mMark;
	}
	
	public double getWorth(){
		return mWorth;
	}
	
	public String getClassName(){
		return mClassName;
	}
	
	public String getAssignName(){
		return mAssignmentName;
	}
	
	public void setId(long id_n){
		mId = id_n;
	}
	
	public void setMark(double mark_n){
		mMark = mark_n;
	}
	
	public void setClassId(long id){
		mClassId = id;
	}
	
	public void setWorth(double worth_n){
		mWorth = worth_n;
	}
	
	public void setClassName(String class_name_n){
		mClassName = class_name_n;
	}
	
	public void setAssignName(String assign_name){
		mAssignmentName = assign_name;
	}
}
