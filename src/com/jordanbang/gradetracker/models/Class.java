package com.jordanbang.gradetracker.models;

public class Class{
	private long mId;
	private String mClassName;
	
	public long getId(){
		return mId;
	}
	
	public void setId(long id){
		this.mId = id;
	}
	
	public String getClassName(){
		return mClassName;
	}
	
	public void setClassName(String class_name){
		this.mClassName = class_name;
	}
	
	@Override
	public String toString(){
		return mClassName;
	}
}
