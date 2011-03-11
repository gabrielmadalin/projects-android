package com.josuvladimir.neuropsychologie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Home extends Activity{
	/** Called when the activity is first created. */
	ListView mListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
    }
}