package com.example.contentprovidertest;

public class StudentModel {
	private String tableId;
	private String studentId;
	private String studentName;
	private String studentSummary;
	private String studentSex;
	
	public StudentModel(){
		
	}
	
	public StudentModel(String id,String name,String sex,String summary){
		studentId = id;
		studentName = name;
		studentSex = sex;
		studentSummary = summary;
	}
	
	public String getId(){
		return studentId;
	}
	
	public String getName(){
		return studentName;
	}
	
	public String getSummary(){
		return studentSummary;
	}
	
	public String getSex(){
		return studentSex;
	}
	
	public String getTableId(){
		return tableId;
	}
	
	public void setId(String id){
		studentId = id;
	}
	
	public void setName(String name){
		studentName = name;
	}
	
	public void setSex(String sex){
		studentSex = sex;
	}
	
	public void setSummary(String summ){
		studentSummary = summ;
	}
	
	public void setTableId(String id){
		tableId = id;
	}
}
