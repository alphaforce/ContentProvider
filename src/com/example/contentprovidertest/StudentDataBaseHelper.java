package com.example.contentprovidertest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDataBaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "student.db";
	private static final int DATABASE_VERSION = 1;
	static final String TABLE_NAME = "students";
	
//	static final String DATABASE_CREATE = 
//			"create table StudentTable(_id integer primary key autoincrement," + 
//			"studentId text not null,name text not null," + 
//			"sex text not null,summary text not null);";
	
	static final String DATABASE_CREATE = "CREATE TABLE " 
			+ TABLE_NAME + " (" + Student._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Student.STUDENT_ID 
			+ " TEXT NOT NULL, " + Student.STUDENT_NAME 
			+ " TEXT NOT NULL, " + Student.STUDENT_SUMMARY
			+ " TEXT NOT NULL, " + Student.STUDENT_SEX 
			+ " TEXT NOT NULL);";
	
	private static StudentDataBaseHelper mInstance;
	
	static synchronized StudentDataBaseHelper getInstance(Context context){
		if (mInstance == null)
			mInstance = new StudentDataBaseHelper(context);
		return mInstance;
	}
	
    public StudentDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    	onCreate(db);
    }
}