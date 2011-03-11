package com.josuvladimir.neuropsychologie;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SubList extends ListActivity {

	private String[] strings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sublist);
		strings = getResources().getStringArray(R.array.data_1_01);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,strings);
		setListAdapter(adapter);
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
}
