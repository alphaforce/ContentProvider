package com.example.contentprovidertest;

import java.util.List;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
    private static ListView mListView;
    private List<StudentModel> mEntities;
    private static StudentListAdapter mStudentListAdapter;
    private DatabaseWrapper mDatabaseWrapper;
    
    private static final int MSG_SUCCESS = 0;
    private static final int MSG_FAILED = 1;
    
    private static Handler mHandler = new Handler(){
    	
    	@Override
    	public void handleMessage(Message msg){
    		switch(msg.what){
	    		case MSG_SUCCESS:
	    			mListView.setAdapter(mStudentListAdapter);
	    			break;
	    		case MSG_FAILED:
	    			break;
    		}
    	}
    };
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseWrapper = new DatabaseWrapper(getContentResolver());
        mListView = (ListView) findViewById(R.id.mylist);
        
        mListView.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, final long id) {
				new AlertDialog.Builder(MainActivity.this).setTitle("确定要删除  " + 
					((StudentModel)mListView.getItemAtPosition(position)).getName() +
					" 吗？")
					.setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String index = ((StudentModel)mListView.getItemAtPosition(position)).getTableId();
							mStudentListAdapter.delete(position);
							mDatabaseWrapper.delete(index);
						}
					}).setNegativeButton("取消", null)
					.show();
				return true;
			}});
        
        
        
        Thread th = new Thread(task);
        th.start();

              
//        startManagingCursor(cursor);  
//        stopManagingCursor(cursor);
    }
    
    //要启动线程做这些事
    private void initView(){

    	mEntities = mDatabaseWrapper.query();
    	mStudentListAdapter = new StudentListAdapter(this,mEntities);
    }
    
    private Runnable task = new Runnable(){
		@Override
		public void run() {
			try{
				initView();
				mHandler.obtainMessage(MSG_SUCCESS).sendToTarget();
			}catch(Exception e){
				mHandler.obtainMessage(MSG_FAILED).sendToTarget();
			}
		}
    };
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, Menu.FIRST + 1, 0, "新增学生");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
	        case Menu.FIRST + 1: 
	            Intent it = new Intent();
	        	it.setClass(MainActivity.this, CreateStuActivity.class);
	        	startActivity(it);
	        	break;
	        default:
	            break;
        }
        return true;
	}
	
	@Override
	protected void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		setIntent(intent);
		Thread th = new Thread(task);
        th.start();
	}
}