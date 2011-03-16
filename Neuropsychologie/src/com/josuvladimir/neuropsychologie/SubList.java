package com.josuvladimir.neuropsychologie;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class SubList extends ListActivity {

	private List<String> mStrings;
	private ListAdapter	mAdapter;
	private TextView	mTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sublist);
		mTextView = (TextView) findViewById(R.id.sublist_title);
		mStrings = new ArrayList<String>();
		Bundle bundle = getIntent().getExtras();
		mTextView.setText(bundle.getString(Application.SUBLIST));
		int id = bundle.getInt(Application.LIST_ID);
		getData(id);
		mAdapter = new ListAdapter(this, mStrings);
		setListAdapter(mAdapter);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		showSubList(0);
		showTextView(0);
	}
	private void showSubList(int id) {
		Intent intent = new Intent();
		intent.setClass(this, TextViewActivity.class);
		startActivity(intent);		
	}
	private void showTextView(int id) {
		
	}
	public void getData(int id) {
		String string = "R.array.data_" + id + "_";
		String tmpString;
		String[] tmpStrings;
		int	arrayID;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				tmpString = string + i + j;
				if(Application.checkData(tmpString)){
					arrayID = Application.getData(tmpString);
					tmpStrings = getResources().getStringArray(arrayID);
					mStrings.add(tmpStrings[0]);
				}
			}
		}
	}
}
