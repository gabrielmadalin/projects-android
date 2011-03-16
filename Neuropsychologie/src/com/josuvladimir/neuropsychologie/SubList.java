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
	private int			mListID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sublist);
		mTextView = (TextView) findViewById(R.id.sublist_title);
		mStrings = new ArrayList<String>();
		Bundle bundle = getIntent().getExtras();
		mTextView.setText(bundle.getString(Application.SUBLIST));
		mListID = bundle.getInt(Application.LIST_ID);
		getData(mListID);
		mAdapter = new ListAdapter(this, mStrings);
		setListAdapter(mAdapter);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		showText(position);
	}
	private void showText(int id) {
		Intent intent = new Intent();
		intent.setClass(this, TextViewActivity.class);
		intent.putExtra(Application.LIST_ID, mListID);
		intent.putExtra(Application.SUBLIST_ID, id + 1);
		intent.putExtra(Application.SUBLIST, mStrings.get(id));
		startActivity(intent);		
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
