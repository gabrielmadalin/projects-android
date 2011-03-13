package com.josuvladimir.neuropsychologie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class Home extends Activity implements OnClickListener{
	/** Called when the activity is first created. */
	ListView mListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		findViewById(R.id.home_button).setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_button:
			Intent intent = new Intent(this, List.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}