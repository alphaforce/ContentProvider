package com.example.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class StudentProvider extends ContentProvider{
	 
	private StudentDataBaseHelper mHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private static final String TAG = "StudentProvider";
    
    private static final int URI_STUDENT_ALL = 0;
    private static final int URI_STUDENT_BY_ID = 1;
    private static final int URI_STUDENT_INSERT = 2;
    private static final String VND_ANDROID_DIR_STUDENT = "vnd.android-dir/student";
    private static final String VND_ANDROID_ITEM_STUDENT = "vnd.android-item/student";
     
    private static final UriMatcher sMatcher;
    static{
        sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sMatcher.addURI(Student.AUTOHORITY,"student", URI_STUDENT_ALL);
        sMatcher.addURI(Student.AUTOHORITY, "student/#", URI_STUDENT_BY_ID);
        sMatcher.addURI(Student.AUTOHORITY, "insert", URI_STUDENT_INSERT);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

    	mSQLiteDatabase = mHelper.getWritableDatabase();
        int count = 0;
        switch (sMatcher.match(uri)) {
	        case URI_STUDENT_ALL:
	            count = mSQLiteDatabase.delete(StudentDataBaseHelper.TABLE_NAME,
	            		selection, selectionArgs);
	            break;
	        case URI_STUDENT_BY_ID:
	        	try{
		        	long id = Long.parseLong(uri.getLastPathSegment());
		            count = mSQLiteDatabase.delete(StudentDataBaseHelper.TABLE_NAME, 
		            		Student._ID + " = " + id, null );
	        	}catch(NumberFormatException e){
	        		Log.e(TAG, "Student ID must be a Long.");
	        	}
	            break;
	        default:
	            throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sMatcher.match(uri)) {
	        case URI_STUDENT_ALL:
                return VND_ANDROID_DIR_STUDENT;
	        case URI_STUDENT_BY_ID:
	            return VND_ANDROID_ITEM_STUDENT;
	        default:
                throw new IllegalArgumentException("Unknown URI"+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

    	mSQLiteDatabase = mHelper.getWritableDatabase();
		long rowId;
		if(sMatcher.match(uri) != URI_STUDENT_INSERT){
			throw new IllegalArgumentException("Unknown URI"+uri);
		}
	    rowId = mSQLiteDatabase.insert(StudentDataBaseHelper.TABLE_NAME,null,values);
	    if(rowId > 0){
           Uri noteUri=ContentUris.withAppendedId(Student.CONTENT_URI, rowId);
           getContext().getContentResolver().notifyChange(noteUri, null);
           return noteUri;
	    }
        throw new IllegalArgumentException("Unknown URI"+uri);
    }

    @Override
    public boolean onCreate() {
        mHelper = new StudentDataBaseHelper(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                    String[] selectionArgs, String sortOrder) {

    	mSQLiteDatabase = mHelper.getWritableDatabase();                
        Cursor c;
        Log.d("-------", String.valueOf(sMatcher.match(uri)));
        switch (sMatcher.match(uri)) {
        case URI_STUDENT_ALL:
                c = mSQLiteDatabase.query(StudentDataBaseHelper.TABLE_NAME, 
                		projection, selection, selectionArgs, null, null, null);
         
                break;
        case URI_STUDENT_BY_ID:
                String id = uri.getPathSegments().get(1);
                c = mSQLiteDatabase.query(StudentDataBaseHelper.TABLE_NAME,
                		projection, Student._ID + "="
                + id + (!TextUtils.isEmpty(selection)?"AND("+selection+')':""),
                selectionArgs, null, null, sortOrder);
            break;
        default:
                Log.d("!!!!!!", "Unknown URI"+uri);
                throw new IllegalArgumentException("Unknown URI"+uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {
        return 0;
    }
}