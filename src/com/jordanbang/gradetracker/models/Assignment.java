package com.jordanbang.gradetracker.models;

public class Assignment {
	private long id;
	private String assignment_name;
	private String class_name;
	private double mark;
	private double worth;
	
	public long getId(){
		return id;
	}
	
	public double getMark(){
		return mark;
	}
	
	public double getWorth(){
		return worth;
	}
	
	public String getClassName(){
		return class_name;
	}
	
	public String getAssignName(){
		return assignment_name;
	}
	
	public void setId(long id_n){
		id = id_n;
	}
	
	public void setMark(double mark_n){
		mark = mark_n;
	}
	
	public void setWorth(double worth_n){
		worth = worth_n;
	}
	
	public void setClassName(String class_name_n){
		class_name = class_name_n;
	}
	
	public void setAssignName(String assign_name){
		assignment_name = assign_name;
	}
}
