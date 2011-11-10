package com.josuvladimir.util;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;

public class RateDialog extends AlertDialog implements OnClickListener {

	private static final String INTENT_REQUEST = "intent_request";
	private static final String INTENT_COUNT = "intent_count";
	private static final String INTENT_MAX = "intent_max";

	private static final String TAG = "RateDialog";
	private static final int OFF_SET = 5;
	private SharedPreferences mSharedPreferences;
	private int mIntentCount;
	private int mIntentMax;
	private boolean mIntentRequest;
	private String mUrl;

	public RateDialog(Context context, String url, String title, String message, String positive, String neutral, String negative) {
		super(context);
		setTitle(title);
		setMessage(message);
		setButton(BUTTON_POSITIVE, positive, this);
		setButton(BUTTON_NEUTRAL, neutral, this);
		setButton(BUTTON_NEGATIVE, negative, this);
		mUrl = url;
		setCancelable(false);
		mSharedPreferences = context.getSharedPreferences("MyApp", 0);
		mSharedPreferences.getBoolean(INTENT_REQUEST, true);
		mIntentCount = mSharedPreferences.getInt(INTENT_COUNT, 0);
		mIntentMax = mSharedPreferences.getInt(INTENT_MAX, OFF_SET);
		mIntentRequest = mSharedPreferences.getBoolean(INTENT_REQUEST, true);
	}
	
	public boolean requestRating(){
		setPreferences(mIntentRequest, ++mIntentCount, mIntentMax);
//		Log.i(TAG, "Request:" + mIntentRequest + " Intent count: " + mIntentCount + " Intent max: " + mIntentMax);
		if (mIntentRequest && mIntentCount > mIntentMax) {
			show();
			return true;
		}
		return false;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case BUTTON_POSITIVE:
			openRate();
			removeFutureIntents();
			dismiss();
			break;
		case BUTTON_NEGATIVE:
			removeFutureIntents();
			cancel();
			break;
		case BUTTON_NEUTRAL:
		default:
			setPreferences(true, 0, mIntentMax + OFF_SET);
			cancel();
			break;
		}
	}

	private void removeFutureIntents() {
		setPreferences(false, 0, 0);
//		setPreferences(true, 0, 2);
	}

	private void setPreferences(boolean futureRequest, int intentCount, int intentMax) {
		Log.d(TAG, "Set future reguest:" + futureRequest + " Intent count:" + intentCount + " Intent max:" + intentMax);
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(INTENT_REQUEST, futureRequest);
		edit.putInt(INTENT_COUNT, intentCount);
		edit.putInt(INTENT_MAX, intentMax);
		edit.commit();
	}

	private void openRate() {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
		getContext().startActivity(intent);
	}
}
