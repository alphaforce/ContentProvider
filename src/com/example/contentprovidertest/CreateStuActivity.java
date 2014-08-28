package com.example.contentprovidertest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateStuActivity extends Activity {

	private DatabaseWrapper mDatabaseWrapper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_stu);

		mDatabaseWrapper = new DatabaseWrapper(getContentResolver());
		final EditText name = (EditText) findViewById(R.id.stu_name);
		final EditText stu_id = (EditText) findViewById(R.id.stu_email);
		final EditText sex = (EditText) findViewById(R.id.stu_sex);
		final EditText summary = (EditText) findViewById(R.id.stu_summary);
		Button save = (Button) findViewById(R.id.save);
		Button cancel = (Button) findViewById(R.id.cacel);
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (name.getText().toString().isEmpty() || stu_id.getText().toString().isEmpty() 
						|| sex.getText().toString().isEmpty() || summary.getText().toString().isEmpty()){
					Toast.makeText(getApplicationContext(), "请输入完整信息", Toast.LENGTH_LONG).show();
				}else{
					StudentModel model = new StudentModel(name.getText().toString(),
							stu_id.getText().toString(),sex.getText().toString(),
							summary.getText().toString());
					mDatabaseWrapper.insert(model);
					Intent it = new Intent();
					it.setClass(CreateStuActivity.this, MainActivity.class);
					startActivity(it);
					Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
					
				}
			}
		});
		
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent();
				it.setClass(CreateStuActivity.this, MainActivity.class);
				startActivity(it);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_stu, menu);
		return true;
	}

}
