package com.josuvladimir.neuropsychologie;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class List extends ListActivity {
	private String[] mStrings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		mStrings = getResources().getStringArray(R.array.home_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings);
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, SubList.class);
		intent.putExtra(Application.LIST_ID, position);
		intent.putExtra(Application.SUBLIST, mStrings[position]);
		startActivity(intent);
	}
}
