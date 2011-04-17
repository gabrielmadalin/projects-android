package com.josuvladimir.util;

import android.content.Context;
import android.content.Intent;


public class ShareObject {
	private Context mContext;
	private String mSubject;
	private String mText;
	public ShareObject(Context context, String subject, String text) {
		mContext = context;
		mSubject = subject;
		mText = text;
	}
	public void share(){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, mSubject);
		intent.putExtra(Intent.EXTRA_TEXT, mText);
		mContext.startActivity(Intent.createChooser(intent, "Share"));
	}
}
