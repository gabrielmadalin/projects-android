package com.josuvladimir.smsender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

@SuppressWarnings("unused")
public class SmSenderActivity extends Activity {
	private static String destinationAddress = "+40742503976";
	private static final String ACTION_SMS_SENT = "com.josuvladimir.smsender.SMS_SENT_ACTION";

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String textMessage = "ce faci mere";
		startService(new Intent(this,SmSenderService.class));
		finish();
    }
}