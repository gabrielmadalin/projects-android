package com.josuvladimir.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class UtilsActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.main_btn_carousel).setOnClickListener(this);
    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.main_btn_carousel:
			Intent intent = new Intent(this, CarouselActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}