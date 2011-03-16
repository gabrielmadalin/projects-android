package com.josuvladimir.neuropsychologie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TextViewActivity extends Activity {

	private TextView mTextView;
	private TextView mTitleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textview);
		mTextView = (TextView) findViewById(R.id.text_view);
		mTitleView = (TextView) findViewById(R.id.text_title);
		Bundle bundle = getIntent().getExtras();
		mTitleView.setText(bundle.getString(Application.SUBLIST));
		loadData(bundle.getInt(Application.LIST_ID),bundle.getInt(Application.SUBLIST_ID));
	}
	private void loadData(int listID, int subListID){
		String string;
		if(subListID < 10){
			string = "R.array.data_" + listID + "_0" + subListID;			
		} else {
			string = "R.array.data_" + listID + "_" + subListID;						
		}
		int	arrayID;
		if (Application.checkData(string)) {
			arrayID = Application.getData(string);
			String[] tmpStrings = getResources().getStringArray(arrayID);			
			mTextView.setText(tmpStrings[3]);		
		}
	}
	
}
