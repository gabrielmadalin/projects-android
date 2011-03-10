package com.josuvladimir.neuropsychologie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TextViewActivity extends Activity {

	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textview);
		mTextView = (TextView) findViewById(R.id.text_view);
		mTextView.setText(R.string.text_01_01);
	}
	
}
