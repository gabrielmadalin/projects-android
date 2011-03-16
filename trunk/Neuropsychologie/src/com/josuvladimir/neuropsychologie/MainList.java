package com.josuvladimir.neuropsychologie;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainList extends ListActivity {
	private String[] mStrings;
	private ListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		mStrings = getResources().getStringArray(R.array.home_list);
		mAdapter = new ListAdapter(this, mStrings);
		setListAdapter(mAdapter);
		getListView().setTextFilterEnabled(true);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, SubList.class);
		intent.putExtra(Application.LIST_ID, position + 1);
		intent.putExtra(Application.SUBLIST, mStrings[position]);
		startActivity(intent);
	}
}
