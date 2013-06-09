package com.jordanbang.gradetracker;

public class class_database{
	private long id;
	private String class_name;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getClassName(){
		return class_name;
	}
	
	public void setClassName(String class_name){
		this.class_name = class_name;
	}
	
	@Override
	public String toString(){
		return class_name;
	}
}
