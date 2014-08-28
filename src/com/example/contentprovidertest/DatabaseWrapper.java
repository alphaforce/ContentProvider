package com.example.contentprovidertest;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class DatabaseWrapper {
	private ContentResolver mContentResolver;
	private List<StudentModel> mEntities;
	
	public DatabaseWrapper(ContentResolver cr){
		mContentResolver = cr;
	}
	
	public void insert(StudentModel model){
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(Student.STUDENT_NAME,model.getName());
		initialValues.put(Student.STUDENT_SEX, model.getSex());
		initialValues.put(Student.STUDENT_SUMMARY, model.getSummary());
		initialValues.put(Student.STUDENT_ID, model.getId());
		mContentResolver.insert(Student.CONTENT_URI_INSERT, initialValues);
	}
	
	public List<StudentModel> query(){
		
		Cursor cursor = mContentResolver.query(Student.CONTENT_URI, null, null, null, null);
    	mEntities = new ArrayList<StudentModel>();
    	if (cursor.moveToFirst()){
    		do{
    			StudentModel stu = new StudentModel();
    			stu.setTableId(cursor.getString(cursor.getColumnIndex(Student._ID)));
    			stu.setId(cursor.getString(cursor.getColumnIndex(Student.STUDENT_ID)));
    			stu.setName(cursor.getString(cursor.getColumnIndex(Student.STUDENT_NAME)));
    			stu.setSex(cursor.getString(cursor.getColumnIndex(Student.STUDENT_SEX)));
    			stu.setSummary(cursor.getString(cursor.getColumnIndex(Student.STUDENT_SUMMARY)));
    			mEntities.add(stu);
    		}while(cursor.moveToNext());
    		cursor.close();
    	}
    	return mEntities;
	}
	
	public void delete(String index){
		Uri uri = Uri.withAppendedPath(Student.CONTENT_URI, index);
		mContentResolver.delete(uri, null, null);
	}
	
	public void update(){
		
	}
}
