package com.josuvladimir.smsender;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmSenderActivity extends Activity {
	private static final String scAddress = null;
	private static String destinationAddress = "+40742503976";
	private static final String ACTION_SMS_SENT = "com.josuvladimir.smsender.SMS_SENT_ACTION";

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SmsManager smsManager = SmsManager.getDefault();
        
        String text = "test";
    	destinationAddress = "+40744931398";
		smsManager.sendTextMessage(destinationAddress, scAddress, text, PendingIntent.getBroadcast(this, 0, new Intent(ACTION_SMS_SENT), 0), null);
		Toast.makeText(this, "Sended", Toast.LENGTH_SHORT).show();
    }
}