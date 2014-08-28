package com.example.contentprovidertest;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StudentListAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private List<StudentModel> mEntites;
	
	public StudentListAdapter(Context context,List<StudentModel> list){
		mContext = context;
		mEntites = list;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mEntites.size();
	}

	@Override
	public Object getItem(int position) {
		return mEntites.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = mInflater.inflate(R.layout.list_view, null);
		}
		ToUI((StudentModel)getItem(position),convertView);
		return convertView;
	}
	
	private void ToUI(StudentModel stu,View view){
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView id = (TextView) view.findViewById(R.id.stu_id);
		TextView summary = (TextView) view.findViewById(R.id.summary);
		TextView sex = (TextView) view.findViewById(R.id.sex);
		
		name.setText(stu.getName());
		id.setText(stu.getId());
		summary.setText(stu.getSummary());
		sex.setText(stu.getSex());
	}

	public void delete(int position){
		if (position >= 0){
			mEntites.remove(position);
			notifyDataSetChanged();
		}
	}
}
