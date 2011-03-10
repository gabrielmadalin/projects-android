package com.josuvladimir.neuropsychologie;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Home extends ListActivity{
	/** Called when the activity is first created. */
	ListView mListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		mListView = getListView();
    }
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent();
		intent.setClass(this, SubList.class);
		startActivity(intent);
	}
}