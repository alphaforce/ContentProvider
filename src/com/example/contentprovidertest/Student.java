package com.example.contentprovidertest;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Student implements BaseColumns{
	
    public static final String STUDENT_ID = "studentId";
    public static final String STUDENT_NAME = "name";
    public static final String STUDENT_SUMMARY = "summary";
    public static final String STUDENT_SEX = "sex";
     
     
    public static final String AUTOHORITY = "com.example.contentprovidertest";
//    public static final int ITEM = 1;
//    public static final int ITEM_ID = 2;
     
//    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.example.contentprovidertest";
//    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.example.contentprovidertest";
     
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/student");
    public static final Uri CONTENT_URI_INSERT = Uri.parse("content://" + AUTOHORITY + "/insert");
}