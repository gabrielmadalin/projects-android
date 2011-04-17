package com.josuvladimir.katzenshaker;

import android.app.Application;

public class Katzenshaker extends Application {

	@Override
	public void onCreate() {
		test();
		super.onCreate();
	}

	private void test() {
		getResources().getResourceName(R.raw.i_love_u_baby);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
